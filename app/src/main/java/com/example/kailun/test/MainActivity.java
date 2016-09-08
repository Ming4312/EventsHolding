package com.example.kailun.test;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.Firebase;

import junit.framework.Test;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int REGISTER_REQUEST_CODE = 1;
    public final  static String APP_STATUS="App Status";
    public static MainActivity _this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _this = this;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferences = getSharedPreferences(APP_STATUS,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("firstTime");
        editor.commit();

        firstRun();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        if (id == R.id.nav_events) {
            // Handle the camera action
            fragment = new Events();
        } else if (id == R.id.nav_createEvent) {
            fragment = new CreateEvent();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_login) {
            fragment = new Login();
        } else if (id == R.id.nav_logout){

            Firebase myFirebaseRef = new Firebase("https://radiant-inferno-4091.firebaseio.com");
            myFirebaseRef.unauth();
        }
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //When signed up
        if(requestCode == REGISTER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                setFirstTime();
                goToLogin();

            }
        }

    }
    public void firstRun(){
        SharedPreferences preferences = getSharedPreferences(APP_STATUS,MODE_PRIVATE);
        Boolean isFirstTime = preferences.getBoolean("firstTime",true);
        if(isFirstTime){
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivityForResult(intent,REGISTER_REQUEST_CODE);
        }else{
            setNavHeaderText();
        }
    }
    public void setFirstTime(){
        //set boolean that the register form will not show on next time
        SharedPreferences preferences = getSharedPreferences(APP_STATUS,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstTime",false);
        editor.commit();
    }
    public void goToLogin(){
        //after sign up, go to login page
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new Login();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    public void setNavHeaderText(){
        //if logged in, change the header text to user email
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView email = (TextView)header.findViewById(R.id.tv_logged_in_email);
        email.setText(SaveSharedPreference.getUserName(MainActivity.this));
    }
}
