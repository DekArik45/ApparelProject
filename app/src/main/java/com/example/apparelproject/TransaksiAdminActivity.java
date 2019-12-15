package com.example.apparelproject;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.apparelproject.adapter.ProductAdminAdapter;
import com.example.apparelproject.adapter.TransaksiAdminAdapter;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.TransactionModel;

import java.util.ArrayList;

public class TransaksiAdminActivity extends AppCompatActivity {

    private ArrayList<TransactionModel> list = new ArrayList<>();
    TransactionQuery transactionQuery;
    TransaksiAdminAdapter transaksiAdminAdapter;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_admin);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Transaksi");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        rv = findViewById(R.id.transaksi_admin_recyclerview);
        transactionQuery = new TransactionQuery(this);
        list.addAll(transactionQuery.getAllTransactionNotCartHeader());

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transaksiAdminAdapter = new TransaksiAdminAdapter(this);
        transaksiAdminAdapter.setStatus("admin");
        transaksiAdminAdapter.setList(list);
        rv.setAdapter(transaksiAdminAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
