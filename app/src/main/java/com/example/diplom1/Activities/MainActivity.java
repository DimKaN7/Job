package com.example.diplom1.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.diplom1.Fragments.OrdersFragment;
import com.example.diplom1.Fragments.TileFragment;
import com.example.diplom1.R;
import com.example.diplom1.Fragments.SeamlessFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public static boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View v = getCurrentFocus();
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } catch (Exception e) { }
                switch (item.getItemId()) {
                    case R.id.nav_seamless:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                new SeamlessFragment()).commit();
                        break;
                    case R.id.nav_tile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                new TileFragment()).commit();
                        break;
                    case R.id.nav_orders:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                new OrdersFragment()).commit();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Bundle bundle = getIntent().getBundleExtra("translation");
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new SeamlessFragment()).commit();
        }
        else {
            if (bundle.getString("type").equals("seamless")) {
                Fragment seamlessFragment = new SeamlessFragment();
                seamlessFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        seamlessFragment).commit();
                navigationView.setCheckedItem(R.id.nav_seamless);
            } else if (bundle.getString("type").equals("tile")){
                Fragment tileFragment = new TileFragment();
                tileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        tileFragment).commit();
                navigationView.setCheckedItem(R.id.nav_tile);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}
