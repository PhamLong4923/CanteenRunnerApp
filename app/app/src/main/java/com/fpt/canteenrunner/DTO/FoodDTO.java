package com.fpt.canteenrunner.DTO;

public class FoodDTO {
    private String ImageURL;
    private String FoodName;
    private String Price;
    private String CateID;

    public FoodDTO(String imageURL, String foodName, String price, String cateID) {
        ImageURL = imageURL;
        FoodName = foodName;
        Price = price;
        CateID = cateID;
    }

    public String getCateID() {
        return CateID;
    }

    public void setCateID(String cateID) {
        CateID = cateID;
    }

    public FoodDTO() {
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}