package com.fpt.canteenrunner.Canteen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.canteenrunner.AuthenActivity.ResetPasswordActivity;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.AccountDAO;
import com.fpt.canteenrunner.Database.Model.AccountEntity;
import com.fpt.canteenrunner.MyHistoryActivity;
import com.fpt.canteenrunner.MyTicketActivity;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.changepasswordActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullname, phoneNum, email, point;
    private Button  btnChangePassword,btnMyTicket, btnMyPresentTicket;
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
        btnMyTicket = findViewById(R.id.id_MyTicket2);
        btnMyPresentTicket =findViewById(R.id.id_MyTicket3);
        accountDAO = CanteenRunnerDatabase.getInstance(this).accountDAO();
        executorService = Executors.newSingleThreadExecutor();
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email_user = preferences.getString("email", null);
        System.out.println("Email bên Profile nè: " + email_user);
        executorService.execute(() -> {
            AccountEntity accountEntity = accountDAO.login(email_user);
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
                AccountEntity accountEntity = accountDAO.login(email_user);
                runOnUiThread(() -> {
                    if (accountEntity != null) {
                        Intent intent = new Intent(ProfileActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("user", accountEntity); // Truyền AccountEntity qua Intent
                        startActivity(intent);
                    }
                });
            });
        });

        btnMyTicket.setOnClickListener(v -> {
            executorService.execute(() -> {
                AccountEntity accountEntity = accountDAO.login(email_user);
                runOnUiThread(() -> {
                    if (accountEntity != null) {
                        Intent intent = new Intent(ProfileActivity.this, MyHistoryActivity.class);
                        intent.putExtra("user", accountEntity);
                        startActivity(intent);
                    }
                });
            });
        });

        btnMyPresentTicket.setOnClickListener(v -> {
            executorService.execute(() -> {
                AccountEntity accountEntity = accountDAO.login(email_user);
                runOnUiThread(() -> {
                    if (accountEntity != null) {
                        Intent intent = new Intent(ProfileActivity.this, MyTicketActivity.class);
                        intent.putExtra("user", accountEntity);
                        startActivity(intent);
                    }
                });
            });
        });
    }
}