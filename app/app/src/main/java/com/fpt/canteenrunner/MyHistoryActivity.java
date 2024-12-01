package com.fpt.canteenrunner;

import android.app.DatePickerDialog;
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

import com.fpt.canteenrunner.Adapter.MyHistoryAdapter;
import com.fpt.canteenrunner.DTO.MyHistoryDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyHistoryActivity extends AppCompatActivity {
    private ImageView backBtn;
    private EditText historyDate;
    private Button clearFilter;
    private ToggleButton toggleButton;
    private RecyclerView historyList;
    private List<MyHistoryDTO> data= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_history);
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



    private void fakeData() {
        data.add(new MyHistoryDTO("Cơm gà", "1/12/2024", false));
        data.add(new MyHistoryDTO("Cơm gà", "1/12/2024", true));
        data.add(new MyHistoryDTO("Cơm gà", "12/12/2021", false));
        data.add(new MyHistoryDTO("Cơm gà", "12/12/2021", true));
        data.add(new MyHistoryDTO("Cơm gà", "12/12/2021", true));
        data.add(new MyHistoryDTO("Cơm gà", "12/12/2021", true));
        data.add(new MyHistoryDTO("Cơm gà", "12/12/2021", true));
    }
    private void filterData() {
        String selectedDate = historyDate.getText().toString();
        boolean isUsed = toggleButton.isChecked();

        List<MyHistoryDTO> filteredData = new ArrayList<>();

        for (MyHistoryDTO item : data) {
            boolean matchDate = selectedDate.isEmpty() || item.getBuyDate().equals(selectedDate);
            boolean matchStatus = isUsed == item.getStatus();

            if (matchDate && matchStatus) {
                filteredData.add(item);
            }
        }

        updateRecyclerView(filteredData);
    }
    private void updateRecyclerView(List<MyHistoryDTO> filteredData) {
        MyHistoryAdapter adapter = new MyHistoryAdapter(filteredData);
        historyList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void bindingAction() {
        backBtn.setOnClickListener(this::back);
        toggleButton.setOnClickListener(this::toggle);
        historyDate.setOnClickListener(this::filterByDate);
        clearFilter.setOnClickListener(this::clearFilter);
    }

    private void clearFilter(View view) {
        historyDate.setText("");
        toggleButton.setChecked(false);
        updateRecyclerView(data);
    }

    private void filterByDate(View view) {
        showDatePickerDialog();
    }
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year1, month1, dayOfMonth) -> {
            historyDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
            filterData();
        }, year, month, day);
        datePickerDialog.show();
    }
    private void toggle(View view) {
        filterData();
    }

    private void back(View view) {
        finish();
    }

    private void bindingView() {
        backBtn = findViewById(R.id.back_btn);
        historyDate = findViewById(R.id.history_date);
        toggleButton = findViewById(R.id.toggleButton);
        historyList = findViewById(R.id.history_list);
        clearFilter = findViewById(R.id.clear_btn);

    }
    private void initRecyclerView() {
        MyHistoryAdapter adapter = new MyHistoryAdapter(data);
        historyList.setAdapter(adapter);
        historyList.setLayoutManager(new LinearLayoutManager(this));

    }
}