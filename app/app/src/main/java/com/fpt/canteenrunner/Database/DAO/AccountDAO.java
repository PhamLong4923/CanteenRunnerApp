package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.AccountEntity;

@Dao
public interface AccountDAO {

    @Insert
    void insertAccount(AccountEntity account);

    @Query("SELECT * FROM Account WHERE Username = :username AND Password = :password")
    AccountEntity login(String username, String password);
}