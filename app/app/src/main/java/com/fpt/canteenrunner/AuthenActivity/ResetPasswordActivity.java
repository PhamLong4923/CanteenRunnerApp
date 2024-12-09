package com.fpt.canteenrunner.AuthenActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText etNewPassword, etConfirmPassword;
    private Button btnResetPassword;
    private AccountEntity user;
    private AccountDAO accountDAO;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        accountDAO = CanteenRunnerDatabase.getInstance(this).accountDAO();
        executorService = Executors.newSingleThreadExecutor();

        user = (AccountEntity) getIntent().getSerializableExtra("user");

        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(v -> onResetPasswordClicked());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void onResetPasswordClicked( ) {
        CanteenRunnerDatabase db = CanteenRunnerDatabase.getInstance(ResetPasswordActivity.this);
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mã hóa mật khẩu mới
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Sử dụng ExecutorService để thực hiện thao tác cơ sở dữ liệu trong background thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            // Cập nhật mật khẩu người dùng trong cơ sở dữ liệu
            user.setPassword(hashedPassword);
            long result = db.accountDAO().updatePassword(user);

            // Cập nhật UI sau khi hoàn thành thao tác cơ sở dữ liệu
            runOnUiThread(() -> {
                if (result > 0) {
                    Toast.makeText(this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng activity
                } else {
                    Toast.makeText(this, "Đặt lại mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}