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
}
