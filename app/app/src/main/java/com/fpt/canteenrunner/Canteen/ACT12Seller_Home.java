package com.fpt.canteenrunner.Canteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.canteenrunner.ACT16Activity;
import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.DAO.MyTicketDAO;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import com.fpt.canteenrunner.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ACT12Seller_Home extends AppCompatActivity {

    private EditText etID, etPrice, etTime, etCanteenName;
    private ImageButton menu;
    private Button btnScanQR;
    private Button btnCompleted;
    private ExecutorService executorservice;
    private CanteenRunnerDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act12_seller_home);

        executorservice = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(this);

        bindingView();
        bindingAction();
    }

    private void bindingView() {
        etID = findViewById(R.id.etID);
        etPrice = findViewById(R.id.etPrice);
        etTime = findViewById(R.id.etTime);
        etCanteenName = findViewById(R.id.etCanteenName);
        btnScanQR = findViewById(R.id.btnScanQR);
        btnCompleted = findViewById(R.id.btnCompleted);

        menu = findViewById(R.id.btnMenu);


    }

    private void bindingAction() {
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo IntentIntegrator để bắt đầu quét mã QR
                IntentIntegrator integrator = new IntentIntegrator(ACT12Seller_Home.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Đưa mã QR vào vùng quét");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        menu.setOnClickListener(this::onClickMenu);
    }

    private void onClickMenu(View view) {
        Intent intent = new Intent(this, ACT16Activity.class);
        startActivity(intent);

    }

    private void checkTicketStatus(String ticketId) {

        executorservice.execute(() -> {

            MyTicketEntity ticket = db.myTicketDAO().getTicketById(ticketId);

            if (ticket != null) {
                String status = ticket.getStatus();


                runOnUiThread(() -> {
                    if ("Pending".equalsIgnoreCase(status)) {
                        btnCompleted.setEnabled(true);
                    } else if ("Paid".equalsIgnoreCase(status)) {
                        btnCompleted.setEnabled(false);
                        Toast.makeText(this, "Ticket đã thanh toán, không thể hoàn tất!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                runOnUiThread(() -> {
                    btnCompleted.setEnabled(false);
                    Toast.makeText(this, "Không tìm thấy Ticket!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Lấy kết quả quét QR
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String scannedData = result.getContents();
                Toast.makeText(this, "Dữ liệu QR: " + scannedData, Toast.LENGTH_LONG).show();
                // Giả định định dạng dữ liệu là ID|Price|Time|CanteenName
                String[] parts = scannedData.split("\\|");
                if (parts.length == 4) {
                    etID.setText(parts[0]);
                    etPrice.setText(parts[1]);
                    etTime.setText(parts[2]);
                    etCanteenName.setText(parts[3]);
                } else {
                    Toast.makeText(this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không có dữ liệu nào được quét", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}