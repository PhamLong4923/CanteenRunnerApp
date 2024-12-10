package com.fpt.canteenrunner.Canteen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.ACT17Activity;
import com.fpt.canteenrunner.Activity.Payment.ActivitySelectTicket;
import com.fpt.canteenrunner.Adapter.CanteenAdapter;
import com.fpt.canteenrunner.Adapter.FoodAdapter;
import com.fpt.canteenrunner.Adapter.FoodAdapter2;
import com.fpt.canteenrunner.DTO.CanteenDTO;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.DTO.FoodDTO2;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.R;

public class ACT7_Canteen_Selected extends AppCompatActivity {
    private String canteenId;
    private Button btnRice;
    private Button btnWater;
    private Button btnDrink;
    private AppCompatImageButton btnTicket;
    private TextView canteenInfo;
    private ImageView canteenImage;
    private RecyclerView gridFood;
    private List<FoodDTO2> foodList;
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act7_canteen_selected);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        initCateenInfo(canteenId);
        initRecyclerView(canteenId, "1");
    }

    private void bindingView(){
        canteenInfo = findViewById(R.id.tvCanteenInfo);
        canteenImage = findViewById(R.id.slider);
        btnRice = findViewById(R.id.btnRice);
        btnWater = findViewById(R.id.btnWater);
        btnDrink = findViewById(R.id.btnDrink);
        btnTicket = findViewById(R.id.btnTicket);
        gridFood = findViewById(R.id.gridFood);

        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());

        canteenId = getIntent().getStringExtra("canteen_id");


    }
    private void bindingAction(){
        btnRice.setOnClickListener(view -> changeFoodCategory(view, "1"));
        btnWater.setOnClickListener(view -> changeFoodCategory(view, "2"));
        btnDrink.setOnClickListener(view -> changeFoodCategory(view, "3"));

        btnTicket.setOnClickListener(this::onClickTicket);

    }

    private void onClickTicket(View view) {
        Intent intent = new Intent(this, ActivitySelectTicket.class);
        intent.putExtra("canteen_id", canteenId);
        startActivity(intent);
    }

    private void changeFoodCategory(View view, String categoryId) {
        initRecyclerView(canteenId, categoryId);
    }

    private void initCateenInfo(String canteenId) {
        executorService.execute(() -> {
            CanteenEntity canteen = db.canteenDAO().getCanteenById(canteenId);
            runOnUiThread(() -> {
                canteenInfo.setText(canteen.CanteenName);
                Glide.with(this).load(Uri.parse(canteen.Image)).into(canteenImage);
            });
        });
    }



    private void initRecyclerView(String canteenId, String categoryId){
        executorService.execute(() -> {
            foodList = new ArrayList<>();
            // Sample data
            List<FoodsEntity> foodEntityList = new ArrayList<>();
            if(categoryId.equals("1")){
                foodEntityList.addAll(db.foodsDAO().getFoodsByCanteenIdAndCategoryId(canteenId, "1"));

            }

            if(categoryId.equals("2")){
                foodEntityList.addAll(db.foodsDAO().getFoodsByCanteenIdAndCategoryId(canteenId, "2"));
                foodEntityList.addAll(db.foodsDAO().getFoodsByCanteenIdAndCategoryId(canteenId, "3"));
                foodEntityList.addAll(db.foodsDAO().getFoodsByCanteenIdAndCategoryId(canteenId, "4"));
            }
            if(categoryId.equals("3")){
                foodEntityList.addAll(db.foodsDAO().getFoodsByCanteenIdAndCategoryId(canteenId, "5"));
                foodEntityList.addAll(db.foodsDAO().getFoodsByCanteenIdAndCategoryId(canteenId, "6"));

            }



            for (FoodsEntity entity : foodEntityList)  {
                FoodDTO2 dto = new FoodDTO2(entity.getImageURL(),entity.getCategoryID(),entity.getUpdateDate() , entity.getName());
                foodList.add(dto);
            }
            runOnUiThread(() -> {
                FoodAdapter2 adapter = new FoodAdapter2(foodList);
                gridFood.setAdapter(adapter);
                gridFood.setLayoutManager(new GridLayoutManager(this, 3));


            });
        });
    }
}