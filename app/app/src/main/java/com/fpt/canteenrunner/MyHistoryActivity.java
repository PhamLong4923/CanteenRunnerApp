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
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyHistoryActivity extends AppCompatActivity {
    private ImageView backBtn;
    private EditText historyDate;
    private Button clearFilter;
    private ToggleButton toggleButton;
    private RecyclerView historyList;
    private List<MyHistoryDTO> data = new ArrayList<>();
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;
    private String accountID = "1";
    private String status = "Paid";
    SimpleDateFormat dateFormat;
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
        bindingView();
        bindingAction();
        initRecyclerView();
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
            String formattedDay = String.format("%02d", dayOfMonth);
            String formattedMonth = String.format("%02d", month1 + 1);
            historyDate.setText(formattedDay + "/" + formattedMonth + "/" + year1);
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
        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());

    }
    private void initRecyclerView() {
        executorService.execute(() -> {
            List<MyTicketEntity> listMyTicket = db.myTicketDAO().getMyTicketsByAccount(accountID);
            List<MyHistoryDTO> loadedData = new ArrayList<>();

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng chuỗi nguồn
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng mong muốn

            for (MyTicketEntity ticket : listMyTicket) {
                String formattedDate = null;
                try {
                    if (ticket.getOrderDate() != null) {
                        Date date = inputFormat.parse(ticket.getOrderDate());
                        formattedDate = outputFormat.format(date);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MyHistoryDTO historyDTO = new MyHistoryDTO(
                        ticket.getMyTicketID(),
                        formattedDate, // Ngày đã định dạng
                        ticket.getStatus().equals(status)
                );
                loadedData.add(historyDTO);
            }

            runOnUiThread(() -> {
                data.clear();
                data.addAll(loadedData);
                updateRecyclerView(data);
            });
        });

        MyHistoryAdapter adapter = new MyHistoryAdapter(data);
        historyList.setAdapter(adapter);
        historyList.setLayoutManager(new LinearLayoutManager(this));
    }

}