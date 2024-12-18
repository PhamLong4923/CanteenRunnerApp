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
        version = 4, exportSchema = false)
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
            try {
                //account mra / 123/ account dell nao cung pass 123 het
                db.execSQL("INSERT INTO Account (AccountID, Username, Password, Email, PhoneNumber, Score, Role, CreatedDate, IsFingerPrintEnabled, FingerPrintData) VALUES " +
                        "(1, 'mra', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'mra@example.com', '0123456789', 100.0, 'User', '2024-01-01', 0, NULL), " +
                        "(2, 'mrb', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'mrb@example.com', '0987654321', 100.0, 'User', '2024-01-01', 0, NULL), " +
                        "(3, 'hanoipho', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'hanoipho@example.com', '0962171802', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                        "(4, 'quanganh', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'quanganh@example.com', '0962172276', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                        "(5, 'simdo', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'simdo@example.com', '0962170159', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                        "(6, 'fastfood', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'fastfood@example.com', '0962172196', 100.0, 'Seller', '2024-01-01', 0, NULL), " +
                        "(7, 'admin', '$2a$10$qyJs9H3MTpb3C0i2LextCuf7XlXNhOCXCL5dkoH7Q/1cLvEonvLr2', 'admin@example.com', '0962719574', 2000.0, 'Admin', '2023-01-02', 1, 'SampleFingerData1');");

                db.execSQL("INSERT INTO Canteen (CanteenID, CanteenName, AccountID, Image) VALUES " +
                        "(1, 'Hà Nội Phố', 3, 'https://scontent.fhan15-1.fna.fbcdn.net/v/t1.15752-9/462568998_1786002385531733_3823915966563814947_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeHgxLxyh5ADv7tX-dAXqognkRqbyqjA6cCRGpvKqMDpwMuncPyZeVodobmS0OcwKu0tDc2OhY4w_6AyXJ76ZZYN&_nc_ohc=F1ERTzT8ELoQ7kNvgFZAS5b&_nc_zt=23&_nc_ht=scontent.fhan15-1.fna&oh=03_Q7cD1QFUVWoHekZVe8sSBZiY5MOFEoS4iFDj-LyKtc0mSlVK4Q&oe=6780F735'), " +
                        "(2, 'Quang Anh Canteen', 4, 'https://scontent.fhan20-1.fna.fbcdn.net/v/t1.15752-9/467129860_1001601938393888_7531430362697881714_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeGm1c6K9S8YMdUMUyy5wpLVPv3VH7Tv23E-_dUftO_bcdSxAvZ-PSx_jGJv9OjCkZT98SSb_hESAg-XKg1rImwv&_nc_ohc=xgHdQj3YKEEQ7kNvgEiiN-B&_nc_zt=23&_nc_ht=scontent.fhan20-1.fna&oh=03_Q7cD1QFmERZSdFuHjlxDD7da4Se7mE9ZiLo6xrOu1TDU8LZtgg&oe=6781291D'), " +
                        "(3, 'Simdo', 5, 'https://scontent.fhan15-1.fna.fbcdn.net/v/t1.15752-9/467480087_477443755365344_8441671924614553440_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeGg9omoKc0Xy8fas3_4SfGl2UMM59SYxWHZQwzn1JjFYcQ4d2ij51dhS3qMLYFepL5rq_exetzBVmom6YZnKhJ9&_nc_ohc=N2V6ojKzLL4Q7kNvgG7tTsd&_nc_zt=23&_nc_ht=scontent.fhan15-1.fna&oh=03_Q7cD1QFWyd-mH7IKPWA5r6OV7njKZbGqsXcqiiObtUKEl2vDVQ&oe=6781187D'), " +
                        "(4, 'Fastfood', 6, 'https://th.bing.com/th/id/OIP.4dvMrEru9swHApwRoNFD1gHaEK?rs=1&pid=ImgDetMain');");

                db.execSQL("INSERT INTO Categories (CategoryID, Name, Description) VALUES " +
                        "(1, 'Cơm', 'Thức ăn phổ biến, miễn phí rau'), " +
                        "(2, 'Xôi', 'Gạo nếp dẻo dai'), " +
                        "(3, 'Đồ ăn nước', 'Thức ăn bơi trong nước, hệt như bạn giữa deadline'), " +
                        "(4, 'Bánh mì', 'Giải pháp cho thời gian gấp rút'), " +
                        "(5, 'Nước giải khát', 'Thức uống giải khát'), " +
                        "(6, 'Khác', 'Thực phẩm khác');");

                db.execSQL("INSERT INTO Foods (FoodID, Name, Description, CategoryID, CanteenID, ImageURL, UpdateDate) VALUES " +
                        "(1, 'Nước lọc', 'Quên chai nước ở nhà? mua nước đi e', 5, 2, 'https://product.hstatic.net/200000356473/product/1_ce622aa474314cc3b84ede533e09417b_grande.jpg', '2024-01-01'), " +
                        "(2, 'Nước lọc', 'Canh ăn chán quá nên phải mua nước', 5, 3, 'https://cf.shopee.vn/file/8fd24107b7866d20dab4b2d11129f287', '2024-01-01'), " +
                        "(3, 'Trà xanh TeaPlus', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 2, 'https://product.hstatic.net/1000288770/product/tra_xanh_matcha_tea_plus_chai_455ml_394288efb40d42ecab18b7f7aed1af19.jpg', '2024-01-01'), " +
                        "(4, 'Trà xanh TeaPlus', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 3, 'https://production-cdn.pharmacity.io/digital/1080x1080/plain/e-com/images/ecommerce/P22836_1.jpg', '2024-01-01'), " +
                        "(5, 'Icy đần', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 2, 'https://cf.shopee.vn/file/c450f74c05853ccd32bf2d3c34bc93b2', '2024-01-01'), " +
                        "(6, 'Icy đần', 'Không nên uống nước ngọt khi đang ăn cơm', 5, 3, 'https://pvmarthanoi.com.vn/wp-content/uploads/2023/01/nuoc-trai-cay-ice-vi-dao-490ml-202203011604293225-500x600.jpg', '2024-01-01'), " +
                        "(7, 'Bánh mì trứng', 'Ăn mỗi trứng là bị ăn 0 đấy', 4, 4, 'https://inoxquanghuy.vn/wp-content/uploads/2022/12/banh-mi-pate-trung.jpg', '2024-01-01'), " +
                        "(8, 'Bánh mì trứng chả', 'Ăn chả ra gì', 4, 2, 'https://inoxquanghuy.vn/wp-content/uploads/2022/12/banh-mi-pate-trung.jpg', '2024-01-01'), " +
                        "(9, 'Bánh mì trứng chúc chích', 'Tuyệt', 4, 4, 'https://inoxquanghuy.vn/wp-content/uploads/2022/12/banh-mi-pate-trung.jpg', '2024-01-01'), " +
                        "(10, 'Bánh mì chả nướng', 'Đặc biệt ở chỗ ăn chả ngon', 4, 4, 'https://patecotden.net/wp-content/uploads/2023/08/banh-my-cha-gio-nong-5.png', '2024-01-01'), " +
                        "(11, 'Mì tôm', 'Wtf cho ăn mì với xiên bẩn?', 3, 2, 'https://i0.wp.com/yeuamthuc.org/wp-content/uploads/2024/04/lau-ly-xien-ban-cua-quan-le-la-xien-0f61055f.jpg?resize=749%2C749&ssl=1', '2024-01-01'), " +
                        "(12, 'Xôi', 'Hay quịt dưa chuột, thịt nhiều mỡ ăn nhiều ngấy', 2, 2, 'https://i-giadinh.vnecdn.net/2023/11/19/Bc8Thnhphm18-1700369779-5346-1700369782.jpg', '2024-01-01'), " +
                        "(13, 'Xôi', 'Thịt ngon nhưng không có dưa chuột', 2, 3, 'https://cdn.tgdd.vn/Files/2022/03/31/1423320/cach-lam-xoi-thit-kho-trung-ngon-nhut-nach-don-gian-de-lam-tai-nha-202203312359050604.jpg', '2024-01-01'), " +
                        "(14, 'Phở bòa', 'Phở bòa hấp dẫn, thua xa nội thành và các quán khác', 3, 1, 'https://fohlafood.vn/cdn/shop/articles/bi-quyet-nau-phi-bo-ngon-tuyet-dinh.jpg?v=1712213789', '2024-01-01'), " +
                        "(15, 'Bún mọc', 'Bún Huế hấp dẫn, ăn không hấp dẫn', 3, 1, 'https://beptruong.edu.vn/wp-content/uploads/2019/06/bun-suon-moc.jpg', '2024-01-01'), " +
                        "(16, 'Bún cá', 'Bún cá siêu giòn, bún giòn cá dai', 3, 1, 'https://cdn.eva.vn/upload/4-2023/images/2023-10-27/cach-nau-bun-ca-ngon-chuan-vi-ha-noi-khong-tanh-bun-ca-eva-001-1698392675-405-width691height530.jpg', '2024-01-01'), " +
                        "(17, 'Cơm', 'Cơm dở canh ngon', 1, 2, 'https://comvangfood.com/upload/product/m12-5842.jpg', '2024-01-01'), " +
                        "(18, 'Cơm', 'Cơm ngon canh là nước muối pha????', 1, 3, 'https://dogifood.vn/Images/news/2110221018-com-van-phong-30k.webp', '2024-01-01'), " +
                        "(19, 'Bánh bao', 'Bánh bao siu hót', 6, 1, 'https://thophat.com/wp-content/uploads/2022/03/BB-Chay-400g-1.jpg', '2024-01-01'), " +
                        "(20, 'Bánh bao xá xíu', 'Siu hót kèm xá xíu', 6, 1, 'https://thophat.com/wp-content/uploads/2022/03/BB-Xa-Xiu-1.jpg', '2024-01-01'), " +
                        "(21, 'Bánh bao hoàng kim', 'Siu hót nhưng màu vàng', 6, 1, 'https://abby.vn/wp-content/uploads/2016/11/cach-lam-banh-bao-kim-sa.jpg?fbclid=IwY2xjawHGmllleHRuA2FlbQIxMAABHRwIlEwFgsRSnNI7yPabcjPygP4cDf1RreS2_9KHJEekAT0-XIaFUurJIQ_aem_a6kFNGjtyCeaAXhUV6IFbQ', '2024-01-01'), " +
                        "(22, 'Trà xanh 0 độ', 'Trà xanh 0 độ 25 độ', 5, 1, 'https://asianoeki.com/cdn/shop/products/New-8936193070013.jpg?v=1666086985', '2024-01-01'), " +
                        "(23, 'Bún chả', 'Bún thịt nướng ăn chán vô cùng', 3, 3, 'https://thammyvienngocdung.com/wp-content/uploads/2024/02/bun-cha-cham-bao-nhieu-calo.jpg', '2024-01-01'), " +
                        "(24, 'Phở gà', 'Phở gà không ngon lắm', 3, 2, 'https://cdn.buffetposeidon.com/app/media/uploaded-files/200823-cach-lam-pho-ga-buffet-poseidon.1.jpg', '2024-01-01');");

                db.execSQL("INSERT INTO FoodPrices (PriceID, FoodID, Price) VALUES " +
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
                        "(24, 18, 45000), " +
                        "(25, 19, 10000), " +
                        "(26, 20, 15000), " +
                        "(27, 21, 15000), " +
                        "(28, 22, 10000), " +
                        "(29, 23, 35000), " +
                        "(30, 24, 35000);");

                db.execSQL("INSERT INTO Ticket (TicketID, TicketPrice, CanteenID) VALUES " +
                        "(1, 5000, 2), " +
                        "(2, 5000, 3), " +
                        "(3, 10000, 2), " +
                        "(4, 10000, 3), " +
                        "(5, 15000, 4), " +
                        "(6, 20000, 4), " +
                        "(7, 25000, 2), " +
                        "(8, 25000, 3), " +
                        "(9, 35000, 1), " +
                        "(10, 35000, 2), " +
                        "(11, 35000, 3), " +
                        "(12, 40000, 1), " +
                        "(13, 40000, 2), " +
                        "(14, 40000, 3), " +
                        "(15, 45000, 2), " +
                        "(16, 45000, 3);");

                db.execSQL("INSERT INTO MyTicket (MyTicketID, AccountID, TicketID, OrderDate, Price, PaymentType, Status, QrCode) VALUES " +
                        "(1, 1, 10, '2024-01-05', 35000, 'Credit Card', 'Paid', NULL), " +
                        "(2, 1, 12, '2024-01-05', 40000, 'Cash', 'Pending', 'SampleQRCode1'), " +
                        "(3, 2, 9, '2024-01-05', 35000, 'Credit Card', 'Paid', NULL), " +
                        "(4, 2, 13, '2024-01-06', 40000, 'Cash', 'Pending', 'SampleQRCode1');");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };
}

