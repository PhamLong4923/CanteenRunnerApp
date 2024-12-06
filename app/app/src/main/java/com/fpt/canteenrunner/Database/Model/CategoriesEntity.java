package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class CategoriesEntity {
    @NonNull
    @PrimaryKey
    public String CategoryID;

    public String Name;
    public String Description;
}
