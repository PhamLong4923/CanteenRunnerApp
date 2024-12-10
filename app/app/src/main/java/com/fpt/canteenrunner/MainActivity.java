package com.fpt.canteenrunner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.Adapter.FoodAdapter;
import com.fpt.canteenrunner.Canteen.ACT6_Canteens;
import com.fpt.canteenrunner.Canteen.ProfileActivity;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.CategoriesDAO;
import com.fpt.canteenrunner.Database.DAO.FoodPricesDAO;
import com.fpt.canteenrunner.Database.DAO.FoodsDAO;
import com.fpt.canteenrunner.Database.Model.CategoriesEntity;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.Header.HeaderActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private FoodsDAO foodsDAO;
    private TabLayout tabLayout;
    private CategoriesDAO categoryDAO;
    private FoodPricesDAO foodPricesDAO;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnMenu = findViewById(R.id.fabOptions);
        btnMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, btnMenu);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_options, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.option_settings) {
                    Toast.makeText(MainActivity.this, "Settings selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.option_canteen) {
                    Intent intent = new Intent(MainActivity.this, ACT6_Canteens.class);
                    startActivity(intent);
                }
                return true;
            });
            popupMenu.show();
        });

        tabLayout = findViewById(R.id.tabLayout);
        categoryDAO = CanteenRunnerDatabase.getInstance(this).categoriesDAO();
        foodsDAO = CanteenRunnerDatabase.getInstance(this).foodsDAO();
        foodPricesDAO = CanteenRunnerDatabase.getInstance(this).foodPricesDAO();
        loadCategoriesFromDatabase();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String categoryName = tab.getText().toString();
                System.out.println("cateName: "+ categoryName);
                loadFoodsByCategory(categoryName);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        ImageView ivAvatar = findViewById(R.id.iv_avatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    private void loadCategoriesFromDatabase() {
        executorService.execute(() -> {
            List<CategoriesEntity> categories = categoryDAO.getAllCategories();
            System.out.println("Size of category: " + categories.size());
            runOnUiThread(() -> {
                for (CategoriesEntity category : categories) {
                    TabLayout.Tab tab = tabLayout.newTab();
                    tab.setText(category.getName());
                    tabLayout.addTab(tab);
                }
            });
        });
    }
    private void loadFoodsByCategory(String categoryName) {
        executorService.execute(() -> {
            CategoriesEntity category = categoryDAO.getCategoryNameByName(categoryName);
            if (category != null) {
                List<FoodsEntity> foodsEntities = foodsDAO.getFoodsByCategoryId(category.getCategoryID());
                List<FoodDTO> foodDTOs = convertToFoodDTOs(foodsEntities);
                runOnUiThread(() -> displayFoods(foodDTOs));
            }
        });
    }

    private List<FoodDTO> convertToFoodDTOs(List<FoodsEntity> foodsEntities) {
        List<FoodDTO> foodDTOs = new ArrayList<>();
        for (FoodsEntity entity : foodsEntities) {
            FoodPricesEntity foodPrices = foodPricesDAO.getPricesByFood1(String.valueOf(entity.getFoodID()));
            double price = foodPrices.getPrice();
            FoodDTO dto = new FoodDTO(entity.getImageURL(), entity.getName(), String.valueOf(price), String.valueOf(entity.getCategoryID()));
            foodDTOs.add(dto);
        }
        return foodDTOs;
    }

    private void displayFoods(List<FoodDTO> foods) {
        RecyclerView recyclerView = findViewById(R.id.foodList);
        FoodAdapter adapter = new FoodAdapter(foods);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }


}