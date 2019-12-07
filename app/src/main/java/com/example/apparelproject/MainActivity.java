package com.example.apparelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.apparelproject.Fragment.CartFragment;
import com.example.apparelproject.Fragment.HomeFragment;
import com.example.apparelproject.Fragment.ProfileFragment;
import com.example.apparelproject.Fragment.SearchFragment;
import com.example.apparelproject.constants.Fields;
import com.example.apparelproject.utils.DrawerMenu;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigation;
    Toolbar mToolbar;

    String title = "Home";
    SharedPreferences sharedpreferences;
    Boolean session;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            initDrawerMenu();
            replaceFragment(new HomeFragment());
            initBottomBar();

            sharedpreferences = this.getSharedPreferences(Fields.PREFERENCE, Context.MODE_PRIVATE);

            session = sharedpreferences.getBoolean(Fields.SESSION_STATUS, false);

//        SharedPreferences msharedpreferences = this.getSharedPreferences(Fields.PRODUCT_PREFERENCE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = msharedpreferences.edit();
//        editor.clear();
//        editor.apply();
    }

    private void initBottomBar(){
        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);

        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        title = "Home";
                        mToolbar.setTitle(title);
                        break;

                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                        title = "Search";
                        mToolbar.setTitle(title);
                        break;

                    case R.id.navigation_cart:
                        fragment = new CartFragment();
                        title = "Cart";
                        mToolbar.setTitle(title);
                        break;

                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        title = "Profile";
                        mToolbar.setTitle(title);
                        break;
                }
                mToolbar.setTitleTextColor(Color.rgb(255,255,255));
                return replaceFragment(fragment);
            }
        });
    }

    public boolean replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        FragmentTransaction ft = manager.beginTransaction();
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            ft.replace(R.id.main_container, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(fragmentTag);
            ft.commit();
            return true;
        }
        return true;
    }

    private void initDrawerMenu(){
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbar.setTitleTextColor(getColor(R.color.primary_dark));
        this.setSupportActionBar(mToolbar);
//
        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, mToolbar);
        mToolbar.setTitleTextColor(Color.rgb(135,135,135));
    }

    //langsung keluar
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
