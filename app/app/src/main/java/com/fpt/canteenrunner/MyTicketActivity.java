package com.fpt.canteenrunner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.Adapter.MyTicketAdapter;
import com.fpt.canteenrunner.DTO.MyHistoryDTO;
import com.fpt.canteenrunner.DTO.MyTicketDTO;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTicketActivity extends AppCompatActivity {
    private ImageView backBtn;
    private RecyclerView tiketList;
    private List<MyTicketDTO> data = new ArrayList<>();
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;
    private String accountID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_ticket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        initRecyclerView();
    }

    private void initRecyclerView() {
        executorService.execute(() -> {
            List<MyTicketEntity> tiket = db.myTicketDAO().getMyTicketsWithTicketByAccount(accountID);
            for (MyTicketEntity ticket : tiket) {
                TicketEntity ticketEntity = db.ticketDAO().getTicketById(ticket.getTicketID());
                String price = String.valueOf(ticketEntity.getTicketPrice());
                MyTicketDTO dto = new MyTicketDTO(
                        ticket.getMyTicketID(),
                        ticket.getOrderDate(),
                        price
                );

                data.add(dto);
            }
            runOnUiThread(() -> {
                MyTicketAdapter adapter = new MyTicketAdapter(data);
                tiketList.setAdapter(adapter);
                tiketList.setLayoutManager(new LinearLayoutManager(this));
            });
        });

    }
    private void bindingAction() {
        backBtn.setOnClickListener(this::back);

    }
    private void back(View view) {
        finish();
    }
    private void bindingView() {
        backBtn = findViewById(R.id.back_btn);
        tiketList = findViewById(R.id.ticket_list);
        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());
    }
}