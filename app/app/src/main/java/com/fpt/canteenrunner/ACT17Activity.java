package com.fpt.canteenrunner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.DTO.SpinnerItem;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import com.fpt.canteenrunner.Database.Model.CategoriesEntity;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ACT17Activity extends AppCompatActivity {
    private ImageView backbtn;
    private ImageView uploadImage;
    private Button addBtn;
    private EditText foodName, foodPrice,edtimageUrl;
    private Spinner category, cantin;
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act17);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();

    }


    private void bindingView() {
        backbtn = findViewById(R.id.back_btn);
        uploadImage = findViewById(R.id.ivUpload);
        foodName = findViewById(R.id.edtFoodName);
        foodPrice = findViewById(R.id.edtFoodPrice);
        category = findViewById(R.id.spCategory);
        addBtn = findViewById(R.id.btnAdd);
        cantin = findViewById(R.id.spCantin);
        edtimageUrl = findViewById(R.id.edtImageUrl);
        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());
        executorService.execute(() -> {
            // Load data from database
            List<CategoriesEntity> categories = db.categoriesDAO().getAllCategories();
            List<CanteenEntity> cantins = db.canteenDAO().getAllCanteens();
            List<SpinnerItem> cantinItems = new ArrayList<>();
            for (CanteenEntity cantin : cantins) {
                // Add data to Spinner
                cantinItems.add(new SpinnerItem(cantin.getCanteenID(), cantin.getCanteenName()));
            }
            List<SpinnerItem> cateItems = new ArrayList<>();

            for (CategoriesEntity cate : categories) {
                cateItems.add(new SpinnerItem(cate.getCategoryID(), cate.getName()));
            }
            runOnUiThread(() -> {
                // Set adapter for the category Spinner
                ArrayAdapter<SpinnerItem> categoryAdapter = new ArrayAdapter<>(ACT17Activity.this,
                        android.R.layout.simple_spinner_item, cateItems);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryAdapter);

                // Set adapter for the canteen Spinner
                ArrayAdapter<SpinnerItem> cantinAdapter = new ArrayAdapter<>(ACT17Activity.this,
                        android.R.layout.simple_spinner_item, cantinItems);
                cantinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cantin.setAdapter(cantinAdapter);
            });
        });


    }

    private void bindingAction() {
        backbtn.setOnClickListener(this::back);
        uploadImage.setOnClickListener(this::uploadImage);
        addBtn.setOnClickListener(this::addFood);
        edtimageUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something when text changes
                Glide.with(ACT17Activity.this)
                        .load(s.toString())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.broken)
                        .into(uploadImage);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something after text changes
            }
        });
    }

    private void addFood(View view) {
        executorService.execute(() -> {
            String name = foodName.getText().toString();
            String price = foodPrice.getText().toString();
            SpinnerItem cateItem = (SpinnerItem) category.getSelectedItem();
            SpinnerItem cantinItem = (SpinnerItem) cantin.getSelectedItem();
            String imageUrl = edtimageUrl.getText().toString();
            // Check if any required fields are empty or null
            if (name.isEmpty() || price.isEmpty() || cateItem == null || cantinItem == null) {
                runOnUiThread(() -> Toast.makeText(ACT17Activity.this, "Please fill all fields", Toast.LENGTH_SHORT).show());
                return;
            }

            // Parse the price and handle potential issues
            double parsedPrice = 0;
            try {
                parsedPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                runOnUiThread(() -> Toast.makeText(ACT17Activity.this, "Invalid price format", Toast.LENGTH_SHORT).show());
                return;
            }

            // Create and insert new food item
            FoodsEntity newFood = new FoodsEntity();
            newFood.setName(name);
            newFood.setCategoryID(cateItem.getId());
            newFood.setCanteenID(cantinItem.getId());
            newFood.setImageURL(imageUrl);
            db.foodsDAO().insertFood(newFood);

            // Get the newly inserted food ID
            String newFoodId = db.foodsDAO().getLastInsertedFoodID().getFoodID().toString();

            // Create and insert food price
            FoodPricesEntity newPrice = new FoodPricesEntity();
            newPrice.setFoodID(newFoodId);
            newPrice.setPrice(parsedPrice);
            db.foodPricesDAO().insertPrice(newPrice);

            runOnUiThread(() -> {
                foodName.setText("");
                foodPrice.setText("");
                setResult(RESULT_OK);
                finish();
                Toast.makeText(ACT17Activity.this, "Food added successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }


    private void uploadImage(View view) {
        String url = edtimageUrl.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter an image URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sử dụng Glide để tải ảnh từ URL
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading) // Ảnh tạm khi tải
                .error(R.drawable.broken)      // Ảnh hiển thị khi lỗi
                .into(uploadImage);
    }




    private void back(View view) {
        finish();
    }
}