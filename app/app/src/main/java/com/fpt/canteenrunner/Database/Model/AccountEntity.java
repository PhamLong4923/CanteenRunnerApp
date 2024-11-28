package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Account")
public class AccountEntity {
    @PrimaryKey
    @NonNull
    public String AccountID;

    public String Username;
    public String Password;
    public String Email;
    public String PhoneNumber;
    public double Score;
    public String Role;
    public String CreatedDate;
    public boolean IsFingerPrintEnabled;
    public String FingerPrintData;
}
