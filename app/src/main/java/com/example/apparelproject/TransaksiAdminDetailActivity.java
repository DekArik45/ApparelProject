package com.example.apparelproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.adapter.ProductAdminAdapter;
import com.example.apparelproject.adapter.TransaksiProdukDibeli;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;

public class TransaksiAdminDetailActivity extends AppCompatActivity {

    TextView mNamaUser, mTotal, mJumlah, mStatus;
    ImageView buktiPembayaran;
    RecyclerView rv;
    String namaUser,jumlah,harga,status,tanggal,statusRiwayat;
    Bitmap imageBitmap;
    FloatingActionButton fab;
    byte[] image;

    private ArrayList<TransactionModel> list = new ArrayList<>();

    TransaksiProdukDibeli transaksiProdukDibeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_admin_detail);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Detail Transaksi");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Intent data = getIntent();
        namaUser = data.getStringExtra(Config.COLUMN_TRX_NAMA_USER);
        jumlah = data.getStringExtra(Config.COLUMN_TRX_JUMLAH);
        harga= data.getStringExtra(Config.COLUMN_TRX_HARGA);
        status= data.getStringExtra(Config.COLUMN_TRX_STATUS);
        tanggal= data.getStringExtra(Config.COLUMN_TRX_TANGGAL);
        image= data.getByteArrayExtra(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN);
        statusRiwayat = data.getStringExtra("statusRiwayat");

        mNamaUser = findViewById(R.id.detail_transaksi_admin_namaUser);
        mTotal = findViewById(R.id.detail_transaksi_admin_grandTotal);
        mJumlah = findViewById(R.id.detail_transaksi_admin_jumlahBelanja);
        mStatus = findViewById(R.id.detail_transaksi_admin_status);
        fab = findViewById(R.id.detail_transaksi_admin_fab);

        buktiPembayaran = findViewById(R.id.detail_transaksi_admin_image);
        rv = findViewById(R.id.detail_transaksi_admin_recyclerVew);

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
                Intent intent = new Intent(TransaksiAdminDetailActivity.this,UpdateStatusTransaksiActivity.class);
                intent.putExtra(Config.COLUMN_TRX_TANGGAL,tanggal);
                intent.putExtra(Config.COLUMN_TRX_STATUS,status);
                startActivityForResult(intent,1);
            }
        });
    }

    private void settingRecycler(){
        TransactionQuery transactionQuery = new TransactionQuery(this);
        list.addAll(transactionQuery.getAllTransactionNotCartDetail(tanggal));

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transaksiProdukDibeli = new TransaksiProdukDibeli(this);
        transaksiProdukDibeli.setList(list);
        rv.setAdapter(transaksiProdukDibeli);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            Log.e("status","kaka "+data.getStringExtra("status"));

//            finish();
//            overridePendingTransition(0, 0);
//            startActivity(getIntent());
//            overridePendingTransition(0, 0);
            mStatus.setText(data.getStringExtra("status"));
            status = data.getStringExtra("status");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (statusRiwayat.equals("riwayat")){
            Intent i = new Intent(TransaksiAdminDetailActivity.this,RiwayatActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Intent i = new Intent(TransaksiAdminDetailActivity.this,TransaksiAdminActivity.class);
            startActivity(i);
            finish();
        }

    }
}
