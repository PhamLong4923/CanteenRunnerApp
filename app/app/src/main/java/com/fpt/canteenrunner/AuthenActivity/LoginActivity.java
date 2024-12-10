package com.fpt.canteenrunner.AuthenActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fpt.canteenrunner.Canteen.ProfileActivity;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.AccountDAO;
import com.fpt.canteenrunner.Database.Model.AccountEntity;
import com.fpt.canteenrunner.MainActivity;
import com.fpt.canteenrunner.R;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button btnRedirectRegister, btnForgotPassword,btnLogin;

    private AccountDAO accountDAO;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRedirectRegister = findViewById(R.id.btnRedirectRegister);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);

        accountDAO = CanteenRunnerDatabase.getInstance(this).accountDAO();
        executorService = Executors.newSingleThreadExecutor();

        btnLogin.setOnClickListener(v -> handleLogin());

        btnRedirectRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleLogin() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {

            AccountEntity accountEntity = accountDAO.login(email);

            runOnUiThread(() -> {
                if (accountEntity != null) {
                    // So sánh mật khẩu đã nhập với mật khẩu đã mã hóa trong cơ sở dữ liệu
                    if (BCrypt.checkpw(password, accountEntity.getPassword())) {
                        // Mật khẩu đúng
                        String token = generateJWT(accountEntity);
                        saveToken(token,accountEntity);
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Mật khẩu sai
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tài khoản không tồn tại
                    Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private String generateJWT(AccountEntity accountEntity) {
        // Lấy secret_key từ BuildConfig
        // Định nghĩa thuật toán mã hóa (HMAC256 trong trường hợp này)
        Algorithm algorithm = Algorithm.HMAC256("4e704ae56750cac77a2ce92342a7e7acd4070697ca98a208cb2c134c8ed043bb");

        // Tạo JWT với thông tin tài khoản và thời gian hết hạn (ví dụ: 1 giờ)
        String token = JWT.create()
                .withJWTId(accountEntity.AccountID)
                .withSubject(accountEntity.getEmail()) // Sử dụng email như là subject trong JWT
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Thời gian hết hạn 1 giờ
                .sign(algorithm); //xác thực rằng token chưa bị thay đổi sau khi tạo ra.

        return token;
    }

    private void saveToken(String token,AccountEntity accountEntity) {
        // Lưu JWT vào SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("jwt_token", token);
        editor.putString("email", accountEntity.getEmail());
        editor.putString("accountID", accountEntity.getAccountID());
        System.out.println("Bên Login save Email : " + accountEntity.getEmail());
        editor.apply();
    }
}