package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import java.util.List;

@Dao
public interface FoodsDAO {

    @Insert
    void insertFood(FoodsEntity food);

    @Query("SELECT * FROM Foods WHERE CanteenID = :canteenId")
    List<FoodsEntity> getFoodsByCanteen(String canteenId);
}
