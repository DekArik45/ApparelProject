package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparelproject.database.DatabaseHelper;
import com.example.apparelproject.database.UserQuery;
import com.example.apparelproject.model.UserModel;
import com.example.apparelproject.utils.Config;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername,mUserpasswd;
    private FancyButton mLogin;
    private TextView mRegister;
    private String Name,Password;
    private SharedPreferences mSharedPreferences;
    Boolean session = false;
    String cek = "false";
    String username1,password1;
    private ArrayList<UserModel> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Cek session login jika TRUE maka langsung buka MainActivity
        mSharedPreferences = getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        session = mSharedPreferences.getBoolean(Config.SESSION,false);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mUsername = (EditText)findViewById(R.id.login_usernameText);
        mUserpasswd = (EditText)findViewById(R.id.login_passwordText);
        mLogin =  findViewById(R.id.login_button);
        mRegister = (TextView)findViewById(R.id.login_toRegister);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validUserData()) {
                    UserQuery query = new UserQuery(getApplicationContext());
                    UserModel currentUser = query.Authenticate(new UserModel(mUsername.getText().toString(), mUserpasswd.getText().toString()));

                    if (currentUser != null){
                        Snackbar.make(mLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                        mEditor.putString(Config.COLUMN_USER_NAMA,currentUser.getNama());
                        mEditor.putLong(Config.COLUMN_USER_ID,currentUser.getId());
                        mEditor.putString(Config.COLUMN_USER_USERNAME,currentUser.getUsername());
                        mEditor.putString(Config.COLUMN_USER_PASSWORD,currentUser.getPassword());
                        if (currentUser.getImage() != null){
                            mEditor.putString(Config.COLUMN_USER_IMAGE, Base64.encodeToString(currentUser.getImage(), Base64.DEFAULT));
                        }

                        mEditor.putString(Config.COLUMN_USER_JENISKELAMIN,currentUser.getJenis_kelamin());
                        mEditor.putString(Config.COLUMN_USER_EMAIL,currentUser.getEmail());
                        mEditor.putString(Config.COLUMN_USER_ALAMAT,currentUser.getAlamat());
                        mEditor.putString(Config.COLUMN_USER_HAKAKSES,currentUser.getHak_akses());
                        mEditor.putBoolean(Config.SESSION, true);
                        mEditor.apply();

                        //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //User Logged in Failed
                        Snackbar.make(mLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(mLogin, "Data Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validUserData() {
        Name = mUsername.getText().toString().trim();
        Password = mUserpasswd.getText().toString().trim();
        return !(Name.isEmpty() || Password.isEmpty());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
