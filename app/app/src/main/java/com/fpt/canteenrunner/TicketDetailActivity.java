package com.fpt.canteenrunner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.canteenrunner.Database.CanteenRunnerDatabase;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketDetailActivity extends AppCompatActivity {
    private ImageView backBtn;
    private ImageView ivQrcode;
    private TextView tvCanten, tvPrice, tvOrderDate;
    private ExecutorService executorService;
    private CanteenRunnerDatabase db;
    private String qrStringCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        Intent intent = getIntent();
        String ticketId = intent.getStringExtra("ticketCode");
        if (ticketId != null) {
            executorService.execute(() -> {
                MyTicketEntity qrCode = db.myTicketDAO().getMyTicketByTicketId(ticketId);
                TicketEntity ticket = db.ticketDAO().getTicketById(ticketId);
                String canteenName = db.canteenDAO().getCanteenNameById(ticket.getCanteenID());
                String Price = String.valueOf(qrCode.getPrice()) ;
                runOnUiThread(() -> {
                    tvCanten.setText(canteenName);
                    tvPrice.setText(Price);
                    tvOrderDate.setText(qrCode.getOrderDate());
                    qrStringCode = qrCode.getQrCode()+" | "+qrCode.getPrice()+" | "+qrCode.getOrderDate()+" | "+canteenName;
                    generateQRCode(qrStringCode);
                });
            });
        }
    }

    private void generateQRCode(String data) {
        try {
            // Set up encoding parameters for QR code
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Generate QR code bit matrix
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512, hints);

            // Convert BitMatrix to Bitmap
            Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
            for (int x = 0; x < 512; x++) {
                for (int y = 0; y < 512; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Black and white
                }
            }
            // Set the generated QR code to the ImageView
            ivQrcode.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindingView() {
        backBtn = findViewById(R.id.back_btn);
        ivQrcode = findViewById(R.id.iv_qrcode);
        executorService = Executors.newSingleThreadExecutor();
        db = CanteenRunnerDatabase.getInstance(getApplicationContext());
        tvCanten = findViewById(R.id.tvCanten);
        tvPrice = findViewById(R.id.tvPrice);
        tvOrderDate = findViewById(R.id.tvOrderDate);
    }

    private void bindingAction() {
        backBtn.setOnClickListener(this::onBackBtnClick);
    }

    private void onBackBtnClick(View view) {
        finish();
    }
}