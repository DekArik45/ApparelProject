package com.example.apparelproject.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.example.apparelproject.LoginActivity;
import com.example.apparelproject.MainActivity;
import com.example.apparelproject.NewsActivity;
import com.example.apparelproject.ProductActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.RegisterActivity;
import com.example.apparelproject.RiwayatActivity;
import com.example.apparelproject.TransaksiAdminActivity;
import com.example.apparelproject.TransaksiCustomerActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

public class DrawerMenu {

    String password, username, nama, sub_title, foto, hakAkses;
    Bitmap fotoBitmap;
    boolean session;
    SharedPreferences sharedpreferences;

    public DrawerMenu(){

    }

    public void createDrawer(Context context, AppCompatActivity activity, Toolbar mToolbar){

        sharedpreferences = context.getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(Config.SESSION,false);
        hakAkses = sharedpreferences.getString(Config.COLUMN_USER_HAKAKSES,"");
        if (session) {

            nama = sharedpreferences.getString(Config.COLUMN_USER_NAMA, null);
            password = sharedpreferences.getString(Config.COLUMN_USER_PASSWORD, null);
            username = sharedpreferences.getString(Config.COLUMN_USER_USERNAME, null);
            sub_title = hakAkses;
            byte[] fotoByte = Base64.decode(sharedpreferences.getString(Config.COLUMN_USER_IMAGE,null),Base64.DEFAULT);
            fotoBitmap = DbBitmapUtility.getImage(fotoByte);

        }
        else{
            Drawable d = context.getDrawable(R.drawable.profile_avatar);
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            nama = "Guest";
            sub_title = "Silahkan Login";
            foto = Config.URL_IMAGE+"avatar.png";
            fotoBitmap = bitmap;
        }

        // Create the AccountHeader

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header_drawermenu)
                .addProfiles(
                        new ProfileDrawerItem().withName(nama).withEmail(sub_title).withIcon(fotoBitmap)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        if (session) {
            if (hakAkses.equals("Admin")){
                buildDrawerLoggedInAdmin(context,activity,mToolbar, headerResult);
            }
            else if (hakAkses.equals("Customer")){
                buildDrawerLoggedIn(context,activity,mToolbar, headerResult);
            }
        }
        else{
            buildDrawerNotLoggedIn(context,activity,mToolbar, headerResult);
        }
    }

    private void buildDrawerLoggedInAdmin(Context context, AppCompatActivity activity, Toolbar mToolbar, AccountHeader headerResult){
        final Context contextFinal = context;
        final AppCompatActivity activityFinal = activity;
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Produk").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("News").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Transaksi User").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Riwayat Transaksi User").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("Logout").withIcon(GoogleMaterial.Icon.gmd_account_circle);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1){
                            Intent intent = new Intent(contextFinal, ProductActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 2){

                            Intent intent = new Intent(contextFinal, NewsActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 3){

                            Intent intent = new Intent(contextFinal, TransaksiAdminActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 4){

                            Intent intent = new Intent(contextFinal, RiwayatActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 5){
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.apply();

                            Intent intent = new Intent(contextFinal, MainActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        return false;
                    }
                })
                .build();

        if(mToolbar != null)
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void buildDrawerLoggedIn(Context context, AppCompatActivity activity, Toolbar mToolbar, AccountHeader headerResult){
        final Context contextFinal = context;
        final AppCompatActivity activityFinal = activity;
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Transaksi").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Riwayat").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Logout").withIcon(GoogleMaterial.Icon.gmd_account_circle);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1){
                            Intent intent = new Intent(contextFinal, TransaksiCustomerActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 2){
                            Intent intent = new Intent(contextFinal, RiwayatActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 3){
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.apply();

                            Intent intent = new Intent(contextFinal, MainActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        return false;
                    }
                })
                .build();

        if(mToolbar != null)
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void buildDrawerNotLoggedIn(Context context, AppCompatActivity activity, Toolbar mToolbar, AccountHeader headerResult){
        final Context contextFinal = context;
        final AppCompatActivity activityFinal = activity;
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Login").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Register").withIcon(GoogleMaterial.Icon.gmd_account_box);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        item1,
                        item2
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1){
                            Intent intent = new Intent(contextFinal, LoginActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("from","MainActivity");
                            intent.putExtras(extras);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 2){
                            Intent intent = new Intent(contextFinal, RegisterActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        return false;
                    }
                })
                .build();

        if(mToolbar != null)
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

}
