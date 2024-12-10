package com.fpt.canteenrunner.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpt.canteenrunner.Network.api.CreateOrder;
import com.fpt.canteenrunner.R;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ActivityPaymentZaloPay extends AppCompatActivity {
    private Button btnPay;
    private TextView txtAmount;
    private double amount = 10000; // Số tiền thanh toán (VD: 1 triệu)
    private final ExecutorService executorService = Executors.newFixedThreadPool(4); // Tạo ExecutorService

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act9half_payment_zalopay);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        btnPay = findViewById(R.id.btnPay);
        txtAmount = findViewById(R.id.txtAmount);

        // Hiển thị số tiền
        txtAmount.setText(String.format("%.0f VND", amount));

        btnPay.setOnClickListener(view -> {
            // Xử lý gọi API trong luồng nền
            executorService.submit(() -> {
                try {
                    // Gọi API tạo đơn hàng
                    CreateOrder orderApi = new CreateOrder();
                    JSONObject orderData = orderApi.createOrder(String.valueOf((int) amount));

                    String returnCode = orderData.getString("return_code");
                    if ("1".equals(returnCode)) {
                        String token = orderData.getString("zp_trans_token");

                        // Thực hiện thanh toán trên Main Thread
                        runOnUiThread(() -> ZaloPaySDK.getInstance().payOrder(
                                ActivityPaymentZaloPay.this,
                                token,
                                "demozpdk://app",
                                new PayOrderListener() {
                                    @Override
                                    public void onPaymentSucceeded(String transactionId, String transToken, String appTransId) {
                                        Toast.makeText(ActivityPaymentZaloPay.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onPaymentCanceled(String zpTransToken, String appTransId) {
                                        Toast.makeText(ActivityPaymentZaloPay.this, "Đã hủy thanh toán", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransId) {
                                        Toast.makeText(ActivityPaymentZaloPay.this, "Lỗi thanh toán", Toast.LENGTH_SHORT).show();
                                    }
                                }));
                    } else {
                        // Hiển thị lỗi tạo đơn hàng trên Main Thread
                        runOnUiThread(() -> Toast.makeText(ActivityPaymentZaloPay.this, "Tạo đơn hàng thất bại", Toast.LENGTH_SHORT).show());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(ActivityPaymentZaloPay.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đảm bảo ExecutorService được tắt khi không còn sử dụng
        executorService.shutdown();
    }
}
