package com.fpt.canteenrunner.DTO;

public class CanteenDTO {
    private  String canteenId;
    private String canteenName;
    private String canteenImageUrl;

    public CanteenDTO(String canteenName, String canteenImageUrl, String canteenId) {
        this.canteenId = canteenId;
        this.canteenName = canteenName;
        this.canteenImageUrl = canteenImageUrl;
    }
    public String getCanteenId() {
        return canteenId;
    }
    public String getCanteenName() {
        return canteenName;
    }

    public String getCanteenImageUrl() {
        return canteenImageUrl;
    }
}
