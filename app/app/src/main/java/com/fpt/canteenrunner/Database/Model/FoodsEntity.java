package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Foods", foreignKeys = {
        @ForeignKey(entity = CanteenEntity.class, parentColumns = "CanteenID", childColumns = "CanteenID"),
        @ForeignKey(entity = CategoriesEntity.class, parentColumns = "CategoryID", childColumns = "CategoryID")
})
public class FoodsEntity {
    @PrimaryKey
    @NonNull
    public String FoodID;

    public String Name;
    public String Description;
    public double Price;
    public String CategoryID;
    public String CanteenID;
    public String ImageURL;
    public String Update;
}
