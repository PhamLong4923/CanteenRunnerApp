package com.fpt.canteenrunner;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ACT17dActivity extends AppCompatActivity {
    private ImageView backbtn;
    private ImageView uploadImage;
    private Button updateBtn, deleteBtn;
    private EditText foodName, foodPrice, edtimageUrl;
    private Spinner category, cantin;
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act17d);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }

    private void back(View view) {
        finish();
    }

    private void bindingAction() {
        backbtn.setOnClickListener(this::back);
        updateBtn.setOnClickListener(this::update);
        deleteBtn.setOnClickListener(this::delete);


        edtimageUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something when text changes
                Glide.with(ACT17dActivity.this)
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

    private void delete(View view) {
        executorService.execute(() -> {
            // Delete food
            db.foodPricesDAO().deletePrice(Integer.parseInt(getIntent().getStringExtra("foodId")));
            db.foodsDAO().deleteFood(Integer.parseInt(getIntent().getStringExtra("foodId")));
            runOnUiThread(() -> {
                Toast.makeText(ACT17dActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            });
        });

    }

    private void update(View view) {
        executorService.execute(() -> {
            // Update food
            String name = foodName.getText().toString();
            String price = foodPrice.getText().toString();
            SpinnerItem cateItem = (SpinnerItem) category.getSelectedItem();
            SpinnerItem cantinItem = (SpinnerItem) cantin.getSelectedItem();
            String imageUrl = edtimageUrl.getText().toString();
            // Check if any required fields are empty or null
            if (name.isEmpty() || price.isEmpty() || cateItem == null || cantinItem == null) {
                runOnUiThread(() -> Toast.makeText(ACT17dActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show());
                return;
            }
            FoodsEntity food = db.foodsDAO().getFoodById(getIntent().getStringExtra("foodId"));
            food.setName(name);
            if (!imageUrl.isEmpty()) {
                food.setImageURL(imageUrl);
            }
            food.setCategoryID(cateItem.getId());
            food.setCanteenID(cantinItem.getId());
            // Get the current date and format it
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            food.setUpdateDate(currentDate);
            db.foodsDAO().updateFood(food);
            FoodPricesEntity foodPrices = db.foodPricesDAO().getPricesByFood1(String.valueOf(food.getFoodID()));
            foodPrices.setPrice(Double.parseDouble(price));
            db.foodPricesDAO().updatePrice(foodPrices);
            setResult(RESULT_OK);
            finish();
        });
    }

    private void bindingView() {
        backbtn = findViewById(R.id.back_btn);
        uploadImage = findViewById(R.id.ivUpload);
        foodName = findViewById(R.id.edtFoodName);
        foodPrice = findViewById(R.id.edtFoodPrice);
        edtimageUrl = findViewById(R.id.edtImageUrl);
        category = findViewById(R.id.spCategory);
        cantin = findViewById(R.id.spCantin);
        updateBtn = findViewById(R.id.btnUpdate);
        deleteBtn = findViewById(R.id.btnDelete);
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            // Load data from database
            FoodsEntity food = db.foodsDAO().getFoodById(getIntent().getStringExtra("foodId"));
            FoodPricesEntity foodPrices = db.foodPricesDAO().getPricesByFood1(String.valueOf(food.getFoodID()));
            runOnUiThread(() -> {
                foodName.setText(food.getName());
                foodPrice.setText(String.valueOf(foodPrices.getPrice()));
                Glide.with(ACT17dActivity.this)
                        .load(food.getImageURL())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.broken)
                        .into(uploadImage);
            });
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
                ArrayAdapter<SpinnerItem> categoryAdapter = new ArrayAdapter<>(ACT17dActivity.this,
                        android.R.layout.simple_spinner_item, cateItems);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryAdapter);

                // Set adapter for the canteen Spinner
                ArrayAdapter<SpinnerItem> cantinAdapter = new ArrayAdapter<>(ACT17dActivity.this,
                        android.R.layout.simple_spinner_item, cantinItems);
                cantinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cantin.setAdapter(cantinAdapter);
                // Select item in category Spinner by key
                for (int i = 0; i < cateItems.size(); i++) {
                    if (cateItems.get(i).getId().equals(food.getCategoryID())) {
                        category.setSelection(i);
                        break;
                    }
                }
                // Select item in canteen Spinner by key
                for (int i = 0; i < cantinItems.size(); i++) {
                    if (cantinItems.get(i).getId().equals(food.getCanteenID())) {
                        cantin.setSelection(i);
                        break;
                    }
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindingView();
    }
}