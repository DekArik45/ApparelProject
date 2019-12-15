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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apparelproject.database.NewsQuery;
import com.example.apparelproject.model.NewsModel;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.io.IOException;

public class CreateNewsActivity extends AppCompatActivity {
    EditText mNama, mDeskripsi;
    Button save, mBrowse;
    ImageView imageView;
    Bitmap bitmapFoto;
    byte[] imageByte = null;
    String nama, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Tambah News");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mNama = findViewById(R.id.create_news_judul);
        mDeskripsi = findViewById(R.id.create_news_deskripsi);
        mBrowse = findViewById(R.id.create_news_btnaddimage);
        save = findViewById(R.id.create_news_save);
        imageView = findViewById(R.id.create_news_addimage);

        mBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void save(){
        nama = mNama.getText().toString();
        deskripsi = mDeskripsi.getText().toString();

        NewsModel news = new NewsModel(-1,nama,deskripsi,imageByte);
        NewsQuery query = new NewsQuery(getApplicationContext());

        long id =query.insertNews(news);

        if (id>0){
            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreateNewsActivity.this,NewsActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "data Gagal Disimpan", Toast.LENGTH_LONG).show();
        }
    }

    public static Bitmap scaleDown(Bitmap realImage,
                                   boolean filter) {
//        jika image lebih besar dari 1500 x 1500 maka image di ubah menjadi 800 x800
        if (realImage.getWidth() > 600 || realImage.getHeight() > 300){
            Bitmap newbitmap = Bitmap.createScaledBitmap(realImage, 600,
                    300, filter);
            return newbitmap;
        }
        else{
            return realImage;
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
