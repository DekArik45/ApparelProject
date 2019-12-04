package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparelproject.constants.Fields;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName,mPasswd,mEmail, mUsername;
    private Button mFotoBtn;
    private CircleImageView mFoto;
    FancyButton mRegisterBtn;
    private TextView mLogin;
    Bitmap bitmapFoto;
    private String Name,Password,Email, Username;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = (EditText)findViewById(R.id.register_nama);
        mUsername = findViewById(R.id.register_usernameText);
        mPasswd = (EditText)findViewById(R.id.register_passwordText);
        mEmail = (EditText)findViewById(R.id.register_emailText);
        mRegisterBtn = findViewById(R.id.register_button);
        mLogin = (TextView)findViewById(R.id.register_toLogin);

        mFoto = findViewById(R.id.register_viewFoto);
        mFotoBtn = findViewById(R.id.register_btnFoto);

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
                    SharedPreferences mSharedPreference = getSharedPreferences(Fields.PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mSharedPreference.edit();
                    mEditor.putBoolean(Fields.SESSION_STATUS, true);
                    mEditor.putString(Fields.NAME,Name);
                    mEditor.putString(Fields.PASSWORD,Password);
                    mEditor.putString(Fields.EMAIL,Email);
                    mEditor.putString(Fields.USERNAME,Username);
                    mEditor.putString(Fields.FOTO,"http://firstbot18.000webhostapp.com/foto_apparel/images.jpeg");
                    mEditor.apply();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
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

                //displaying selected image to imageview
                mFoto.setImageBitmap(bitmapFoto);

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

        return !(Username.isEmpty() || Password.isEmpty() || Name.isEmpty() || Email.isEmpty());
    }
}
