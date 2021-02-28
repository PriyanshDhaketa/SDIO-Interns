package com.example.sdiointerns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.bringToFront();
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        // textView = (TextView) findViewById(R.id.textView);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        //textView.setText("Home Activity");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_camera:
                        //textView.setText("Camera Activity");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        // startActivity(new Intent(combine.this, cardview.class));
                        overridePendingTransition(0, 0);
                        return true;
                    //break;

                    case R.id.nav_viewlist:
                        //textView.setText("Profile Activity");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    //break;
                    case R.id.nav_dashboard:
                        //textView.setText("Dashboard Activity");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    //break;
                    case R.id.nav_photo:
                        //textView.setText("Dashboard Activity");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    //break;
                }
                return false;
            }
        });



    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //write your code here for what to do when item clicked
            startActivity(new Intent(getApplicationContext(), Home.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.nav_camera) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.nav_viewlist) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.nav_dashboard) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            overridePendingTransition(0, 0);
            return true;
        }else if (id == R.id.nav_photo) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            overridePendingTransition(0, 0);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    }

