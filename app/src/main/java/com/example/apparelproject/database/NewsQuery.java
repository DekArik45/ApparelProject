package com.example.apparelproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.apparelproject.model.NewsModel;
import com.example.apparelproject.utils.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsQuery {

    private Context context;

    public NewsQuery(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertNews(NewsModel News){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_NEWS_JUDUL, News.getJudul());
        contentValues.put(Config.COLUMN_NEWS_DESKRIPSI, News.getDeskripsi());
        contentValues.put(Config.COLUMN_NEWS_IMAGE, News.getImage());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABEL_NEWS, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<NewsModel> getAllNews(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_NEWS, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<NewsModel> NewsList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_NEWS_ID));
                        String judul  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NEWS_JUDUL));
                        String deskripsi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NEWS_DESKRIPSI));
                        byte[] image = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_NEWS_IMAGE));

                        NewsList.add(new NewsModel(id,judul,deskripsi,image));
                    }   while (cursor.moveToNext());

                    return NewsList;
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

    public NewsModel getNewsByID(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        NewsModel News = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_NEWS, null,
                    Config.COLUMN_NEWS_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                int idNews = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_NEWS_ID));
                String judul  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NEWS_JUDUL));
                String deskripsi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NEWS_DESKRIPSI));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_NEWS_IMAGE));

                News = new NewsModel(idNews,judul,deskripsi,image);

            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return News;
    }

    public long updateNews(NewsModel News){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_NEWS_JUDUL, News.getJudul());
        contentValues.put(Config.COLUMN_NEWS_DESKRIPSI, News.getDeskripsi());
        contentValues.put(Config.COLUMN_NEWS_IMAGE, News.getImage());

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_NEWS, contentValues,
                    Config.COLUMN_NEWS_ID + " = ? ",
                    new String[] {String.valueOf(News.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteNewsByID(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABEL_NEWS,
                    Config.COLUMN_NEWS_ID + " = ? ",
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
