package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import java.util.List;

@Dao
public interface TicketDAO {

    @Insert
    void insertTicket(TicketEntity ticket);

    @Query("SELECT * FROM Ticket WHERE CanteenID = :canteenId")
    List<TicketEntity> getTicketsByCanteen(String canteenId);

    @Query("SELECT * FROM Ticket WHERE TicketID = :ticketId limit 1")
    TicketEntity getTicketById(String ticketId);


}
