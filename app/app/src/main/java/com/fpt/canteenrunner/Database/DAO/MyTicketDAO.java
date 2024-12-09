package com.fpt.canteenrunner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import java.util.List;

@Dao
public interface MyTicketDAO {

    @Insert
    void insertMyTicket(MyTicketEntity myTicket);

    @Query("SELECT * FROM MyTicket WHERE AccountID = :accountId")
    List<MyTicketEntity> getMyTicketsByAccount(String accountId);

    @Query("SELECT * FROM MyTicket JOIN Ticket ON MyTicket.TicketID = Ticket.TicketID WHERE AccountID = :accountId AND Status = 'Pending'")
    List<MyTicketEntity> getMyTicketsWithTicketByAccount(String accountId);

    @Query("SELECT * FROM MyTicket WHERE TicketID = :ticketId LIMIT 1")
    MyTicketEntity getMyTicketByTicketId(String ticketId);
}
