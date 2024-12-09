package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Account")
public class AccountEntity implements Serializable {
    @PrimaryKey
    @NonNull
    public String AccountID;
    public String Username;
    public String Password;
    public String Email;
    public String PhoneNumber;
    public int Score;
    public String Role;
    public String CreatedDate;
    public boolean IsFingerPrintEnabled;
    @Nullable
    public String FingerPrintData;

    public AccountEntity() {
    }

    public AccountEntity(@NonNull String accountID, String username, String password, String email, String phoneNumber, int score, String role, String createdDate, boolean isFingerPrintEnabled, String fingerPrintData) {
        AccountID = accountID;
        Username = username;
        Password = password;
        Email = email;
        PhoneNumber = phoneNumber;
        Score = score;
        Role = role;
        CreatedDate = createdDate;
        IsFingerPrintEnabled = isFingerPrintEnabled;
        FingerPrintData = fingerPrintData;
    }

    @NonNull
    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(@NonNull String accountID) {
        AccountID = accountID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getFingerPrintData() {
        return FingerPrintData;
    }

    public void setFingerPrintData(String fingerPrintData) {
        FingerPrintData = fingerPrintData;
    }

    public boolean isFingerPrintEnabled() {
        return IsFingerPrintEnabled;
    }

    public void setFingerPrintEnabled(boolean fingerPrintEnabled) {
        IsFingerPrintEnabled = fingerPrintEnabled;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "AccountID='" + AccountID + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Score=" + Score +
                ", Role='" + Role + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", IsFingerPrintEnabled=" + IsFingerPrintEnabled +
                ", FingerPrintData='" + FingerPrintData + '\'' +
                '}';
    }
}
