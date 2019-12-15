package com.example.apparelproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.apparelproject.adapter.TransaksiAdminAdapter;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;

import java.util.ArrayList;

public class RiwayatActivity extends AppCompatActivity {
    private ArrayList<TransactionModel> list = new ArrayList<>();
    TransactionQuery transactionQuery;
    TransaksiAdminAdapter transaksiAdminAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Riwayat");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        rv = findViewById(R.id.riwayat_recyclerView);
        transactionQuery = new TransactionQuery(this);
        SharedPreferences sharedpreferences = this.getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        String namaUser = sharedpreferences.getString(Config.COLUMN_USER_NAMA,"");
        String hakAkses = sharedpreferences.getString(Config.COLUMN_USER_HAKAKSES,"");
        if (hakAkses.equals("Customer")){
            list.addAll(transactionQuery.getAllTransactionRiwayatHeaderMember(namaUser));
        }
        else if (hakAkses.equals("Admin")){
            list.addAll(transactionQuery.getAllTransactionRiwayatHeader());
        }

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transaksiAdminAdapter = new TransaksiAdminAdapter(this);
        transaksiAdminAdapter.setStatus("riwayat");
        transaksiAdminAdapter.setList(list);
        rv.setAdapter(transaksiAdminAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
