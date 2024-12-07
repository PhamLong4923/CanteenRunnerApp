package com.fpt.canteenrunner.Database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import androidx.sqlite.db.SupportSQLiteDatabase;

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
        version = 2, exportSchema = false)
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
                    .addCallback(prepopulateCallback) // Thêm callback để chèn dữ liệu mẫu
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    // Callback để chèn dữ liệu mẫu
    private static final Callback prepopulateCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Chèn dữ liệu mẫu
            db.execSQL("INSERT INTO AccountEntity (AccountID, Username, Password, Email, PhoneNumber, Score, Role, CreatedDate, IsFingerPrintEnabled, FingerPrintData) VALUES " +
                    "(1, 'mra', 'password', 'mra@example.com', '0123456789', 100.0, 'User', '2024-01-01', 0, NULL), " +
                    "(2, 'mrb', 'password', 'mrb@example.com', '0987654321', 100.0, 'User', '2024-01-01', 0, NULL), " +
                    "(3, 'hanoipho', 'password', 'hanoipho@example.com', '0962171802', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                    "(4, 'quanganh', 'password', 'quanganh@example.com', '0962172276', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                    "(5, 'sendo', 'password', 'sendo@example.com', '0962170159', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                    "(6, 'fastfood', 'password', 'fastfood@example.com', '0962172196', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                    "(7, 'admin', 'password', 'admin@example.com', '0962719574', 2000.0, 'Admin', '2023-01-02', 1, 'SampleFingerData1');");

            db.execSQL("INSERT INTO CanteenEntity (CanteenID, CanteenName, AccountID) VALUES " +
                    "(1, 'Hà Nội Phố', 3), " +
                    "(2, 'Quang Anh Canteen', 4), " +
                    "(3, 'Sendo', 5), " +
                    "(4, 'Fastfood', 6);");

            db.execSQL("INSERT INTO CategoriesEntity (CategoryID, Name, Description) VALUES " +
                    "(1, 'Cơm', 'Thức ăn phổ biến, miễn phí rau'), " +
                    "(2, 'Xôi', 'Gạo nếp dẻo dai'), " +
                    "(3, 'Đồ ăn nước', 'Thức ăn bơi trong nước, hệt như bạn giữa deadline'), " +
                    "(4, 'Bánh mì', 'Giải pháp cho thời gian gấp rút'), " +
                    "(5, 'Nước giải khát', 'Thức uống giải khát'), " +
                    "(6, 'Khác', 'Thực phẩm khác');");

            db.execSQL("INSERT INTO FoodsEntity (FoodID, Name, Description, CategoryID, CanteenID, ImageURL, UpdateDate) VALUES " +
                    "(1, 'Nước lọc', 'Quên chai nước ở nhà? mua nước đi e', 5, 2, 'food_image', '2024-01-01'), " +
                    "(2, 'Nước lọc', 'Canh ăn chán quá nên phải mua nước', 5, 3, 'food_image', '2024-01-01'), " +
                    "(3, 'Trà xanh TeaPlus', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 2, 'food_image', '2024-01-01'), " +
                    "(4, 'Trà xanh TeaPlus', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 3, 'food_image', '2024-01-01'), " +
                    "(5, 'Icy đần', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 2, 'food_image', '2024-01-01'), " +
                    "(6, 'Icy đần', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 3, 'food_image', '2024-01-01'), " +
                    "(7, 'Bánh mì trứng', 'Ăn mỗi trứng là bị ăn 0 đấy', 4, 4, 'food_image', '2024-01-01'), " +
                    "(8, 'Bánh mì trứng chả', 'Ăn chả ra gì', 4, 2, 'food_image', '2024-01-01'), " +
                    "(9, 'Bánh mì trứng chúc chích', 'Tuyệt', 4, 4, 'food_image', '2024-01-01'), " +
                    "(10, 'Bánh mì chả nướng', 'Đặc biệt ở chỗ ăn chả ngon', 4, 4, 'food_image', '2024-01-01'), " +
                    "(11, 'Mì tôm', 'Wtf cho ăn mì với xiên bẩn?', 3, 2, 'food_image', '2024-01-01'), " +
                    "(12, 'Xôi', 'Hay quịt dưa chuột, thịt nhiều mỡ ăn nhiều ngấy', 2, 2, 'food_image', '2024-01-01'), " +
                    "(13, 'Xôi', 'Thịt ngon nhưng không có dưa chuột', 2, 3, 'food_image', '2024-01-01'), " +
                    "(14, 'Phở', 'Phở bòa hấp dẫn, thua xa nội thành và các quán khác', 3, 1, 'food_image', '2024-01-01'), " +
                    "(15, 'Bún mọc', 'Bún Huế hấp dẫn, ăn không hấp dẫn', 3, 1, 'food_image', '2024-01-01'), " +
                    "(16, 'Bún cá', 'Bún cá siêu giòn, bún giòn cá dai', 3, 1, 'food_image', '2024-01-01'), " +
                    "(17, 'Cơm', 'Cơm dở canh ngon', 1, 2, 'food_image', '2024-01-01'), " +
                    "(18, 'Cơm', 'Cơm ngon canh là nước muối pha????', 1, 3, 'food_image', '2024-01-01');");

            db.execSQL("INSERT INTO FoodPricesEntity (PriceID, FoodID, Price) VALUES " +
                    "(1, 1, 5000), " +
                    "(2, 2, 5000), " +
                    "(3, 3, 10000), " +
                    "(4, 4, 10000), " +
                    "(5, 5, 10000), " +
                    "(6, 6, 10000), " +
                    "(7, 7, 15000), " +
                    "(8, 8, 20000), " +
                    "(9, 9, 20000), " +
                    "(10, 10, 20000), " +
                    "(11, 11, 20000), " +
                    "(12, 12, 25000), " +
                    "(13, 13, 25000), " +
                    "(14, 12, 30000), " +
                    "(15, 13, 30000), " +
                    "(16, 14, 35000), " +
                    "(17, 15, 35000), " +
                    "(18, 16, 35000), " +
                    "(19, 17, 35000), " +
                    "(20, 18, 35000), " +
                    "(21, 17, 40000), " +
                    "(22, 18, 40000), " +
                    "(23, 17, 45000), " +
                    "(24, 18, 45000);");

            db.execSQL("INSERT INTO TicketEntity (TicketID, TicketPrice, CanteenID) VALUES " +
                    "(1, 35000, 1), " +
                    "(2, 35000, 2), " +
                    "(3, 40000, 1), " +
                    "(4, 40000, 2);");

            db.execSQL("INSERT INTO MyTicketEntity (MyTicketID, AccountID, TicketID, OrderDate, Price, PaymentType, Status, QrCode) VALUES " +
                    "(1, 1, 1, '2024-01-05', 35000, 'Credit Card', 'Paid', 'SampleQRCode1'), " +
                    "(2, 2, 2, '2024-01-05', 40000, 'Cash', 'Pending', NULL), " +
                    "(3, 1, 1, '2024-01-05', 35000, 'Credit Card', 'Paid', 'SampleQRCode1'), " +
                    "(4, 2, 2, '2024-01-06', 40000, 'Cash', 'Pending', NULL);");
        }
    };
}

