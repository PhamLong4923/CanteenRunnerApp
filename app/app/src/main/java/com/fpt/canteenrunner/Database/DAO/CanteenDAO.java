package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import java.util.List;

@Dao
public interface CanteenDAO {

    @Insert
    void insertCanteen(CanteenEntity canteen);

    @Query("SELECT * FROM Canteen WHERE AccountID = :accountId")
    List<CanteenEntity> getCanteensByAccount(String accountId);
}
