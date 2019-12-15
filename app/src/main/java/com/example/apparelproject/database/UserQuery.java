package com.example.apparelproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.apparelproject.model.UserModel;
import com.example.apparelproject.utils.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserQuery {

    private Context context;

    public UserQuery(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertUser(UserModel User){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_USER_NAMA, User.getNama());
        contentValues.put(Config.COLUMN_USER_USERNAME, User.getUsername());
        contentValues.put(Config.COLUMN_USER_PASSWORD, User.getPassword());
        contentValues.put(Config.COLUMN_USER_IMAGE, User.getImage());
        contentValues.put(Config.COLUMN_USER_JENISKELAMIN, User.getJenis_kelamin());
        contentValues.put(Config.COLUMN_USER_EMAIL, User.getEmail());
        contentValues.put(Config.COLUMN_USER_ALAMAT, User.getAlamat());
        contentValues.put(Config.COLUMN_USER_HAKAKSES, User.getHak_akses());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABEL_USER, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<UserModel> getAllUser(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_USER, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<UserModel> UserList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_NAMA));
                        String username = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_USERNAME));
                        String password = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_PASSWORD));
                        byte[] image = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_USER_IMAGE));
                        String jenis_kelamin = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_JENISKELAMIN));
                        String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_EMAIL));
                        String alamat = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_ALAMAT));
                        String hak_akses = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_HAKAKSES));

                        UserList.add(new UserModel(id,name,username,password,image,jenis_kelamin,email,alamat,hak_akses));
                    }   while (cursor.moveToNext());

                    return UserList;
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

    public UserModel getUserByID(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        UserModel User = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_USER, null,
                    Config.COLUMN_USER_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                int idUser = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_NAMA));
                String username = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_PASSWORD));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_USER_IMAGE));
                String jenis_kelamin = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_JENISKELAMIN));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_EMAIL));
                String alamat = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_ALAMAT));
                String hak_akses = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_HAKAKSES));

                User = new UserModel(id,name,username,password,image,jenis_kelamin,email,alamat,hak_akses);

            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return User;
    }

    public long updateUser(UserModel User){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_USER_NAMA, User.getNama());
        contentValues.put(Config.COLUMN_USER_USERNAME, User.getUsername());
        contentValues.put(Config.COLUMN_USER_PASSWORD, User.getPassword());
        contentValues.put(Config.COLUMN_USER_IMAGE, User.getImage());
        contentValues.put(Config.COLUMN_USER_JENISKELAMIN, User.getJenis_kelamin());
        contentValues.put(Config.COLUMN_USER_EMAIL, User.getEmail());
        contentValues.put(Config.COLUMN_USER_ALAMAT, User.getAlamat());
        contentValues.put(Config.COLUMN_USER_HAKAKSES, User.getHak_akses());

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_USER, contentValues,
                    Config.COLUMN_USER_ID + " = ? ",
                    new String[] {String.valueOf(User.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteUserByID(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABEL_USER,
                    Config.COLUMN_USER_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public UserModel Authenticate(UserModel user) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Config.TABEL_USER, null,
                Config.COLUMN_USER_USERNAME + " = ? ", new String[]{String.valueOf(user.getUsername())},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            UserModel user1 = new UserModel(
                cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ID)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_NAMA)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_USERNAME)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_PASSWORD)),
                cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_USER_IMAGE)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_JENISKELAMIN)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_EMAIL)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_ALAMAT)),
                cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_HAKAKSES))
            );

            //Match both passwords check they are same or not
            if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
                return user1;
            }
        }
        //if user password does not matches or there is no record with that email then return @false
        return null;
    }
}
