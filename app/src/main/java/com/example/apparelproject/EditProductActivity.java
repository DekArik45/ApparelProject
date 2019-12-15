package com.example.apparelproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.database.ProductQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.io.IOException;

public class EditProductActivity extends AppCompatActivity {

    EditText mNama, mDeskripsi, mHarga;
    Spinner mKategori, mSize, mWarna;
    Button save, mBrowse;
    ImageView imageView;
    Bitmap bitmapFoto;
    byte[] imageByte = null;
    String id,nama, deskripsi, harga, kategori, size, warna;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Edit Produk");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Intent data = getIntent();

        nama = data.getStringExtra(Config.COLUMN_PRODUK_NAMA);
        deskripsi = data.getStringExtra(Config.COLUMN_PRODUK_DESKRIPSI);
        kategori = data.getStringExtra(Config.COLUMN_PRODUK_KATEGORI);
        size = data.getStringExtra(Config.COLUMN_PRODUK_UKURAN);
        warna = data.getStringExtra(Config.COLUMN_PRODUK_WARNA);
        imageByte = data.getByteArrayExtra(Config.COLUMN_PRODUK_IMAGE);
        harga = data.getStringExtra(Config.COLUMN_PRODUK_HARGA);
        id = data.getStringExtra(Config.COLUMN_PRODUK_ID);

        save = findViewById(R.id.edit_produk_update);
        mNama = findViewById(R.id.edit_produk_namaproduk);
        mDeskripsi = findViewById(R.id.edit_produk_deskripsi);
        mHarga = findViewById(R.id.edit_produk_harga);
        mKategori = findViewById(R.id.edit_produk_kategori);
        mSize = findViewById(R.id.edit_produk_size);
        mWarna = findViewById(R.id.edit_produk_colour);
        mBrowse = findViewById(R.id.edit_produk_btnFoto);
        imageView = findViewById(R.id.edit_produk_viewFoto);
        Log.e("data ",id+" " +harga);
        mNama.setText(nama);
        mHarga.setText(harga);
        mDeskripsi.setText(deskripsi);

        Bitmap imageBitmap = DbBitmapUtility.getImage(imageByte);
        Glide.with(this)
                .load(imageBitmap)
                .apply(new RequestOptions())
                .into(imageView);

        mBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        spinnerColor();
        spinnerKategori();
        spinnerSize();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = mNama.getText().toString();
                deskripsi = mDeskripsi.getText().toString();
                harga = mHarga.getText().toString();

                if (imageByte != null || !nama.isEmpty() || !deskripsi.isEmpty() || !harga.isEmpty()){
                    save();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Lengkapi data terlebih dahulu", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void save(){
        Log.e("data ",id+" " +harga);
        ProductModel product = new ProductModel(Integer.parseInt(id),imageByte,nama,deskripsi,kategori,Integer.parseInt(harga),warna,size);
        ProductQuery query = new ProductQuery(getApplicationContext());

        long id =query.updateProduct(product);

        if (id>0){
            Toast.makeText(getApplicationContext(), "Data Berhasil diUpdate", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditProductActivity.this,ProductActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "data Gagal Diupdate", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmapFoto = scaleDown(bitmapFoto,true);
                //displaying selected image to imageview
                imageView.setImageBitmap(bitmapFoto);

                imageByte = DbBitmapUtility.getBytes(bitmapFoto);
                Log.e("ad",imageByte.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Bitmap scaleDown(Bitmap realImage,
                                   boolean filter) {
//        jika image lebih besar dari 1500 x 1500 maka image di ubah menjadi 800 x800
        if (realImage.getWidth() > 400 || realImage.getHeight() > 600){
            Bitmap newbitmap = Bitmap.createScaledBitmap(realImage, 400,
                    600, filter);
            return newbitmap;
        }
        else{
            return realImage;
        }
    }

    private void spinnerKategori(){
        int position = 0;

        for (int i =0;i<getResources().getStringArray(R.array.kategori).length;i++){
            if (getResources().getStringArray(R.array.kategori)[i].equals(kategori)) {
                position = i;
            }
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.kategori));

        mKategori.setAdapter(adapter1);
        kategori = adapter1.getItem(position);
        mKategori.setSelection(position, true);
        View v = mKategori.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));
        // mengeset listener untuk mengetahui saat item dipilih
        mKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kategori = adapter1.getItem(i);
                ((TextView) view).setTextColor(getResources().getColor(R.color.md_black_1000));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerSize(){
        int position = 0;

        for (int i =0;i<getResources().getStringArray(R.array.size).length;i++){
            if (getResources().getStringArray(R.array.size)[i].equals(size)) {
                position = i;
            }
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.size));

        mSize.setAdapter(adapter1);
        size = adapter1.getItem(position);
        mSize.setSelection(position, true);
        View v = mSize.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));
        // mengeset listener untuk mengetahui saat item dipilih
        mSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size = adapter1.getItem(i);
                ((TextView) view).setTextColor(getResources().getColor(R.color.md_black_1000));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerColor(){
        int position = 0;

        for (int i =0;i<getResources().getStringArray(R.array.colour).length;i++){
            if (getResources().getStringArray(R.array.colour)[i].equals(warna)) {
                position = i;
            }
        }
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.colour));

        mWarna.setAdapter(adapter1);
        warna = adapter1.getItem(position);
        mWarna.setSelection(position, true);
        View v = mWarna.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));
        // mengeset listener untuk mengetahui saat item dipilih
        mWarna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                warna = adapter1.getItem(i);
                ((TextView) view).setTextColor(getResources().getColor(R.color.md_black_1000));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
