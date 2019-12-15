package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparelproject.database.UserQuery;
import com.example.apparelproject.model.UserModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName,mPasswd,mEmail, mUsername;
    private Button mFotoBtn;
    private CircleImageView mFoto;
    private RadioGroup rgJenisKelamin, rgHakAkses;
    FancyButton mRegisterBtn;
    private TextView mLogin;
    Bitmap bitmapFoto;
    private String Name,Password,Email, Username, Alamat, jenisKelamin="Laki-Laki",hakAkses="Admin";
    byte[] imageByte=null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mName = (EditText)findViewById(R.id.register_nama);
        mUsername = findViewById(R.id.register_usernameText);
        mPasswd = (EditText)findViewById(R.id.register_passwordText);
        mEmail = (EditText)findViewById(R.id.register_email);
        rgJenisKelamin = findViewById(R.id.register_jeniskelamin);
        rgHakAkses = findViewById(R.id.register_hakAkses);
        mRegisterBtn = findViewById(R.id.register_button);
        mLogin = (TextView)findViewById(R.id.register_toLogin);

        mFoto = findViewById(R.id.register_viewFoto);
        mFotoBtn = findViewById(R.id.register_btnFoto);

        rgJenisKelamin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.register_laki){
                    jenisKelamin = "Laki-Laki";
                }
                else if (checkedId == R.id.register_perempuan){
                    jenisKelamin = "Perempuan";
                }
            }
        });

        rgHakAkses.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.register_admin){
                    hakAkses = "Admin";
                }
                else if (checkedId == R.id.register_customer){
                    hakAkses = "Customer";
                }
            }
        });

        mFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validUserData()){
                    SharedPreferences mSharedPreference = getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mSharedPreference.edit();

                    UserModel user = new UserModel(-1,Name,Username,Password,imageByte,jenisKelamin,Email,Alamat,hakAkses);
                    UserQuery query = new UserQuery(getApplicationContext());

                    long id =query.insertUser(user);

                    if (id>0){
                        mEditor.putString(Config.COLUMN_USER_NAMA,Name);
                        mEditor.putString(Config.COLUMN_USER_USERNAME,Username);
                        mEditor.putString(Config.COLUMN_USER_PASSWORD,Password);
                        mEditor.putString(Config.COLUMN_USER_IMAGE, Base64.encodeToString(imageByte, Base64.NO_WRAP));
                        mEditor.putString(Config.COLUMN_USER_JENISKELAMIN,jenisKelamin);
                        mEditor.putString(Config.COLUMN_USER_EMAIL,Email);
                        mEditor.putString(Config.COLUMN_USER_ALAMAT,Alamat);
                        mEditor.putString(Config.COLUMN_USER_HAKAKSES,hakAkses);
                        mEditor.putBoolean(Config.SESSION, true);
                        mEditor.apply();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(),"Register & Login Berhasil",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Register Gagal",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Data tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                mFoto.setImageBitmap(bitmapFoto);

                imageByte = DbBitmapUtility.getBytes(bitmapFoto);
                Log.e("ad",imageByte.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private boolean validUserData() {
        Username = mUsername.getText().toString().trim();
        Password = mPasswd.getText().toString().trim();
        Name = mName.getText().toString().trim();
        Email = mEmail.getText().toString().trim();
        Alamat = mEmail.getText().toString().trim();


        return !(Username.isEmpty() || Password.isEmpty() || Name.isEmpty() || Email.isEmpty() || Alamat.isEmpty() || imageByte == null);
    }

    public static Bitmap scaleDown(Bitmap realImage,
                                   boolean filter) {
//        jika image lebih besar dari 1500 x 1500 maka image di ubah menjadi 800 x800
        if (realImage.getWidth() > 500 || realImage.getHeight() > 500){
            Bitmap newbitmap = Bitmap.createScaledBitmap(realImage, 500,
                    500, filter);
            return newbitmap;
        }
        else{
            return realImage;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
