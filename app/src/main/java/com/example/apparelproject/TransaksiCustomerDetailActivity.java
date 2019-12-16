package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.adapter.TransaksiProdukDibeli;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.io.IOException;
import java.util.ArrayList;

public class TransaksiCustomerDetailActivity extends AppCompatActivity {

    TextView mNamaUser, mTotal, mJumlah, mStatus;
    ImageView buktiPembayaran;
    RecyclerView rv;
    String namaUser,jumlah,harga,status,tanggal,statusRiwayat;
    String nama_user;
    Bitmap imageBitmap;
    byte[] image;
    FloatingActionButton fab;

    Bitmap bitmapFoto;
    byte[] imageByte;

    private ArrayList<TransactionModel> list = new ArrayList<>();

    TransaksiProdukDibeli transaksiProdukDibeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_customer_detail);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Detail Transaksi");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        SharedPreferences sharedpreferences = this.getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        nama_user = sharedpreferences.getString(Config.COLUMN_USER_NAMA, "");

        Intent data = getIntent();
        namaUser = data.getStringExtra(Config.COLUMN_TRX_NAMA_USER);
        jumlah = data.getStringExtra(Config.COLUMN_TRX_JUMLAH);
        harga= data.getStringExtra(Config.COLUMN_TRX_HARGA);
        status= data.getStringExtra(Config.COLUMN_TRX_STATUS);
        tanggal= data.getStringExtra(Config.COLUMN_TRX_TANGGAL);
        image= data.getByteArrayExtra(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN);
        statusRiwayat = data.getStringExtra("statusRiwayat");

        mNamaUser = findViewById(R.id.detail_transaksi_customer_namaUser);
        mTotal = findViewById(R.id.detail_transaksi_customer_grandTotal);
        mJumlah = findViewById(R.id.detail_transaksi_customer_jumlahBelanja);
        mStatus = findViewById(R.id.detail_transaksi_customer_status);
        fab = findViewById(R.id.detail_transaksi_customer_fab);

        buktiPembayaran = findViewById(R.id.detail_transaksi_customer_image);
        rv = findViewById(R.id.detail_transaksi_customer_recyclerVew);

        if (image != null){
            imageBitmap = DbBitmapUtility.getImage(image);
            Glide.with(this)
                    .load(imageBitmap)
                    .apply(new RequestOptions())
                    .into(buktiPembayaran);
        }
//        int total = Integer.parseInt(harga) * Integer.parseInt(jumlah);
        mNamaUser.setText(namaUser);
        mJumlah.setText(jumlah);
        mTotal.setText("Rp."+String.valueOf(harga));
        if (status.equals(Config.STATUS_TRX_DITOLAK)){
            mStatus.setTextColor(getColor(R.color.md_red_500));
        }
        else if (status.equals(Config.STATUS_TRX_SELESAI)){
            mStatus.setTextColor(getColor(R.color.md_green_500));
        }
        else{
            mStatus.setTextColor(getColor(R.color.md_blue_500));
        }
        mStatus.setText(status);
        settingRecycler();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
    }

    private void settingRecycler(){
        TransactionQuery transactionQuery = new TransactionQuery(this);
        list.addAll(transactionQuery.getAllTransactionNotCartDetailMember(nama_user,tanggal));

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transaksiProdukDibeli = new TransaksiProdukDibeli(this);
        transaksiProdukDibeli.setList(list);
        rv.setAdapter(transaksiProdukDibeli);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void updateBukti(){
        TransactionQuery query = new TransactionQuery(getApplicationContext());

        long id =query.updateTransactionBuktiPembayaran(tanggal,imageByte);

        if (id>0){
            Toast.makeText(getApplicationContext(), "Data Berhasil diUpdate", Toast.LENGTH_LONG).show();
            buktiPembayaran.setImageBitmap(bitmapFoto);
            status = Config.STATUS_TRX_MENUNGGUVERIFIKASI;
        }
        else{
            Toast.makeText(getApplicationContext(), "data Gagal Diupdate", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmapFoto = scaleDown(bitmapFoto,true);
                //displaying selected image to imageview
                imageByte = DbBitmapUtility.getBytes(bitmapFoto);
                updateBukti();

                Log.e("ad",imageByte.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Bitmap scaleDown(Bitmap realImage,
                                   boolean filter) {
//        jika image lebih besar dari 1500 x 1500 maka image di ubah menjadi 800 x800
        if (realImage.getWidth() > 500 || realImage.getHeight() > 500){
            Bitmap newbitmap = Bitmap.createScaledBitmap(realImage, 500,
                    500, filter);
            return newbitmap;
        }
        else{
            return realImage;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (statusRiwayat.equals("riwayat")){
            Intent i = new Intent(TransaksiCustomerDetailActivity.this,RiwayatActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(TransaksiCustomerDetailActivity.this,TransaksiCustomerActivity.class);
            startActivity(i);
            finish();
        }
    }
}
