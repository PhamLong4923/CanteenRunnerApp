package com.fpt.canteenrunner.Header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fpt.canteenrunner.Canteen.ProfileActivity;
import com.fpt.canteenrunner.R;

public class HeaderActivity extends AppCompatActivity {

    private TextView tvUsername;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        // Ánh xạ TextView và ImageView
        tvUsername = findViewById(R.id.tv_username);
        ImageView ivAvatar = findViewById(R.id.iv_avatar);

        // Lấy SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = preferences.getString("email", "Người dùng"); // Giá trị mặc định là "Người dùng"
        System.out.println("Username trong Header: " + username);

        // Hiển thị tên người dùng
        tvUsername.setText(username);

        // Xử lý sự kiện nhấn vào avatar
        ivAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(HeaderActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại dữ liệu (nếu cần)
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = preferences.getString("email", "Người dùng");
        tvUsername.setText(username);
        System.out.println("HeaderActivity onResume");
    }
}
