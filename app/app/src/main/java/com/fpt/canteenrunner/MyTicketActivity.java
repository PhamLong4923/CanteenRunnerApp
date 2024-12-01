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

import java.util.ArrayList;
import java.util.List;

public class MyTicketActivity extends AppCompatActivity {
    private ImageView backBtn;
    private RecyclerView tiketList;
    private List<MyTicketDTO> data= new ArrayList<>();

    private void fakeData() {
        data.add(new MyTicketDTO("Cơm gà 1", "1/12/2024", "30:00"));
        data.add(new MyTicketDTO("Cơm gà 2", "1/12/2024", "32:00"));
        data.add(new MyTicketDTO("Cơm gà 3", "12/12/2021", "33:00"));
        data.add(new MyTicketDTO("Cơm gà 4", "12/12/2021", "34:00"));
        data.add(new MyTicketDTO("Cơm gà 5", "12/12/2021", "35:00"));
        data.add(new MyTicketDTO("Cơm gà 6", "12/12/2021", "36:00"));
        data.add(new MyTicketDTO("Cơm gà 7", "12/12/2021", "37:00"));
    }
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
        fakeData();
        bindingView();
        bindingAction();
        initRecyclerView();
    }

    private void initRecyclerView() {
        MyTicketAdapter adapter = new MyTicketAdapter(data);
        tiketList.setAdapter(adapter);
        tiketList.setLayoutManager(new LinearLayoutManager(this));

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
    }


}