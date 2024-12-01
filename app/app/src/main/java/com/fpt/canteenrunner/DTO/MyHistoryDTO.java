package com.fpt.canteenrunner.DTO;

public class MyHistoryDTO {
    private String tiketCode;
    private String buyDate;
    private Boolean status;

    public MyHistoryDTO(String tiketCode, String buyDate, Boolean status) {
        this.tiketCode = tiketCode;
        this.buyDate = buyDate;
        this.status = status;
    }

    public String getTiketCode() {
        return tiketCode;
    }

    public void setTiketCode(String tiketCode) {
        this.tiketCode = tiketCode;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
