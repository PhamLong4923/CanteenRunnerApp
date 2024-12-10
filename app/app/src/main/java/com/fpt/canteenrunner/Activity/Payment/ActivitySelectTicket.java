package com.fpt.canteenrunner.Activity.Payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.Adapter.TicketAdapter;
import com.fpt.canteenrunner.Adapter.TicketPriceAdapter;
import com.fpt.canteenrunner.AuthenActivity.LoginActivity;
import com.fpt.canteenrunner.Canteen.ProfileActivity;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import com.fpt.canteenrunner.MainActivity;
import com.fpt.canteenrunner.R;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivitySelectTicket extends AppCompatActivity {

    private RecyclerView rvTicketPrices;
    private TicketAdapter ticketAdapter;
    String imageUrl, accountID, email_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act8_select_ticket);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email_user = preferences.getString("email", null);
        accountID = preferences.getString("accountID", null);
        if (email_user == null){
            Intent intent = new Intent(ActivitySelectTicket.this, LoginActivity.class);
            startActivity(intent);
        }

        rvTicketPrices = findViewById(R.id.rv_ticket_prices);
        // Nhận CanteenId từ Intent
        String canteenId = getIntent().getStringExtra("canteen_id");
        if (canteenId == null || canteenId.isEmpty()) {
            Toast.makeText(this, "Không có thông tin canteenId, cho canteenId = 2", Toast.LENGTH_SHORT).show();
            canteenId = "2"; // Giá trị mặc định
        }

        // Sử dụng Executor để thực hiện truy vấn
        Executor executor = Executors.newSingleThreadExecutor();
        String finalCanteenId = canteenId;
        executor.execute(() -> {
            // Lấy thông tin giá vé
            List<TicketEntity> tickets = CanteenRunnerDatabase.getInstance(this).ticketDAO().getTicketsByCanteen(finalCanteenId);
            imageUrl = CanteenRunnerDatabase.getInstance(this).canteenDAO().getCanteenImageById(finalCanteenId);
            // Kiểm tra nếu không có giá vé
            if (tickets == null || tickets.isEmpty()) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Căng tin này không bán vé", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            // Cập nhật giao diện RecyclerView
            runOnUiThread(() -> {
                ticketAdapter = new TicketAdapter(tickets, this::onTicketPriceSelected);
                rvTicketPrices.setLayoutManager(new LinearLayoutManager(this));
                rvTicketPrices.setAdapter(ticketAdapter);
            });
        });
        ImageView ivAvatar = findViewById(R.id.iv_avatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySelectTicket.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onTicketPriceSelected(TicketEntity selectedTicket) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Lấy thông tin vé từ CSDL
            TicketEntity ticket = CanteenRunnerDatabase.getInstance(this).ticketDAO().getTicketById(selectedTicket.getTicketID());

            if (ticket == null) {
                runOnUiThread(() -> Toast.makeText(this, "Không tìm thấy giá vé này", Toast.LENGTH_SHORT).show());
                return;
            }

            String canteenName = CanteenRunnerDatabase.getInstance(this).canteenDAO().getCanteenNameById(ticket.getCanteenID());

            // Chuyển về main thread để chuẩn bị dữ liệu và chuyển Activity
            runOnUiThread(() -> {
                Intent intent = new Intent(ActivitySelectTicket.this, ActivityPaymentMethod.class);
                intent.putExtra("Price", ticket.getTicketPrice());
                intent.putExtra("CanteenName", canteenName);
                intent.putExtra("CanteenID", ticket.getCanteenID());
                intent.putExtra("TicketID", ticket.getTicketID());
                intent.putExtra("ImageUrl", imageUrl);
                startActivity(intent);
            });
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email_user = preferences.getString("email", null);
        accountID = preferences.getString("accountID", null);
        if (email_user == null){
            Intent intent = new Intent(ActivitySelectTicket.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
