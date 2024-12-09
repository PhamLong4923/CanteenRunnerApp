package com.fpt.canteenrunner.Activity.Payment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;  // Thêm import Toast

import androidx.appcompat.app.AppCompatActivity;

import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Network.PaymentRetrofitInstance;
import com.fpt.canteenrunner.Network.api.PaymentApi;
import com.fpt.canteenrunner.Network.model.PaymentStatusResponse;
import com.fpt.canteenrunner.R;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityPayment extends AppCompatActivity {

    private ImageView ivQrCode;
    private TextView tvTicketInfo;
    private Button btnCheckPayment;

    Retrofit retrofit = PaymentRetrofitInstance.getRetrofitInstance();
    PaymentApi paymentApi = retrofit.create(PaymentApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act9half_payment);

        // Ánh xạ View
        ivQrCode = findViewById(R.id.iv_qr_code);
        tvTicketInfo = findViewById(R.id.tv_ticket_info);
        btnCheckPayment = findViewById(R.id.btn_check_payment);

        // Nhận thông tin từ Intent
        String foodName = getIntent().getStringExtra("FoodName");
        double selectedPrice = getIntent().getDoubleExtra("SelectedPrice", 0);

        // Hiển thị thông tin giao dịch
        tvTicketInfo.setText(String.format("Tên món: %s\nGiá: %,.0f VNĐ", foodName, selectedPrice));

        // Tạo URL VietQR
        String bankCode = "bidv"; // Mã ngân hàng
        String accountNumber = "2152978861"; // Số tài khoản ngân hàng
        String qrUrl = String.format(
                "https://img.vietqr.io/image/%s-%s-compact2.jpg?amount=%.0f&addInfo=%s",
                bankCode, accountNumber, selectedPrice, "Order" + foodName
        );

        // Hiển thị mã QR
        Glide.with(this)
                .load(qrUrl)
                .into(ivQrCode);

        // Xử lý nút kiểm tra thanh toán
        btnCheckPayment.setOnClickListener(v -> {
            // Hiển thị Toast khi bắt đầu kiểm tra thanh toán
            Toast.makeText(ActivityPayment.this, "Đang kiểm tra thanh toán...", Toast.LENGTH_SHORT).show();
            // Giả sử bạn có thông tin ticketId từ dữ liệu
            String ticketId = "your_ticket_id"; // Thay thế với ticketId thực tế
            checkPaymentStatus(ticketId);
        });
    }

    private void checkPaymentStatus(String ticketId) {
        // Gọi API kiểm tra trạng thái thanh toán
        paymentApi.getPaymentStatus(ticketId).enqueue(new Callback<PaymentStatusResponse>() {
            @Override
            public void onResponse(Call<PaymentStatusResponse> call, Response<PaymentStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    if ("Success".equalsIgnoreCase(status)) {
                        // Hiển thị thông báo thanh toán thành công
                        Toast.makeText(ActivityPayment.this, "Trạng thái: Đã thanh toán", Toast.LENGTH_SHORT).show();
                    } else {
                        // Hiển thị thông báo thanh toán chưa thành công
                        Toast.makeText(ActivityPayment.this, "Trạng thái: Chưa thanh toán", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Hiển thị thông báo lỗi kiểm tra thanh toán
                    Toast.makeText(ActivityPayment.this, "Trạng thái: Lỗi kiểm tra thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentStatusResponse> call, Throwable t) {
                // Hiển thị thông báo lỗi kết nối tới máy chủ
                Toast.makeText(ActivityPayment.this, "Trạng thái: Không thể kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
