package com.example.apparelproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.apparelproject.adapter.ProductAdminAdapter;
import com.example.apparelproject.database.ProductQuery;
import com.example.apparelproject.model.ProductModel;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView rv;
    FloatingActionButton fab;
    Toolbar toolbar;

    private ArrayList<ProductModel> list = new ArrayList<>();
    ProductQuery productQuery;
    ProductAdminAdapter productAdminAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Product");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        rv = findViewById(R.id.product_recyclerView);
        fab = findViewById(R.id.product_add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this,CreateProdukActivity.class);
                startActivity(intent);
                finish();
            }
        });
        productQuery = new ProductQuery(this);
        settingRecyclerView();
    }

    private void settingRecyclerView(){

        list.addAll(productQuery.getAllProduct());

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productAdminAdapter = new ProductAdminAdapter(this);
        productAdminAdapter.setListProduct(list);
        rv.setAdapter(productAdminAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
