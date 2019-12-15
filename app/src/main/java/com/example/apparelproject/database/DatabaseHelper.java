package com.example.apparelproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apparelproject.utils.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION =6;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_TABEL_PRODUK = "CREATE TABLE " + Config.TABEL_PRODUK + "("
                + Config.COLUMN_PRODUK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PRODUK_IMAGE + " BLOB , "
                + Config.COLUMN_PRODUK_NAMA + " TEXT , "
                + Config.COLUMN_PRODUK_DESKRIPSI + " TEXT , "
                + Config.COLUMN_PRODUK_KATEGORI + " TEXT , "
                + Config.COLUMN_PRODUK_HARGA + " INTEGER , "
                + Config.COLUMN_PRODUK_WARNA + " TEXT , "
                + Config.COLUMN_PRODUK_UKURAN + " TEXT "
                + ");";

        // Create tables SQL execution
        String CREATE_TABEL_NEWS = "CREATE TABLE " + Config.TABEL_NEWS + "("
                + Config.COLUMN_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_NEWS_JUDUL + " TEXT , "
                + Config.COLUMN_NEWS_DESKRIPSI + " TEXT , "
                + Config.COLUMN_NEWS_IMAGE + " BLOB "
                + ");";

        // Create tables SQL execution
        String CREATE_TABEL_USER = "CREATE TABLE " + Config.TABEL_USER + "("
                + Config.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_USER_NAMA + " TEXT , "
                + Config.COLUMN_USER_USERNAME + " TEXT , "
                + Config.COLUMN_USER_PASSWORD + " TEXT , "
                + Config.COLUMN_USER_IMAGE + " BLOB , "
                + Config.COLUMN_USER_JENISKELAMIN + " TEXT , "
                + Config.COLUMN_USER_EMAIL + " TEXT , "
                + Config.COLUMN_USER_ALAMAT + " TEXT , "
                + Config.COLUMN_USER_HAKAKSES + " TEXT "
                + ");";

        // Create tables SQL execution
        String CREATE_TABEL_TRX = "CREATE TABLE " + Config.TABEL_TRX + "("
                + Config.COLUMN_TRX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_TRX_NAMA_USER + " TEXT , "
                + Config.COLUMN_TRX_TANGGAL + " TEXT , "
                + Config.COLUMN_TRX_IMAGE_PRODUK + " BLOB , "
                + Config.COLUMN_TRX_NAMA_PRODUK + " TEXT , "
                + Config.COLUMN_TRX_HARGA + " INTEGER , "
                + Config.COLUMN_TRX_JUMLAH + " INTEGER , "
                + Config.COLUMN_TRX_STATUS + " TEXT , "
                + Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN + " BLOB "
                + ");";

        db.execSQL(CREATE_TABEL_PRODUK);
        db.execSQL(CREATE_TABEL_NEWS);
        db.execSQL(CREATE_TABEL_USER);
        db.execSQL(CREATE_TABEL_TRX);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABEL_PRODUK);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABEL_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABEL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABEL_TRX);
        // Create tables again
        onCreate(db);
    }

}
