package com.fpt.canteenrunner.Canteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.Adapter.CanteenAdapter;
import com.fpt.canteenrunner.Adapter.MyTicketAdapter;
import com.fpt.canteenrunner.DTO.CanteenDTO;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.CanteenDAO;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import com.fpt.canteenrunner.MainActivity;
import com.fpt.canteenrunner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ACT6_Canteens extends AppCompatActivity {

    private CanteenRunnerDatabase db;
    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private List<CanteenDTO> canteenList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act6_canteens);



        bindingView();
        bindingAction();

        initRecyclerView();
        ImageView ivAvatar = findViewById(R.id.iv_avatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACT6_Canteens.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bindingAction() {

    }

    private void openCanteenSelectedActivity(String canteenId) {
        Intent intent = new Intent(this, ACT7_Canteen_Selected.class);
        intent.putExtra("canteen_id", canteenId);
        startActivity(intent);
    }
    private void bindingView(){

        recyclerView = findViewById(R.id.rvCanteens);

        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());
    }

    private void initRecyclerView(){

        executorService.execute(() -> {
            // Sample data
            List<CanteenEntity> canteenEntityList = db.canteenDAO().getAllCanteens(); // Assuming this is already fetched


            for (CanteenEntity entity : canteenEntityList) {
                CanteenDTO dto = new CanteenDTO(entity.getCanteenName(), entity.getImage(), entity.getCanteenID());
                canteenList.add(dto);
            }
            runOnUiThread(() -> {
                CanteenAdapter adapter = new CanteenAdapter(canteenList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));


            });
        });

    }
}
