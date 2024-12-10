package com.fpt.canteenrunner.Activity.Payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.AuthenActivity.LoginActivity;
import com.fpt.canteenrunner.Canteen.ProfileActivity;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.MyTicketDAO;
import com.fpt.canteenrunner.Database.DAO.TicketDAO;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import com.fpt.canteenrunner.MainActivity;
import com.fpt.canteenrunner.MyHistoryActivity;
import com.fpt.canteenrunner.MyTicketActivity;
import com.fpt.canteenrunner.Network.api.CreateOrder;
import com.fpt.canteenrunner.R;

import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ActivityPaymentMethod extends AppCompatActivity {

    private TextView tvFoodName, tvFoodPrice;
    private ImageView ivFoodImage;
    private Button btnPayWithPoints, btnPayWithBank;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4); // ExecutorService cho việc xử lý thanh toán
    CanteenRunnerDatabase database;
    String canteenID, imageUrl, ticketID, accountID, email_user;
    double price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act9_payment_method);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email_user = preferences.getString("email", null);
        accountID = preferences.getString("accountID", null);
        if (email_user == null){
            Intent intent = new Intent(ActivityPaymentMethod.this, LoginActivity.class);
            startActivity(intent);
        }

        database = CanteenRunnerDatabase.getInstance(this);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        // Nhận dữ liệu từ Intent
        String canteenName = getIntent().getStringExtra("CanteenName");
        price = getIntent().getDoubleExtra("Price", 0);
        canteenID = getIntent().getStringExtra("CanteenID");
        ticketID = getIntent().getStringExtra("TicketID");
        imageUrl = getIntent().getStringExtra("ImageUrl");
        // Ánh xạ các View
        btnPayWithPoints = findViewById(R.id.btn_pay_with_points);
        btnPayWithBank = findViewById(R.id.btn_pay_with_zalo);
        TextView tvCanteenName = findViewById(R.id.tv_canteen_name);
        TextView tvFoodPrice = findViewById(R.id.tv_food_price);
        ImageView ivFoodImage = findViewById(R.id.iv_food_image);

        // Cập nhật giao diện
        tvCanteenName.setText("Căng tin: " + canteenName);
        tvFoodPrice.setText(String.format("Giá: %.0f", price));

        // Hiển thị hình ảnh món ăn
        Glide.with(this).load(imageUrl).into(ivFoodImage);

        // Xử lý nút thanh toán ZaloPay
        btnPayWithBank.setOnClickListener(v -> {
            // Gọi API tạo đơn hàng và xử lý thanh toán
            executorService.submit(() -> {
                try {
                    CreateOrder orderApi = new CreateOrder();
                    JSONObject orderData = orderApi.createOrder(String.valueOf((int) price));

                    String returnCode = orderData.getString("return_code");
                    if ("1".equals(returnCode)) {
                        String token = orderData.getString("zp_trans_token");

                        // Thanh toán trực tiếp
                        runOnUiThread(() -> ZaloPaySDK.getInstance().payOrder(
                                ActivityPaymentMethod.this,
                                token,
                                "demozpdk://app",
                                new PayOrderListener() {
                                    @Override
                                    public void onPaymentSucceeded(String transactionId, String transToken, String appTransId) {
                                        onPaymentSuccess(transactionId, transToken, appTransId);
                                    }

                                    @Override
                                    public void onPaymentCanceled(String zpTransToken, String appTransId) {
                                        Toast.makeText(ActivityPaymentMethod.this, "Đã hủy thanh toán", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransId) {
                                        Toast.makeText(ActivityPaymentMethod.this, "Lỗi thanh toán", Toast.LENGTH_SHORT).show();
                                    }
                                }));
                    } else {
                        runOnUiThread(() -> Toast.makeText(ActivityPaymentMethod.this, "Tạo đơn hàng thất bại", Toast.LENGTH_SHORT).show());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(ActivityPaymentMethod.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show());
                }
            });
        });
        ImageView ivAvatar = findViewById(R.id.iv_avatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPaymentMethod.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm xử lý khi thanh toán thành công
    private void onPaymentSuccess(String transactionId, String transToken, String appTransId) {
        Toast.makeText(ActivityPaymentMethod.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        double ticketPrice = price;

        MyTicketDAO myTicketDAO = database.myTicketDAO();
        String myTicketId = "MyTicket_" + transactionId;
        String orderDate = new Date().toString();
        String paymentType = "Credit Card";
        String status = "Pending";

        MyTicketEntity myTicketEntity = new MyTicketEntity(
                myTicketId,
                accountID,  // AccountID mặc định là 1
                ticketID,
                orderDate,
                ticketPrice,
                paymentType,
                status,
                "SampleQRCode1"  // Nếu có mã QR thì điền vào đây
        );

        // Tạo task cho việc chèn MyTicket
        Future<?> myTicketTask = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    myTicketDAO.insertMyTicket(myTicketEntity);  // Chèn MyTicket vào cơ sở dữ liệu
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("DatabaseError", "Failed to insert myticket", e);
                }
            }
        });
        try {
            myTicketTask.get();  // Sau đó chờ đợi myTicketTask hoàn thành
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.e("TaskError", "Error waiting for tasks", e);
        }
        Intent intent = new Intent(ActivityPaymentMethod.this, MyHistoryActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Đảm bảo ExecutorService được tắt khi không còn sử dụng
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email_user = preferences.getString("email", null);
        accountID = preferences.getString("accountID", null);
        if (email_user == null){
            Intent intent = new Intent(ActivityPaymentMethod.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
