package com.fpt.canteenrunner.AuthenActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.AccountDAO;
import com.fpt.canteenrunner.Database.Model.AccountEntity;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.Utils.SendOtp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private Button btnSendOtp;
    private TextView tvBackToLogin;
    private AccountDAO accountDAO;
    private ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);


        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        accountDAO = CanteenRunnerDatabase.getInstance(this).accountDAO();
        executorService = Executors.newSingleThreadExecutor();

        btnSendOtp.setOnClickListener(v -> CheckAndSend());
        tvBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void CheckAndSend() {
        String phone = etPhoneNumber.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }
        executorService.execute(() -> {
        AccountEntity user = accountDAO.CheckPhoneNumber(phone);
        runOnUiThread(() -> {
            if (user != null) {
                String otp = SendOtp.generateOTP(); // Tạo mã OTP
                SendOtp.sendOTP(ForgotPasswordActivity.this, phone, otp);
                // Chuyển sang OTPActivity và truyền thông tin user
                Intent intent = new Intent(ForgotPasswordActivity.this, OTPActivity.class);
                intent.putExtra("user", user); // Truyền User object
                intent.putExtra("OTP", otp);
                intent.putExtra("ActionType", "RESET_PASSWORD");
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Số điện thoại không tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });
    });
    }
}