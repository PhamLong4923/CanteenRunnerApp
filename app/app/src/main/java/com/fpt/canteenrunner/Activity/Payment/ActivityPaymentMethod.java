package com.fpt.canteenrunner.Activity.Payment;

import android.content.Intent;
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
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.MyTicketDAO;
import com.fpt.canteenrunner.Database.DAO.TicketDAO;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
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
    String canteenID;
    double selectedPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act9_payment_method);

        database = CanteenRunnerDatabase.getInstance(this);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        // Nhận dữ liệu từ Intent
        String foodName = getIntent().getStringExtra("FoodName");
        String foodDescription = getIntent().getStringExtra("FoodDescription");
        String categoryName = getIntent().getStringExtra("CategoryName");
        String canteenName = getIntent().getStringExtra("CanteenName");
        String imageUrl = getIntent().getStringExtra("ImageURL");
        selectedPrice = getIntent().getDoubleExtra("SelectedPrice", 0);
        canteenID = getIntent().getStringExtra("CanteenID");

        // Ánh xạ các View
        btnPayWithPoints = findViewById(R.id.btn_pay_with_points);
        btnPayWithBank = findViewById(R.id.btn_pay_with_zalo);
        TextView tvFoodName = findViewById(R.id.tv_food_name);
        TextView tvFoodDescription = findViewById(R.id.tv_food_description);
        TextView tvCategoryName = findViewById(R.id.tv_category_name);
        TextView tvCanteenName = findViewById(R.id.tv_canteen_name);
        TextView tvFoodPrice = findViewById(R.id.tv_food_price);
        ImageView ivFoodImage = findViewById(R.id.iv_food_image);

        // Cập nhật giao diện
        tvFoodName.setText("Tên: " + foodName);
        tvFoodDescription.setText("Mô tả: " + foodDescription);
        tvCategoryName.setText("Danh mục: " + categoryName);
        tvCanteenName.setText("Căng tin: " + canteenName);
        tvFoodPrice.setText(String.format("Giá: %.0f", selectedPrice));

        // Hiển thị hình ảnh món ăn
        Glide.with(this).load(imageUrl).into(ivFoodImage);

        // Xử lý nút thanh toán ZaloPay
        btnPayWithBank.setOnClickListener(v -> {
            // Gọi API tạo đơn hàng và xử lý thanh toán
            executorService.submit(() -> {
                try {
                    CreateOrder orderApi = new CreateOrder();
                    JSONObject orderData = orderApi.createOrder(String.valueOf((int) selectedPrice));

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
    }

    // Hàm xử lý khi thanh toán thành công
    private void onPaymentSuccess(String transactionId, String transToken, String appTransId) {
        Toast.makeText(ActivityPaymentMethod.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        String ticketId = transactionId;
        double ticketPrice = selectedPrice;

        TicketDAO ticketDAO = database.ticketDAO();
        MyTicketDAO myTicketDAO = database.myTicketDAO();
        TicketEntity ticketEntity = new TicketEntity(ticketId, ticketPrice, canteenID);

        // Tạo task cho việc chèn Ticket
        Future<?> ticketTask = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ticketDAO.insertTicket(ticketEntity);  // Chèn Ticket vào cơ sở dữ liệu
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("DatabaseError", "Failed to insert ticket", e);
                }
            }
        });

        String myTicketId = "MyTicket_" + ticketId;
        String orderDate = new Date().toString();
        String paymentType = "Credit Card";
        String status = "Paid";

        MyTicketEntity myTicketEntity = new MyTicketEntity(
                myTicketId,
                "1",  // AccountID mặc định là 1
                ticketId,
                orderDate,
                ticketPrice,
                paymentType,
                status,
                null  // Nếu có mã QR thì điền vào đây
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

        // Chờ cho ticketTask hoàn thành trước khi tiếp tục với myTicketTask
        try {
            ticketTask.get();  // Chờ đợi tác vụ ticketTask hoàn thành
            myTicketTask.get();  // Sau đó chờ đợi myTicketTask hoàn thành
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.e("TaskError", "Error waiting for tasks", e);
        }
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
}
