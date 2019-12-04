package com.example.apparelproject;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.apparelproject.adapter.ListTransactionAdapter;
import com.example.apparelproject.data.TransactionData;
import com.example.apparelproject.model.Transaction;

import java.util.ArrayList;

public class PendingPaymentActivity extends AppCompatActivity {

    RecyclerView mRecylcer;
    private ArrayList<Transaction> list = new ArrayList<>();
    private ArrayList<Transaction> listData = new ArrayList<>();

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_payment);

        mRecylcer = findViewById(R.id.pending_payment_recyclerProduct);
        settingToolbar();
        settingProductAll();
    }

    public void settingProductAll(){

        this.setListData(TransactionData.getListData());

        for (int i=0; i<getListData().size(); i++){
            if (getListData().get(i).getKetBayar().equals("Belum bayar")){
                list.add(getListData().get(i));
            }
        }

        mRecylcer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        ListTransactionAdapter productAdapter = new ListTransactionAdapter(this, "Belum bayar");
        productAdapter.setListTransaction(list);
        mRecylcer.setAdapter(productAdapter);
    }

    public ArrayList<Transaction> getListData() {
        return listData;
    }

    public void setListData(ArrayList<Transaction> listData) {
        this.listData = listData;
    }

    public void settingToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.pending_payment_toolbar);

        mToolbar.setTitle("Pending Payment");
        mToolbar.setTitleTextColor(getColor(R.color.colorPrimaryText));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,135,135,135), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
