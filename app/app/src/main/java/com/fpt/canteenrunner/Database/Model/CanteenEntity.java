package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Canteen", foreignKeys = @ForeignKey(entity = AccountEntity.class,
        parentColumns = "AccountID",
        childColumns = "AccountID"))
public class CanteenEntity {
    @PrimaryKey
    @NonNull
    public String CanteenID;

    public String CanteenName;
    public String AccountID;
    public String Image;

    @NonNull
    public String getCanteenID() {
        return CanteenID;
    }

    public CanteenEntity() {
    }

    public CanteenEntity(@NonNull String canteenID, String canteenName, String accountID, String image) {
        CanteenID = canteenID;
        CanteenName = canteenName;
        AccountID = accountID;
        Image = image;
    }

    public void setCanteenID(@NonNull String canteenID) {
        CanteenID = canteenID;
    }

    public String getCanteenName() {
        return CanteenName;
    }

    public void setCanteenName(String canteenName) {
        CanteenName = canteenName;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
