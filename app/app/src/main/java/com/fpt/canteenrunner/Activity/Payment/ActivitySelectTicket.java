package com.fpt.canteenrunner.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fpt.canteenrunner.Adapter.TicketPriceAdapter;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
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
            List<FoodPricesEntity> prices = CanteenRunnerDatabase.getInstance(this).foodPricesDAO().getPricesByFood(finalFoodId);

            // Chuyển về main thread để cập nhật giao diện
            runOnUiThread(() -> {
                if (prices == null || prices.isEmpty()) {
                    Toast.makeText(this, "Không có giá vé cho món ăn này", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                // Cấu hình RecyclerView
                ticketPriceAdapter = new TicketPriceAdapter(prices, this::onTicketPriceSelected);
                rvTicketPrices.setLayoutManager(new LinearLayoutManager(this));
                rvTicketPrices.setAdapter(ticketPriceAdapter);
            });
        });
    }


    private void onTicketPriceSelected(FoodPricesEntity selectedPrice) {
        // Chuyển sang màn hình thanh toán
        Intent intent = new Intent(ActivitySelectTicket.this, ActivityPayment.class);
        intent.putExtra("SelectedPrice", selectedPrice.getPrice());
        startActivity(intent);
    }
}
