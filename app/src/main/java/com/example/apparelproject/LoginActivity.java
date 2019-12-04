package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparelproject.constants.Fields;
import com.example.apparelproject.data.ProfileData;
import com.example.apparelproject.model.UserModel;

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
        mSharedPreferences = getSharedPreferences(Fields.PREFERENCE, Context.MODE_PRIVATE);
        session = mSharedPreferences.getBoolean(Fields.SESSION_STATUS,false);
        password1 = mSharedPreferences.getString(Fields.PASSWORD, null);
        username1 = mSharedPreferences.getString(Fields.USERNAME, null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(Fields.PASSWORD, password1);
            intent.putExtra(Fields.USERNAME, username1);
            startActivity(intent);
            finish();
        }

        mUsername = (EditText)findViewById(R.id.login_usernameText);
        mUserpasswd = (EditText)findViewById(R.id.login_passwordText);
        mLogin =  findViewById(R.id.login_button);
        mRegister = (TextView)findViewById(R.id.login_toRegister);

        this.setListUser(ProfileData.getListData());



        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validUserData()){
                    for (int i=0; i<getListUser().size(); i++){

                        if (getListUser().get(i).getUsername().equals(mUsername.getText().toString()) && getListUser().get(i).getPassword().equals(mUserpasswd.getText().toString())){
                            cek = "true";
                            Log.e("adf = ",getListUser().get(i).getUsername()+" "+mUsername.getText().toString());

                            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                            mEditor.putString(Fields.NAME,getListUser().get(i).getNamaUser());
                            mEditor.putString(Fields.PASSWORD,mUserpasswd.getText().toString());
                            mEditor.putString(Fields.USERNAME,mUsername.getText().toString());
                            mEditor.putString(Fields.EMAIL,getListUser().get(i).getEmailUser());
                            mEditor.putString(Fields.FOTO,getListUser().get(i).getFotoUser());
                            mEditor.putBoolean(Fields.SESSION_STATUS, true);
                            mEditor.apply();

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    if(username1 == mUsername.getText().toString() && password1 == mUserpasswd.getText().toString()){
                        cek = "true";
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (cek.equals("false")){
                        Toast.makeText(getApplicationContext(),"Username atau Password Salah",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Data tidak boleh kosong",Toast.LENGTH_SHORT).show();
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

    public ArrayList<UserModel> getListUser() {
        return listUser;
    }

    public void setListUser(ArrayList<UserModel> listUser) {
        this.listUser = listUser;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
