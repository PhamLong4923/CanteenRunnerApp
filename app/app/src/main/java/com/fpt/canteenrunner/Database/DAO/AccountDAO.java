package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fpt.canteenrunner.Database.Model.AccountEntity;

import java.util.List;

@Dao
public interface AccountDAO {
    @Query("SELECT * FROM Account")
    List<AccountEntity> getAll();

    @Insert
    Long insertAccount(AccountEntity account);

    @Query("SELECT * FROM Account WHERE Email = :email OR PhoneNumber = :email")
    AccountEntity login(String email);

    @Query("SELECT * FROM Account WHERE PhoneNumber = :PhoneNumber")
    AccountEntity CheckPhoneNumber(String PhoneNumber);

    @Query("SELECT * FROM Account WHERE Email = :Email ")
    AccountEntity CheckEmail(String Email);

    @Update
    Integer updatePassword(AccountEntity account);


}
