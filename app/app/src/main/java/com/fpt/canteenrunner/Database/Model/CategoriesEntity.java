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

    public CategoriesEntity() {
    }

    public CategoriesEntity(@NonNull String categoryID, String name, String description) {
        CategoryID = categoryID;
        Name = name;
        Description = description;
    }

    @NonNull
    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(@NonNull String categoryID) {
        CategoryID = categoryID;
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
}
