package com.fpt.canteenrunner.Canteen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.canteenrunner.R;

public class ProfileActivity extends AppCompatActivity {

    //private SessionManager sessionManager;
    private TextView fullname, phoneNum, email;
    private Button  btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        fullname = findViewById(R.id.tvFullName);
        phoneNum = findViewById(R.id.tvPhone);
        email = findViewById(R.id.tvEmail);
        btnChangePassword = findViewById(R.id.id_ChangePassword);
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

        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);

            // Retrieve the user ID from the session and pass it to ChangePasswordActivity
            if (sessionManager.getUser() != null) {
                int userId = sessionManager.getUser().getId(); // Assuming getId() retrieves the correct user ID
                intent.putExtra("USER_ID", userId); // Pass user ID as an extra
            }

            startActivity(intent);
        });
        */
    }
}