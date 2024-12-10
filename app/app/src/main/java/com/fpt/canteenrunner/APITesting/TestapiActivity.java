package com.fpt.canteenrunner.APITesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fpt.canteenrunner.APIServices.TestApiService;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestapiActivity extends AppCompatActivity {
    private TextView tvApiResponse;
    private Button btnCallApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testapi);

        tvApiResponse = findViewById(R.id.tvApiResponse);
        btnCallApi = findViewById(R.id.btnCallApi);

        // Cấu hình Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5157/") // Địa chỉ server (dùng localhost trên AVD)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestApiService apiService = retrofit.create(TestApiService.class);

        btnCallApi.setOnClickListener(v -> {
            // Gọi API
            Call<List<FoodDTO>> call = apiService.getFoods();
            call.enqueue(new Callback<List<FoodDTO>>() {
                @Override
                public void onResponse(Call<List<FoodDTO>> call, Response<List<FoodDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<FoodDTO> foods = response.body();
                        StringBuilder result = new StringBuilder();
                        for (FoodDTO food : foods) {
                            result.append("Name: ").append(food.getName())
                                    .append("\nDescription: ").append(food.getDescription())
                                    .append("\nPrice: ").append(food.getPrice())
                                    .append("\n\n");
                        }
                        tvApiResponse.setText(result.toString());
                    } else {
                        tvApiResponse.setText("Failed to get data.");
                    }
                }

                @Override
                public void onFailure(Call<List<FoodDTO>> call, Throwable t) {
                    tvApiResponse.setText("Error: " + t.getMessage());
                    Toast.makeText(TestapiActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
