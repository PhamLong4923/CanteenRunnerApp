package com.fpt.canteenrunner.Canteen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.AccountDAO;
import com.fpt.canteenrunner.Database.Model.AccountEntity;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.changepasswordActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    //private SessionManager sessionManager;
    private TextView fullname, phoneNum, email, point;
    private Button  btnChangePassword;
    private AccountDAO accountDAO;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        fullname = findViewById(R.id.tvFullName);
        phoneNum = findViewById(R.id.tvPhone);
        email = findViewById(R.id.tvEmail);
        btnChangePassword = findViewById(R.id.id_ChangePassword);
        accountDAO = CanteenRunnerDatabase.getInstance(this).accountDAO();
        executorService = Executors.newSingleThreadExecutor();
        /*
        sessionManager = new SessionManager(this);

        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Retrieve user details from session and display
            User user = sessionManager.getUser();
            if (user != null) {
                fullname.setText(user.getFullName());
                phoneNum.setText(user.getPhoneNumber());
                email.setText(user.getEmail());
            }
        }

         */
        String email_user = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        System.out.println("Email + Pass " + email_user + " " + password);
        executorService.execute(() -> {
            AccountEntity accountEntity = accountDAO.login(email_user,password);
            runOnUiThread(() -> {
                if (accountEntity != null) {
                    point =  findViewById(R.id.ed_bonus_point);
                    point.setText(String.valueOf(accountEntity.getScore()));
                    fullname =  findViewById(R.id.tvFullName);
                    fullname.setText(accountEntity.getUsername());
                    phoneNum = findViewById(R.id.tvPhone);
                    phoneNum.setText(accountEntity.getPhoneNumber());
                    email = findViewById(R.id.tvEmail);
                    email.setText(accountEntity.getEmail());
                }
            });
        });

        btnChangePassword.setOnClickListener(v -> {
            executorService.execute(() -> {
                AccountEntity accountEntity = accountDAO.login(email_user, password);
                runOnUiThread(() -> {
                    if (accountEntity != null) {
                        Intent intent = new Intent(ProfileActivity.this, changepasswordActivity.class);
                        intent.putExtra("email", accountEntity.getEmail());
                        intent.putExtra("password", accountEntity.getPassword());
                        startActivity(intent);
                    }
                });
            });
        });
    }
}