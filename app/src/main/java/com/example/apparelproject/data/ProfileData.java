package com.example.apparelproject.data;

import com.example.apparelproject.model.UserModel;

import java.util.ArrayList;

public class ProfileData {

    public static String[][] data = new String[][]{
            {"Adji Nur", "adji@gmail.com", "adji", "adji123", "http://firstbot18.000webhostapp.com/foto_apparel/foto_profile.JPG"},
            {"I Kadek Ari Melinia Antara", "ari@gmail.com", "ari", "ari123", "http://firstbot18.000webhostapp.com/foto_apparel/ari.jpg"},
            {"Gusade Taruna", "gusade@gmail.com", "gusade", "gusade123", "http://firstbot18.000webhostapp.com/foto_apparel/84068.jpg"}
    };

    public static ArrayList<UserModel> getListData(){
        UserModel userData = null;
        ArrayList<UserModel> list = new ArrayList<>();
        for (String[] aData : data) {
            userData = new UserModel();
            userData.setNamaUser(aData[0]);
            userData.setEmailUser(aData[1]);
            userData.setUsername(aData[2]);
            userData.setPassword(aData[3]);
            userData.setFotoUser(aData[4]);

            list.add(userData);
        }

        return list;
    }
}
