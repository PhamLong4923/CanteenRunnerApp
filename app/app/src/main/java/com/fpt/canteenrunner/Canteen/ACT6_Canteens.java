package com.fpt.canteenrunner.Canteen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.Adapter.CanteenAdapter;
import com.fpt.canteenrunner.DTO.CanteenDTO;
import com.fpt.canteenrunner.DTO.MyHistoryDTO;
import com.fpt.canteenrunner.R;

import java.util.ArrayList;
import java.util.List;

public class ACT6_Canteens extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act6_canteens);

        // Sample data
        List<CanteenDTO> canteenList = new ArrayList<>();
        canteenList.add(new CanteenDTO("Canteen 1", "https://i.pinimg.com/736x/9c/32/21/9c3221b49038bd8c64947fc84db35a18--interior-design-offices-marble-counters.jpg", "1"));
        canteenList.add(new CanteenDTO("Canteen 2", "https://giathicong.com/wp-content/uploads/2023/05/thiet-ke-can-tin-2.jpg", "2"));
        canteenList.add(new CanteenDTO("Canteen 3", "https://th.bing.com/th/id/OIP.4dvMrEru9swHApwRoNFD1gHaEK?rs=1&pid=ImgDetMain", "3"));

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCanteens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CanteenAdapter(canteenList));
    }
}
