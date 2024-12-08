package com.fpt.canteenrunner.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ticket", foreignKeys = @ForeignKey(entity = CanteenEntity.class,
        parentColumns = "CanteenID",
        childColumns = "CanteenID"))
public class TicketEntity {
    @PrimaryKey
    @NonNull
    public String TicketID;
    public double TicketPrice;
    public String CanteenID;

    public TicketEntity() {
    }

    public TicketEntity(@NonNull String ticketID, double ticketPrice, String canteenID) {
        TicketID = ticketID;
        TicketPrice = ticketPrice;
        CanteenID = canteenID;
    }

    @NonNull
    public String getTicketID() {
        return TicketID;
    }

    public void setTicketID(@NonNull String ticketID) {
        TicketID = ticketID;
    }

    public double getTicketPrice() {
        return TicketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        TicketPrice = ticketPrice;
    }

    public String getCanteenID() {
        return CanteenID;
    }

    public void setCanteenID(String canteenID) {
        CanteenID = canteenID;
    }
}
