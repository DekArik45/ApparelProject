package com.example.apparelproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionQuery {

    private Context context;

    public TransactionQuery(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertTransaction(TransactionModel Transaction){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TRX_NAMA_USER, Transaction.getNama_user());
        contentValues.put(Config.COLUMN_TRX_TANGGAL, Transaction.getTanggal());
        contentValues.put(Config.COLUMN_TRX_IMAGE_PRODUK, Transaction.getImage());
        contentValues.put(Config.COLUMN_TRX_NAMA_PRODUK, Transaction.getNama_produk());
        contentValues.put(Config.COLUMN_TRX_HARGA, Transaction.getHarga());
        contentValues.put(Config.COLUMN_TRX_JUMLAH, Transaction.getJumlah());
        contentValues.put(Config.COLUMN_TRX_STATUS, Transaction.getStatus());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABEL_TRX, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<TransactionModel> getAllTransaction(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_TRX, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));


                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,null));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionCart(String nama_user){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_TRX, null,
                    Config.COLUMN_TRX_NAMA_USER + " = ? and " + Config.COLUMN_TRX_STATUS + " = ? ", new String[]{nama_user,Config.STATUS_TRX_CART},
                    null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));


                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionRiwayatHeaderMember(String nama_user){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("select _id,nama_user,image_produk,nama_produk,SUM(harga*jumlah) as harga, SUM(jumlah) as jumlah, tanggal, status, bukti_pembayaran from tb_transaksi where nama_user = ? and status = ? or status = ? group by tanggal",new String[]{nama_user,Config.STATUS_TRX_SELESAI,Config.STATUS_TRX_DITOLAK});

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));


                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionNotCartHeaderMember(String nama_user){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("select _id,nama_user,image_produk,nama_produk,SUM(harga*jumlah) as harga, SUM(jumlah) as jumlah, tanggal, status, bukti_pembayaran from tb_transaksi where nama_user = ? and status != ? and status != ? and status != ? group by tanggal",new String[]{nama_user,Config.STATUS_TRX_CART,Config.STATUS_TRX_SELESAI,Config.STATUS_TRX_DITOLAK});

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));


                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionNotCartHeader(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("select _id,nama_user,image_produk,nama_produk,SUM(harga*jumlah) as harga, SUM(jumlah) as jumlah, tanggal, status, bukti_pembayaran from tb_transaksi where status != ? and status != ?  and status != ? group by tanggal",new String[]{Config.STATUS_TRX_CART,Config.STATUS_TRX_DITOLAK,Config.STATUS_TRX_SELESAI});

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));

                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionRiwayatHeader(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("select _id,nama_user,image_produk,nama_produk,SUM(harga*jumlah) as harga, SUM(jumlah) as jumlah, tanggal, status, bukti_pembayaran from tb_transaksi where status = ? or status = ? group by tanggal",new String[]{Config.STATUS_TRX_DITOLAK,Config.STATUS_TRX_SELESAI});

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));

                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionNotCartDetail(String tgl){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("select _id,nama_user,image_produk,nama_produk,harga, jumlah, tanggal, status, bukti_pembayaran from tb_transaksi where status != ? and tanggal = ? ",new String[]{Config.STATUS_TRX_CART,tgl});

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));

                        TransactionList.add(new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public List<TransactionModel> getAllTransactionNotCartDetailMember(String namaUser,String tgl){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("select _id,nama_user,image_produk,nama_produk,harga, jumlah, tanggal, status, bukti_pembayaran from tb_transaksi where status != ? and nama_user = ? and tanggal = ? ",new String[]{Config.STATUS_TRX_CART,namaUser,tgl});

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TransactionModel> TransactionList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                        String nama_user  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                        String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                        byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                        String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                        int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                        int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                        String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                        byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));

                        TransactionList.add(new TransactionModel(id,nama_user,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran));
                    }   while (cursor.moveToNext());

                    return TransactionList;
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

    public TransactionModel getTransactionByID(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        TransactionModel Transaction = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABEL_TRX, null,
                    Config.COLUMN_TRX_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                int idTransaction = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_ID));
                String namaUser  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_USER));
                String namaProduk  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_NAMA_PRODUK));
                byte[] image  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_PRODUK));
                String tanggal  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_TANGGAL));
                int harga  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_HARGA));
                int jumlah  = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TRX_JUMLAH));
                String status  = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TRX_STATUS));
                byte[] bukti_pembayaran  = cursor.getBlob(cursor.getColumnIndex(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN));

                Transaction = new TransactionModel(id,namaUser,tanggal,image,namaProduk,harga,jumlah,status,bukti_pembayaran);

            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Transaction;
    }

    public long updateTransactionTolak(String tgl){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TRX_STATUS,Config.STATUS_TRX_DITOLAK);

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_TRX, contentValues,
                    Config.COLUMN_TRX_TANGGAL + " = ? ",
                    new String[] {String.valueOf(tgl)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long updateTransactionStatus(String tgl,String status){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TRX_STATUS,status);

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_TRX, contentValues,
                    Config.COLUMN_TRX_TANGGAL + " = ? ",
                    new String[] {String.valueOf(tgl)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long updateTransactionBuktiPembayaran(String tgl,byte[] image){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN,image);
        contentValues.put(Config.COLUMN_TRX_STATUS,Config.STATUS_TRX_MENUNGGUVERIFIKASI);

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_TRX, contentValues,
                    Config.COLUMN_TRX_TANGGAL + " = ? ",
                    new String[] {String.valueOf(tgl)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long updateTransaction(TransactionModel Transaction){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TRX_NAMA_USER, Transaction.getNama_user());
        contentValues.put(Config.COLUMN_TRX_IMAGE_PRODUK, Transaction.getImage());
        contentValues.put(Config.COLUMN_TRX_NAMA_PRODUK, Transaction.getNama_produk());
        contentValues.put(Config.COLUMN_TRX_TANGGAL, Transaction.getTanggal());
        contentValues.put(Config.COLUMN_TRX_HARGA, Transaction.getHarga());
        contentValues.put(Config.COLUMN_TRX_JUMLAH, Transaction.getJumlah());
        contentValues.put(Config.COLUMN_TRX_STATUS, Transaction.getStatus());

        try {
            rowCount = sqLiteDatabase.update(Config.TABEL_TRX, contentValues,
                    Config.COLUMN_TRX_ID + " = ? ",
                    new String[] {String.valueOf(Transaction.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteTransactionByID(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABEL_TRX,
                    Config.COLUMN_TRX_ID + " = ? ",
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
