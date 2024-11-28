package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
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
}
