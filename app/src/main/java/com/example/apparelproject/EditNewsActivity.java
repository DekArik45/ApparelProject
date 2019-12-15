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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.database.NewsQuery;
import com.example.apparelproject.model.NewsModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.io.IOException;

public class EditNewsActivity extends AppCompatActivity {
    EditText mNama, mDeskripsi;
    Button save, mBrowse;
    ImageView imageView;
    Bitmap bitmapFoto;
    byte[] imageByte = null;
    String nama, deskripsi,id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Edit News");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Intent data = getIntent();

        nama = data.getStringExtra(Config.COLUMN_NEWS_JUDUL);
        deskripsi = data.getStringExtra(Config.COLUMN_NEWS_DESKRIPSI);
        imageByte = data.getByteArrayExtra(Config.COLUMN_NEWS_IMAGE);
        id = data.getStringExtra(Config.COLUMN_NEWS_ID);

        mNama = findViewById(R.id.edit_news_judul);
        mDeskripsi = findViewById(R.id.edit_news_deskripsi);
        mBrowse = findViewById(R.id.edit_news_btnaddimage);
        save = findViewById(R.id.edit_news_save);
        imageView = findViewById(R.id.edit_news_addimage);

        mNama.setText(nama.toString());
        mDeskripsi.setText(deskripsi.toString());
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

        NewsModel news = new NewsModel(Integer.parseInt(id),nama,deskripsi,imageByte);
        NewsQuery query = new NewsQuery(getApplicationContext());

        long id =query.updateNews(news);

        if (id>0){
            Toast.makeText(getApplicationContext(), "Data Berhasil diUpdate", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditNewsActivity.this,NewsActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "data Gagal diUpdate", Toast.LENGTH_LONG).show();
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
