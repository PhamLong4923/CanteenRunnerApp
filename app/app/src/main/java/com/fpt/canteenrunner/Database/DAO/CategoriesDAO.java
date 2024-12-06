package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.CategoriesEntity;

@Dao
public interface CategoriesDAO {

    @Insert
    void insertCategory(CategoriesEntity category);

    @Query("SELECT * FROM Categories WHERE CategoryID = :categoryId")
    CategoriesEntity getCategoryById(String categoryId);
}
