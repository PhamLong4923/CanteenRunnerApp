package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import java.util.List;

@Dao
public interface FoodsDAO {

    @Insert
    void insertFood(FoodsEntity food);

    @Update
    void updateFood(FoodsEntity food);
    @Query("DELETE FROM Foods WHERE FoodID = :foodId")
    void deleteFood(Integer foodId);

    @Query("SELECT * FROM Foods WHERE CanteenID = :canteenId")
    List<FoodsEntity> getFoodsByCanteen(String canteenId);

    @Query("SELECT * FROM Foods ORDER BY FoodID DESC LIMIT 1")
    FoodsEntity getLastInsertedFoodID();

    @Query("select * from Foods where FoodID = :foodId")
    FoodsEntity getFoodById(String foodId);

    @Query("SELECT * FROM foods WHERE CategoryID = :cateID")
    List<FoodsEntity> getFoodsByCategoryId(String cateID);

    @Query("SELECT * FROM Foods WHERE CanteenID = :canteenId AND CategoryID = :categoryId")
    List<FoodsEntity> getFoodsByCanteenIdAndCategoryId(String canteenId, String categoryId);
}
