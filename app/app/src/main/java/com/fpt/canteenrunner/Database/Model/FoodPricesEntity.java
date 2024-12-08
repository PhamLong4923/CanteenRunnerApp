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

    public FoodPricesEntity() {
    }
    public FoodPricesEntity(@NonNull String priceID, String foodID, double price) {
        PriceID = priceID;
        FoodID = foodID;
        Price = price;
    }
    @NonNull
    public String getPriceID() {
        return PriceID;
    }

    public void setPriceID(@NonNull String priceID) {
        PriceID = priceID;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
