package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;

import java.util.List;

@Dao
public interface CanteenDAO {

    @Insert
    void insertCanteen(CanteenEntity canteen);

    @Query("SELECT * FROM Canteen WHERE AccountID = :accountId")
    List<CanteenEntity> getCanteensByAccount(String accountId);

    @Query("SELECT * FROM Canteen ")
    List<CanteenEntity> getAllCanteens();

    @Query("SELECT CanteenName FROM Canteen WHERE CanteenID = :canteenID")
    String getCanteenNameById(String canteenID);
    @Query("SELECT * FROM Canteen WHERE CanteenID = :canteenId")
    CanteenEntity getCanteenById(String canteenId);

    @Query("SELECT * FROM Canteen WHERE CanteenID = :canteenID limit 1")
    CanteenEntity getCanteenByAccount(String canteenID);
}
