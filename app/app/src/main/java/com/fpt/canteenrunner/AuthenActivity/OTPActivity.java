package com.fpt.canteenrunner.AuthenActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OTPActivity extends AppCompatActivity {
    private EditText otpEditText;
    private String generatedOtp;
    private AccountDAO accountDAO;
    private AccountEntity resetUser;
    private ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);

        resetUser = (AccountEntity) getIntent().getSerializableExtra("user");
        accountDAO = CanteenRunnerDatabase.getInstance(this).accountDAO();
        executorService = Executors.newSingleThreadExecutor();

        otpEditText = findViewById(R.id.otp_input);
        Button verifyOtpButton = findViewById(R.id.btn_submit_otp);
        final AccountEntity user = (AccountEntity) getIntent().getSerializableExtra("User");
        generatedOtp = getIntent().getStringExtra("OTP");

        verifyOtpButton.setOnClickListener(v -> verifyOtp(user));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void verifyOtp(AccountEntity user) {
        String enteredOtp = otpEditText.getText().toString().trim();

        if (enteredOtp.isEmpty()) {
            showToast("Vui lòng nhập mã OTP!");
            return;
        }

        if (enteredOtp.equals(generatedOtp)) {
            showToast("Mã OTP xác thực thành công!");

            // Get the action type from the intent
            String actionType = (String) getIntent().getSerializableExtra("ActionType");

            if ("REGISTER".equalsIgnoreCase(actionType)) {
                new Thread(() -> {
                    CanteenRunnerDatabase db = CanteenRunnerDatabase.getInstance(OTPActivity.this);
                    if (db.accountDAO().insertAccount(user) != -1) {
                        Log.d("Account", "verifyOtp: " + accountDAO.getAll());
                        runOnUiThread(() -> {
                            showToast("Đăng ký thành công!");
                            Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> showToast("Đăng ký thất bại. Vui lòng thử lại!"));
                    }
                }).start();
            } else if ("RESET_PASSWORD".equalsIgnoreCase(actionType)) {
                // Cập nhật mật khẩu cho người dùng
                if (resetUser != null) {
                    showToast("Đặt lại mật khẩu thành công!");
                    Intent intent = new Intent(OTPActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("user", resetUser);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("Đặt lại mật khẩu thất bại. Vui lòng thử lại!");
                }
            }
        } else {
            showToast("Mã OTP không chính xác!");
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}