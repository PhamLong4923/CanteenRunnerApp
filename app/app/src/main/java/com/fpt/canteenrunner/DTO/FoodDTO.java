package com.fpt.canteenrunner.DTO;

public class FoodDTO {

    private String foodID;
    private String name;
    private String description;
    private double price;
    private String categoryID;
    private String canteenID;
    private String imageURL;
    private String update;

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public double getPrice() {
        return price;
    }
}

