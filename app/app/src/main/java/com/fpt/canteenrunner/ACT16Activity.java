package com.fpt.canteenrunner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.Adapter.FoodAdapter;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.CategoriesEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ACT16Activity extends AppCompatActivity implements FoodAdapter.OnFoodClickListener {
    private RecyclerView rvFood, rvLightMeal, rvDrink;
    private ImageView btnAddFood, backbtn;
    private List<FoodDTO> foodList = new ArrayList<>();
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;
    private String canteenID ;
    private String lightCateFoodID ="2" ;
    private String FoodCateID = "1";
    private String fastFoodCateID = "5";
    private String fastFoodCateID2 = "6";
    private String lightCateFoodID2 = "3";
    private String lightCateFoodID3 = "4";
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act16);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        initRecyclerView();
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        initRecyclerView();

                    }
                }
        );
    }

    private void initRecyclerView() {
        // Tạo LayoutManager riêng biệt cho từng RecyclerView
        rvFood.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvLightMeal.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvDrink.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        executorService.execute(() -> {
            foodList.clear();
            List<FoodsEntity> foods = db.foodsDAO().getFoodsByCanteen(canteenID);
            for (FoodsEntity food : foods) {
                String price = String.valueOf(db.foodPricesDAO().getPricesByFood1(food.getFoodID().toString()).getPrice());
                FoodDTO dto = new FoodDTO(
                        food.getFoodID().toString(),
                        food.getImageURL(),
                        food.getName(),
                        price,
                        food.getCategoryID()
                );
                foodList.add(dto);
            }
            List<FoodDTO> lightFoodList = new ArrayList<>();
            List<FoodDTO> drinkList = new ArrayList<>();
            List<FoodDTO> foodDTOlist = new ArrayList<>();
            for (FoodDTO food : foodList) {
                if (food.getCateID().equals(lightCateFoodID) || food.getCateID().equals(lightCateFoodID2) || food.getCateID().equals(lightCateFoodID3)) {
                    lightFoodList.add(food);
                } else if (food.getCateID().equals(FoodCateID)) {
                    foodDTOlist.add(food);
                } else if (food.getCateID().equals(fastFoodCateID) || food.getCateID().equals(fastFoodCateID2)) {
                    drinkList.add(food);
                }
            }


            runOnUiThread(() -> {
                rvFood.setAdapter(new FoodAdapter(foodDTOlist));
                rvLightMeal.setAdapter(new FoodAdapter(lightFoodList));
                rvDrink.setAdapter(new FoodAdapter(drinkList));

            });
        });
    }

    private void bindingAction() {
        btnAddFood.setOnClickListener(this::onClickAddFood);
        backbtn.setOnClickListener(this::onClickBack);
    }

    private void onClickBack(View view) {
        finish();
    }

    private void onClickAddFood(View view) {
        Intent intent = new Intent(this, ACT17Activity.class);
        activityResultLauncher.launch(intent);
    }

    private void bindingView() {
        rvFood = findViewById(R.id.rvFood);
        rvLightMeal = findViewById(R.id.rvLightFood);
        rvDrink = findViewById(R.id.rvFastFood);
        btnAddFood = findViewById(R.id.btnAddFood);
        backbtn = findViewById(R.id.back_btn);
        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email_user = preferences.getString("email", null);
        executorService.execute(() -> {
           String accountID = db.accountDAO().login(email_user).getAccountID();
            canteenID = db.canteenDAO().getCanteenByAccount(accountID).getCanteenID().toString();
        });
    }

    @Override
    public void onFoodClick(String foodId) {
        Intent intent = new Intent(this, ACT17dActivity.class);
        intent.putExtra("foodId", foodId);
        activityResultLauncher.launch(intent);
    }
}