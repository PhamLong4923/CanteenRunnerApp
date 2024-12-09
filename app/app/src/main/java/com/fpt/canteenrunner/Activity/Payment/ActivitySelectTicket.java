package com.fpt.canteenrunner.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fpt.canteenrunner.Adapter.TicketPriceAdapter;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.R;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivitySelectTicket extends AppCompatActivity {

    private RecyclerView rvTicketPrices;
    private TicketPriceAdapter ticketPriceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act8_select_ticket);

        rvTicketPrices = findViewById(R.id.rv_ticket_prices);

        // Nhận FoodID từ Intent
        String foodId = getIntent().getStringExtra("FoodID");
        if (foodId == null || foodId.isEmpty()) {
            Toast.makeText(this, "Không có thông tin FoodID, cho FoodID = 17", Toast.LENGTH_SHORT).show();
            foodId = "17"; // Giá trị mặc định
        }

        // Sử dụng Executor để thực hiện truy vấn
        Executor executor = Executors.newSingleThreadExecutor();
        String finalFoodId = foodId;
        executor.execute(() -> {
            // Lấy thông tin giá vé
            List<FoodPricesEntity> prices = CanteenRunnerDatabase.getInstance(this).foodPricesDAO().getPricesByFood(finalFoodId);

            // Kiểm tra nếu không có giá vé
            if (prices == null || prices.isEmpty()) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Không có giá vé cho món ăn này", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            // Cập nhật giao diện RecyclerView
            runOnUiThread(() -> {
                ticketPriceAdapter = new TicketPriceAdapter(prices, this::onTicketPriceSelected);
                rvTicketPrices.setLayoutManager(new LinearLayoutManager(this));
                rvTicketPrices.setAdapter(ticketPriceAdapter);
            });
        });
    }

    private void onTicketPriceSelected(FoodPricesEntity selectedPrice) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Lấy thông tin món ăn từ CSDL
            FoodsEntity food = CanteenRunnerDatabase.getInstance(this).foodsDAO().getFoodById(selectedPrice.getFoodID());

            if (food == null) {
                runOnUiThread(() -> Toast.makeText(this, "Không tìm thấy món ăn này", Toast.LENGTH_SHORT).show());
                return;
            }

            // Lấy thêm thông tin Category và Canteen
            String categoryName = CanteenRunnerDatabase.getInstance(this).categoriesDAO().getCategoryNameById(food.getCategoryID());
            String canteenName = CanteenRunnerDatabase.getInstance(this).canteenDAO().getCanteenNameById(food.getCanteenID());

            // Chuyển về main thread để chuẩn bị dữ liệu và chuyển Activity
            runOnUiThread(() -> {
                Intent intent = new Intent(ActivitySelectTicket.this, ActivityPaymentMethod.class);
                intent.putExtra("FoodName", food.getName());
                intent.putExtra("FoodDescription", food.getDescription());
                intent.putExtra("CategoryName", categoryName);
                intent.putExtra("CanteenName", canteenName);
                intent.putExtra("ImageURL", food.getImageURL());
                intent.putExtra("SelectedPrice", selectedPrice.getPrice()); // Đưa giá vé được chọn vào Intent
                startActivity(intent);
            });
        });
    }
}
