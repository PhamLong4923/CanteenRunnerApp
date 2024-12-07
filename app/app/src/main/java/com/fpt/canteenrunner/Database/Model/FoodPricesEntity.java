package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "FoodPrices", foreignKeys = {
        @ForeignKey(entity = FoodsEntity.class, parentColumns = "FoodID", childColumns = "FoodID")
})
public class FoodPricesEntity {
    @PrimaryKey
    @NonNull
    public String PriceID; // Unique ID for the price entry

    public String FoodID; // Foreign key to FoodsEntity
    public double Price; // Price of the food
}
