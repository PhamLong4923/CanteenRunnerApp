package com.fpt.canteenrunner.DTO;

public class MyTicketDTO {
    private String ticketCode;
    private String buyDate;
    private String Price;

    public MyTicketDTO(String ticketCode, String buyDate, String Price) {
        this.ticketCode = ticketCode;
        this.buyDate = buyDate;
        this.Price = Price;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
