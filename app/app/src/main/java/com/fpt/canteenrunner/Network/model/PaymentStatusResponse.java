package com.fpt.canteenrunner.Network.model;

public class PaymentStatusResponse {
    private String status;
    private String message;

    // Getter và Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
