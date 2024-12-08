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
    public String CategoryID;
    public String CanteenID;
    public String ImageURL;
    public String UpdateDate;

    public FoodsEntity() {
    }

    public FoodsEntity(@NonNull String foodID,
                       String name,
                       String description,
                       String categoryID,
                       String canteenID,
                       String imageURL,
                       String updateDate) {
        FoodID = foodID;
        Name = name;
        Description = description;
        CategoryID = categoryID;
        CanteenID = canteenID;
        ImageURL = imageURL;
        UpdateDate = updateDate;
    }

    @NonNull
    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(@NonNull String foodID) {
        FoodID = foodID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCanteenID() {
        return CanteenID;
    }

    public void setCanteenID(String canteenID) {
        CanteenID = canteenID;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }
}
