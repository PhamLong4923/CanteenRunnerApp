package com.fpt.canteenrunner.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.R;

public class ActivityPaymentMethod extends AppCompatActivity {

    private TextView tvFoodName, tvFoodPrice;
    private ImageView ivFoodImage;
    private Button btnPayWithPoints, btnPayWithBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act9_payment_method);

        String foodName = getIntent().getStringExtra("FoodName");
        String foodDescription = getIntent().getStringExtra("FoodDescription");
        String categoryName = getIntent().getStringExtra("CategoryName");
        String canteenName = getIntent().getStringExtra("CanteenName");
        String imageUrl = getIntent().getStringExtra("ImageURL");
        double selectedPrice = getIntent().getDoubleExtra("SelectedPrice", 0);

        // Ánh xạ các View
        btnPayWithPoints = findViewById(R.id.btn_pay_with_points);
        btnPayWithBank = findViewById(R.id.btn_pay_with_bank);
        TextView tvFoodName = findViewById(R.id.tv_food_name);
        TextView tvFoodDescription = findViewById(R.id.tv_food_description);
        TextView tvCategoryName = findViewById(R.id.tv_category_name);
        TextView tvCanteenName = findViewById(R.id.tv_canteen_name);
        TextView tvFoodPrice = findViewById(R.id.tv_food_price);
        ImageView ivFoodImage = findViewById(R.id.iv_food_image);

        tvFoodName.setText("Tên: " + foodName);
        tvFoodDescription.setText("Mô tả: " + foodDescription);
        tvCategoryName.setText("Danh mục: " + categoryName);
        tvCanteenName.setText("Căng tin: " + canteenName);
        tvFoodPrice.setText(String.format("Giá: %.0f", selectedPrice));

        // Hiển thị hình ảnh (sử dụng Glide hoặc Picasso)
        Glide.with(this).load(imageUrl).into(ivFoodImage);

        // Gắn sự kiện cho các nút thanh toán
        btnPayWithPoints.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng thanh toán bằng điểm chưa hỗ trợ.", Toast.LENGTH_SHORT).show();
        });

        btnPayWithBank.setOnClickListener(v -> {
            // Chuyển sang màn hình thanh toán ngân hàng
            Intent bankIntent = new Intent(ActivityPaymentMethod.this, ActivityPayment.class);
            bankIntent.putExtra("FoodName", foodName);
            bankIntent.putExtra("SelectedPrice", selectedPrice);
            startActivity(bankIntent);
        });
    }
}
