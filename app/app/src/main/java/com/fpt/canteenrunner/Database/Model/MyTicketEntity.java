package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "MyTicket", foreignKeys = {
        @ForeignKey(entity = AccountEntity.class, parentColumns = "AccountID", childColumns = "AccountID"),
        @ForeignKey(entity = TicketEntity.class, parentColumns = "TicketID", childColumns = "TicketID")
})
public class MyTicketEntity {
    @PrimaryKey
    @NonNull
    public String MyTicketID;

    public String AccountID;
    public String TicketID;
    public String OrderDate;
    public double Price;
    public String PaymentType;
    public String Status;
    @Nullable
    public String QrCode;

    public MyTicketEntity() {
    }

    public MyTicketEntity(@NonNull String myTicketID,
                          String accountID,
                          String ticketID,
                          String orderDate,
                          double price,
                          String paymentType,
                          String status,
                          @Nullable String qrCode) {
        MyTicketID = myTicketID;
        AccountID = accountID;
        TicketID = ticketID;
        OrderDate = orderDate;
        Price = price;
        PaymentType = paymentType;
        Status = status;
        QrCode = qrCode;
    }

    @NonNull
    public String getMyTicketID() {
        return MyTicketID;
    }

    public void setMyTicketID(@NonNull String myTicketID) {
        MyTicketID = myTicketID;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getTicketID() {
        return TicketID;
    }

    public void setTicketID(String ticketID) {
        TicketID = ticketID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Nullable
    public String getQrCode() {
        return QrCode;
    }

    public void setQrCode(@Nullable String qrCode) {
        QrCode = qrCode;
    }
}
