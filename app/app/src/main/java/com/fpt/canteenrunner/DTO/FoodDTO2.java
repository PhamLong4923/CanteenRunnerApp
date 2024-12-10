package com.fpt.canteenrunner.DTO;

import java.util.Date;

public class FoodDTO2 {
    private String ImageURL;
    private String FoodName;
    private String UpdateDate;
    private String CateID;

    public FoodDTO2(String imageURL, String cateID, String updateDate, String foodName) {
        ImageURL = imageURL;
        CateID = cateID;
        UpdateDate = updateDate;
        FoodName = foodName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getCateID() {
        return CateID;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public String getFoodName() {
        return FoodName;
    }
}
