package com.example.apparelproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.apparelproject.model.Transaction;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView mRecylcer;
    private ArrayList<Transaction> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mRecylcer = findViewById(R.id.pending_payment_recyclerProduct);

//        settingProductAll();
    }

//    public void settingProductAll(){
//        list.addAll(TransactionData.getListData());
//        mRecylcer.setLayoutManager(new GridLayoutManager(this, 3));
//        ListTransactionAdapter productAdapter = new ListTransactionAdapter(this);
//        productAdapter.setListTransaction(list);
//        mRecylcer.setAdapter(productAdapter);
//    }
}
