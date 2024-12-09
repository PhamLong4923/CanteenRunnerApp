package com.fpt.canteenrunner.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.fpt.canteenrunner.Database.DAO.AccountDAO;
import com.fpt.canteenrunner.Database.DAO.CanteenDAO;
import com.fpt.canteenrunner.Database.DAO.FoodPricesDAO;
import com.fpt.canteenrunner.Database.DAO.FoodsDAO;
import com.fpt.canteenrunner.Database.DAO.TicketDAO;
import com.fpt.canteenrunner.Database.DAO.MyTicketDAO;
import com.fpt.canteenrunner.Database.DAO.CategoriesDAO;
import com.fpt.canteenrunner.Database.Model.AccountEntity;
import com.fpt.canteenrunner.Database.Model.CanteenEntity;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import com.fpt.canteenrunner.Database.Model.MyTicketEntity;
import com.fpt.canteenrunner.Database.Model.CategoriesEntity;

@Database(entities = {AccountEntity.class, CanteenEntity.class, FoodsEntity.class,
        TicketEntity.class, MyTicketEntity.class, CategoriesEntity.class, FoodPricesEntity.class},
        version = 3, exportSchema = false)
public abstract class CanteenRunnerDatabase extends RoomDatabase {

    private static volatile CanteenRunnerDatabase instance;

    // DAO references
    public abstract AccountDAO accountDAO();
    public abstract CanteenDAO canteenDAO();
    public abstract FoodsDAO foodsDAO();
    public abstract TicketDAO ticketDAO();
    public abstract MyTicketDAO myTicketDAO();
    public abstract CategoriesDAO categoriesDAO();
    public abstract FoodPricesDAO foodPricesDAO();

    // Singleton pattern
    public static synchronized CanteenRunnerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CanteenRunnerDatabase.class, "canteen_runner_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
