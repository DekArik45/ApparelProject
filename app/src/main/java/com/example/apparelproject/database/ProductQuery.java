package com.example.apparelproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductQuery {

    private Context context;

    public ProductQuery(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertProduct(ProductModel Product){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PRODUK_IMAGE, Product.getImage());
        contentValues.put(Config.COLUMN_PRODUK_NAMA, Product.getNama());
        contentValues.put(Config.COLUMN_PRODUK_DESKRIPSI, Product.getDeskripsi());
        contentValues.put(Config.COLUMN_PRODUK_KATEGORI, Product.getKategori());
        contentValues.put(Config.COLUMN_PRODUK_HARGA, Product.getHarga());
        contentValues.put(Config.COLUMN_PRODUK_WARNA, Product.getWarna());
        contentValues.put(Config.COLUMN_PRODUK_UKURAN, Product.getUkuran());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABEL_PRODUK, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<ProductModel> getAllProduct(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_PRODUK, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<ProductModel> ProductList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_ID));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_PRODUK_IMAGE));
                        String nama  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_NAMA));
                        String deskripsi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_DESKRIPSI));
                        String kategori = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_KATEGORI));
                        int harga = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_HARGA));
                        String warna = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_WARNA));
                        String ukuran = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_UKURAN));


                        ProductList.add(new ProductModel(id,image,nama,deskripsi,kategori,harga,warna,ukuran));
                    }   while (cursor.moveToNext());

                    return ProductList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public ProductModel getProductByID(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        ProductModel Product = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_PRODUK, null,
                    Config.COLUMN_PRODUK_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                int idProduct = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_ID));
                byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_PRODUK_IMAGE));
                String nama  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_NAMA));
                String deskripsi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_DESKRIPSI));
                String kategori = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_KATEGORI));
                int harga = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_HARGA));
                String warna = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_WARNA));
                String ukuran = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_UKURAN));


                Product = new ProductModel(idProduct,image,nama,deskripsi,kategori,harga,warna,ukuran);

            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Product;
    }

    public List<ProductModel> getProductCategory(String category){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_PRODUK, null,
                    Config.COLUMN_PRODUK_KATEGORI + " = ? ", new String[]{category},
                    null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<ProductModel> ProductList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_ID));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_PRODUK_IMAGE));
                        String nama  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_NAMA));
                        String deskripsi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_DESKRIPSI));
                        String kategori = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_KATEGORI));
                        int harga = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_HARGA));
                        String warna = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_WARNA));
                        String ukuran = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_UKURAN));


                        ProductList.add(new ProductModel(id,image,nama,deskripsi,kategori,harga,warna,ukuran));
                    }   while (cursor.moveToNext());

                    return ProductList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public List<ProductModel> getProductSearch(String search){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_PRODUK, null,
                    Config.COLUMN_PRODUK_NAMA + " LIKE ? ", new String[]{"%"+search+"%"},
                    null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<ProductModel> ProductList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_ID));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_PRODUK_IMAGE));
                        String nama  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_NAMA));
                        String deskripsi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_DESKRIPSI));
                        String kategori = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_KATEGORI));
                        int harga = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUK_HARGA));
                        String warna = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_WARNA));
                        String ukuran = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUK_UKURAN));


                        ProductList.add(new ProductModel(id,image,nama,deskripsi,kategori,harga,warna,ukuran));
                    }   while (cursor.moveToNext());

                    return ProductList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public long updateProduct(ProductModel Product){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PRODUK_IMAGE, Product.getImage());
        contentValues.put(Config.COLUMN_PRODUK_NAMA, Product.getNama());
        contentValues.put(Config.COLUMN_PRODUK_DESKRIPSI, Product.getDeskripsi());
        contentValues.put(Config.COLUMN_PRODUK_KATEGORI, Product.getKategori());
        contentValues.put(Config.COLUMN_PRODUK_HARGA, Product.getHarga());
        contentValues.put(Config.COLUMN_PRODUK_WARNA, Product.getWarna());
        contentValues.put(Config.COLUMN_PRODUK_UKURAN, Product.getUkuran());

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_PRODUK, contentValues,
                    Config.COLUMN_PRODUK_ID + " = ? ",
                    new String[] {String.valueOf(Product.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteProductByID(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABEL_PRODUK,
                    Config.COLUMN_PRODUK_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }
    
}
