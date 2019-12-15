package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.database.ProductQuery;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDetailActivity  extends AppCompatActivity {

    TextView mTitle, mPrice, mColor, mDeskripsi, mSize, mCategory, mPlus, mJumlah, mMin;
    ImageView mImage;
    Button mBtnCart;

    String title, price, color, deskripsi, size, category;

    Toolbar mToolbar;

    private SharedPreferences mSharedPreferences;
    Boolean session = false;
    byte [] image = null;
    Bitmap imageBitmap;
    //Set the values
    TransactionModel transactionModel;
    TransactionQuery transactionQuery;
    private ArrayList<TransactionModel> list = new ArrayList<>();

    CardView cardView;
    String namaUser;
    long idTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        transactionQuery = new TransactionQuery(this);

        mSharedPreferences = getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        session = mSharedPreferences.getBoolean(Config.SESSION,false);

        Intent data = getIntent();
        title = data.getStringExtra(Config.COLUMN_PRODUK_NAMA);
        price = data.getStringExtra(Config.COLUMN_PRODUK_HARGA);
        color = data.getStringExtra(Config.COLUMN_PRODUK_WARNA);
        size = data.getStringExtra(Config.COLUMN_PRODUK_UKURAN);
        category = data.getStringExtra(Config.COLUMN_PRODUK_KATEGORI);
        image = data.getByteArrayExtra(Config.COLUMN_PRODUK_IMAGE);
        deskripsi = data.getStringExtra(Config.COLUMN_PRODUK_DESKRIPSI);

        mTitle = findViewById(R.id.product_detail_name);
        mPrice = findViewById(R.id.product_detail_price);
        mColor = findViewById(R.id.product_detail_color);
        mDeskripsi = findViewById(R.id.product_detail_deskripsi);
        mSize = findViewById(R.id.product_detail_size);
        mCategory= findViewById(R.id.product_detail_category);
        mImage = findViewById(R.id.product_detail_image);
        mPlus = findViewById(R.id.produk_detail_plus);
        mMin = findViewById(R.id.produk_detail_min);
        mJumlah = findViewById(R.id.produk_detail_jumlah);
        cardView = findViewById(R.id.product_detail_cardViewJumlah);

        mBtnCart = findViewById(R.id.product_detail_buttonCart);

        cardView.setVisibility(View.GONE);

        settingToolbar();

        mTitle.setText(title);
        mPrice.setText("Rp."+price);
        mColor.setText(color);
        mDeskripsi.setText(deskripsi);
        mSize.setText(size);
        mCategory.setText(category);

        imageBitmap = DbBitmapUtility.getImage(image);
        Glide.with(this)
                .load(imageBitmap)
                .apply(new RequestOptions())
                .into(mImage);

        if (session){
            cekCart();
            Log.e("id","gksdf g"+idTransaction);
            namaUser = mSharedPreferences.getString(Config.COLUMN_USER_NAMA,"");
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart();
                }
            });

            mPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plusCart();
                }
            });

            mMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minCart();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "you need to login first!!", Toast.LENGTH_LONG).show();
        }
    }

    private void cekCart(){
        list.addAll(transactionQuery.getAllTransaction());
        Log.e("id","gg o"+idTransaction);
        for (int i =0;i<list.size();i++){
            Log.e("id","gg d"+list.get(i).getId());
            if (list.get(i).getNama_produk().equals(title) && list.get(i).getNama_user().equals(mSharedPreferences.getString(Config.COLUMN_USER_NAMA,"")) && list.get(i).getStatus().equals(Config.STATUS_TRX_CART)) {
                idTransaction = list.get(i).getId();
                Log.e("id","gg"+idTransaction);
                if (list.get(i).getJumlah() > 0){
                    mJumlah.setText(String.valueOf(list.get(i).getJumlah()));
                    mBtnCart.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                }
                else{
                    mBtnCart.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void plusCart(){
        int jumlah = Integer.parseInt(mJumlah.getText().toString());
        jumlah += 1;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy H:m");
        String currenttime = df.format(c);

        TransactionModel transactionModel = new TransactionModel(idTransaction,namaUser,currenttime.toString(),image,title,Integer.parseInt(price),jumlah,Config.STATUS_TRX_CART,null);
        TransactionQuery query = new TransactionQuery(getApplicationContext());

        long id =query.updateTransaction(transactionModel);

        if (id>0){
//            Toast.makeText(getApplicationContext(), title+" telah ditambahkan ke cart", Toast.LENGTH_SHORT).show();
            mJumlah.setText(String.valueOf(jumlah));
            mBtnCart.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
        }
        else {
            Log.e("erorr","gagal menambahkan");
//            Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
        }
    }

    private void minCart(){
        int jumlah = Integer.parseInt(mJumlah.getText().toString());

        if (jumlah <1 ){
            deleteCard();
            mBtnCart.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.INVISIBLE);
        }
        else if (jumlah > 0){
            jumlah -= 1;
            if (jumlah < 1) {
                deleteCard();
                mBtnCart.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.INVISIBLE);
            }
            else{
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy H:m");
                String currentTime = df.format(c);

                TransactionModel transactionModel = new TransactionModel(idTransaction,namaUser,currentTime.toString(),image,title,Integer.parseInt(price),jumlah,Config.STATUS_TRX_CART,null);
                TransactionQuery query = new TransactionQuery(getApplicationContext());

                long id =query.updateTransaction(transactionModel);

                if (id>0){
//                    Toast.makeText(getApplicationContext(), title+" telah ditambahkan ke cart", Toast.LENGTH_SHORT).show();
                    mJumlah.setText(String.valueOf(jumlah));
                    mBtnCart.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                }
                else {
//                    Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
                    Log.e("erorr","gagal Mengurangi");
                }
            }
        }
    }

    private void deleteCard(){

        TransactionQuery query = new TransactionQuery(getApplicationContext());

        long count =query.deleteTransactionByID(idTransaction);

        if (count>0){
//            Toast.makeText(getApplicationContext(), title+" telah ditambahkan ke cart", Toast.LENGTH_SHORT).show();
            mJumlah.setText("1");
            mBtnCart.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
        }
        else {
//            Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
            Log.e("erorr","gagal mendelete");
        }
    }

    private void addToCart(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy H:m");
        String currentTime = df.format(c);

        TransactionModel transactionModel = new TransactionModel(-1,namaUser,currentTime.toString(),image,title,Integer.parseInt(price),1,Config.STATUS_TRX_CART,null);
        TransactionQuery query = new TransactionQuery(getApplicationContext());

        idTransaction =query.insertTransaction(transactionModel);

        if (idTransaction>0){
            Toast.makeText(getApplicationContext(), title+" telah ditambahkan ke cart", Toast.LENGTH_SHORT).show();
            mJumlah.setText("1");
            mBtnCart.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
        }
    }

    public void settingToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.product_detail_toolbar);

        mToolbar.setTitle("Detail Produk");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable upArrow = getApplicationContext().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,255,255,255), PorterDuff.Mode.SRC_ATOP);
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
