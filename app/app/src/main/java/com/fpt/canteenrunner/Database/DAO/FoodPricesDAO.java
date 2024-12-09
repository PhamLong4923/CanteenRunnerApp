package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface FoodPricesDAO {

    @Insert
    void insertPrice(FoodPricesEntity price);


    @Query("SELECT * FROM FoodPrices WHERE FoodID = :foodId")
    List<FoodPricesEntity> getPricesByFood(String foodId);

    @Query("SELECT * FROM FoodPrices WHERE FoodID = :foodId")
    FoodPricesEntity getPricesByFood1(String foodId);

}
