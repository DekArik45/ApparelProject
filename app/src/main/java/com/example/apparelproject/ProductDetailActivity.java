package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.constants.Fields;

import java.util.HashSet;
import java.util.Set;

public class ProductDetailActivity  extends AppCompatActivity {

    TextView mTitle, mPrice, mBrand, mColor, mMaterial, mSize, mCategory;
    ImageView mImage;
    Button mBtnCart;

    String title, price, brand, color, material, size, category, image;

    Toolbar mToolbar;

    private SharedPreferences mSharedPreferences;
    Boolean session = false;

    //Set the values
    Set<String> listCartNama = new HashSet<String>();
    Set<String> listCartPrice = new HashSet<String>();
    Set<String> listCartFoto = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mSharedPreferences = getSharedPreferences(Fields.PRODUCT_PREFERENCE, Context.MODE_PRIVATE);
        session = mSharedPreferences.getBoolean(Fields.SESSION_STATUS,false);


        Intent data = getIntent();
        title = data.getStringExtra("title");
        price = data.getStringExtra("price");
        brand = data.getStringExtra("brand");
        color = data.getStringExtra("color");
        material = data.getStringExtra("material");
        size = data.getStringExtra("size");
        category = data.getStringExtra("category");
        image = data.getStringExtra("image");

        mTitle = findViewById(R.id.product_detail_name);
        mPrice = findViewById(R.id.product_detail_price);
        mBrand = findViewById(R.id.product_detail_brand);
        mColor = findViewById(R.id.product_detail_color);
        mMaterial = findViewById(R.id.product_detail_material);
        mSize = findViewById(R.id.product_detail_size);
        mCategory= findViewById(R.id.product_detail_category);
        mImage = findViewById(R.id.product_detail_image);

        mBtnCart = findViewById(R.id.product_detail_buttonCart);

        settingToolbar();

        mTitle.setText(title);
        mPrice.setText(price);
        mBrand.setText(brand);
        mColor.setText(color);
        mMaterial.setText(material);
        mSize.setText(size);
        mCategory.setText(category);

        Glide.with(this)
                .load(image)
                .apply(new RequestOptions())
                .into(mImage);

        mBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCartNama.add(title);
                listCartPrice.add(price);
                listCartFoto.add(image);

                //Retrieve the values
//                Set<String> set = myScores.getStringSet("key", null);

                Toast.makeText(getApplicationContext(), title+" telah ditambahkan ke cart", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.putStringSet(Fields.PRODUCT_NAME,listCartNama);
                mEditor.putStringSet(Fields.PRODUCT_PRICE,listCartPrice);
                mEditor.putStringSet(Fields.PRODUCT_FOTO,listCartFoto);
                mEditor.apply();

                Log.e("size = ",String.valueOf(listCartNama.size()));
            }
        });

    }

    public void settingToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.product_detail_toolbar);

        mToolbar.setTitle("");
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
