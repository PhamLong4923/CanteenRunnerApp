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
}
