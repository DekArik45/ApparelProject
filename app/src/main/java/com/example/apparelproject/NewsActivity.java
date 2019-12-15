package com.example.apparelproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.apparelproject.adapter.NewsAdminAdapter;
import com.example.apparelproject.adapter.ProductAdminAdapter;
import com.example.apparelproject.database.NewsQuery;
import com.example.apparelproject.database.ProductQuery;
import com.example.apparelproject.model.NewsModel;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    RecyclerView rv;
    FloatingActionButton fab;
    Toolbar toolbar;

    private ArrayList<NewsModel> list = new ArrayList<>();
    NewsQuery newsQuery;
    NewsAdminAdapter newsAdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("News");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        rv = findViewById(R.id.news_recyclerView);
        fab = findViewById(R.id.news_add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,CreateNewsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        newsQuery = new NewsQuery(this);
        settingRecyclerView();
    }

    private void settingRecyclerView(){

        list.addAll(newsQuery.getAllNews());

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newsAdminAdapter = new NewsAdminAdapter(this);
        newsAdminAdapter.setListNews(list);
        rv.setAdapter(newsAdminAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
