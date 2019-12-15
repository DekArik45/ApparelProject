package com.example.apparelproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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

import com.example.apparelproject.database.ProductQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProdukActivity extends AppCompatActivity {

    EditText mNama, mDeskripsi, mHarga;
    Spinner mKategori, mSize, mWarna;
    Button save, mBrowse;
    ImageView imageView;
    Bitmap bitmapFoto;
    byte[] imageByte = null;
    String nama, deskripsi, harga, kategori, size, warna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_produk);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Tambah Produk");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        save = findViewById(R.id.create_produk_save);
        mNama = findViewById(R.id.create_produk_namaproduk);
        mDeskripsi = findViewById(R.id.create_produk_deskripsi);
        mHarga = findViewById(R.id.create_produk_harga);
        mKategori = findViewById(R.id.create_produk_kategori);
        mSize = findViewById(R.id.create_produk_size);
        mWarna = findViewById(R.id.create_produk_colour);
        mBrowse = findViewById(R.id.create_produk_btnFoto);
        imageView = findViewById(R.id.create_produk_viewFoto);

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
    }

    private void save(){
        ProductModel product = new ProductModel(-1,imageByte,nama,deskripsi,kategori,Integer.parseInt(harga),warna,size);
        ProductQuery query = new ProductQuery(getApplicationContext());

        long id =query.insertProduct(product);

        if (id>0){
            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreateProdukActivity.this,ProductActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "data Gagal Disimpan", Toast.LENGTH_LONG).show();
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

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.kategori));

        mKategori.setAdapter(adapter1);
        kategori = adapter1.getItem(0);
        mKategori.setSelection(0, true);
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

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.size));

        mSize.setAdapter(adapter1);
        size = adapter1.getItem(0);
        mSize.setSelection(0, true);
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

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.colour));

        mWarna.setAdapter(adapter1);
        warna = adapter1.getItem(0);
        mWarna.setSelection(0, true);
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
