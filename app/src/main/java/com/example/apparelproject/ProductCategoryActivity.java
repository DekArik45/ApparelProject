package com.example.apparelproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.apparelproject.adapter.ProductCategoryAdapter;
import com.example.apparelproject.data.ProductData;
import com.example.apparelproject.model.ProductModel;

import java.util.ArrayList;

public class ProductCategoryActivity extends AppCompatActivity {

    NestedScrollView mScroller;
    Toolbar mToolbar;
    AppBarLayout mAppBar;
    float opacity = 0;

    TextView mHeader;

    Cursor mCursor;

    RecyclerView mProductPopular, mProduct;
    private ArrayList<ProductModel> listPopular = new ArrayList<>();
    private ArrayList<ProductModel> list = new ArrayList<>();
    private ArrayList<ProductModel> listAll = new ArrayList<>();

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        Intent data = getIntent();

        category = data.getStringExtra("category");

        inisialisasiToolbar();

        mProduct = findViewById(R.id.product_category_recyclerProduct);
        mProductPopular = findViewById(R.id.product_category_recyclerPopularProduct);
        mHeader = findViewById(R.id.product_category_txtHeader);

        mHeader.setText(category);

        settingProductPopular();
        settingProductAll();

    }

    public void settingProductPopular(){
        listPopular.addAll(ProductData.getListData());
        mProductPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        ProductCategoryAdapter productAdapter = new ProductCategoryAdapter(this, "1", category);

//        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
//            // The Cursor is now set to the right position
//            mArrayList.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
//        }

        productAdapter.setListProduct(listPopular);
        mProductPopular.setAdapter(productAdapter);
    }

    public void settingProductAll(){
        this.setList(ProductData.getListData());

        Log.e("list = ",String.valueOf(getList().size()));
        for (int i=0; i<getList().size(); i++){
            if (category.equals(getList().get(i).getKategori())){
                listAll.add(getList().get(i));
            }
        }
//        list.addAll(ProductData.getListData());
        mProduct.setLayoutManager(new GridLayoutManager(this, 3));
        ProductCategoryAdapter productAdapter = new ProductCategoryAdapter(this, "0", category);
        productAdapter.setListProduct(listAll);
        mProduct.setAdapter(productAdapter);
    }

    private void inisialisasiToolbar(){
        mScroller = (NestedScrollView) findViewById(R.id.product_category_nestedScrollView);
        mToolbar = (Toolbar) findViewById(R.id.product_category_toolbar);
        mAppBar = (AppBarLayout) findViewById(R.id.product_category_appBarLayout);

        mToolbar.setTitle("Product");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,255,255,255), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        if (mScroller != null) {
            mScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//              scroll down
                    if (scrollY > oldScrollY) {
                        if (scrollY > 100 && scrollY <= 400){
                            settingToolbar(scrollY);
                        }
                    }
//              scroll up
                    if (scrollY < oldScrollY) {
                        if (scrollY > 100 && scrollY <= 400){
                            settingToolbar(scrollY);
                        }
                    }
//               on the line
                    if (scrollY >= 400){
                        mAppBar.setElevation(6);
                        int color = 135;

                        if (getSupportActionBar() != null){
                            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                            upArrow.setColorFilter(Color.argb(255,color,color,color), PorterDuff.Mode.SRC_ATOP);
                            getSupportActionBar().setHomeAsUpIndicator(upArrow);
                        }

                        mToolbar.setTitleTextColor(Color.argb(255,color,color,color));
                        mToolbar.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    }

//                check for top
                    if (scrollY == 0) {
                        mAppBar.setElevation(0);
                        mToolbar.setTitleTextColor(Color.argb(255,255,255,255));
                        mToolbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        mAppBar.bringToFront();
                    }

//                check for bottom
//                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
//                    Log.i(TAG, "BOTTOM SCROLL");
//                }
                }
            });
        }
    }

    private void settingToolbar(int scrollY){

        opacity = ((float)scrollY - 100) / 400;
        mAppBar.setElevation(opacity * 6);
        int color = 255 - (int)(120 * (float)opacity);

        if (getSupportActionBar() != null){
            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,255,255,255), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        mToolbar.setTitleTextColor(Color.argb(255,255,255,255));
        mToolbar.setBackgroundColor(Color.argb((int)(opacity * 255), 255, 255, 255));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

    public ArrayList<ProductModel> getList() {
        return list;
    }

    public void setList(ArrayList<ProductModel> list) {
        this.list = list;
    }
}
