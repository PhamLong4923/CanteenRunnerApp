package com.fpt.canteenrunner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnMenu = findViewById(R.id.fabOptions);
        btnMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, btnMenu);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_options, popupMenu.getMenu());
            popupMenu.show();
        });
    }
}