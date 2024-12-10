package com.fpt.canteenrunner.APIServices;

import com.fpt.canteenrunner.DTO.FoodDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestApiService {
    @GET("api/Test/foods") // Endpoint của API
    Call<List<FoodDTO>> getFoods();
}
