package com.perzhan.earnandburn;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.perzhan.earnandburn.Adapter.EarnGridViewAdapter;
import com.perzhan.earnandburn.Model.Base;
import com.perzhan.earnandburn.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView headerText;
    private ViewGroup earnBtn;
    private ViewGroup burnBtn;

    private ImageView earnView;
    private ImageView burnView;

    private GridView earnGridView;
    private GridView burnGridView;

    private RoundCornerProgressBar earnProgress;
    private RoundCornerProgressBar burnProgress;

    private int maxEarn = 20;
    private int maxBurn = 20;

    private int total = 0;

    private boolean earnToggle;
    private boolean burnToggle;

    private List<Base> earnList;

    private EarnGridViewAdapter earnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    private void init(){
        headerText = (TextView) findViewById(R.id.topPanelHeader);
        earnBtn = (ViewGroup) findViewById(R.id.earnPanel);
        burnBtn = (ViewGroup) findViewById(R.id.burnPanel);

        earnProgress = (RoundCornerProgressBar) findViewById(R.id.earnProgressBar);
        burnProgress = (RoundCornerProgressBar) findViewById(R.id.burnProgressBar);

        earnView = (ImageView) findViewById(R.id.earnView);
        burnView = (ImageView) findViewById(R.id.burnView);

        earnGridView = (GridView) findViewById(R.id.earnGridView);
        burnGridView = (GridView) findViewById(R.id.burnGridView);

        earnList = new ArrayList<>();
        earnAdapter = new EarnGridViewAdapter(this, earnList);
        earnGridView.setAdapter(earnAdapter);

        earnToggle = burnToggle = false;

        earnProgress.setMax(maxEarn);
        burnProgress.setMax(maxBurn);
        earnProgress.setProgress(0);
        burnProgress.setProgress(0);

        putFakeData();

        addListeners();
    }

    private void putFakeData(){
        for(int i = 0; i < 4; i ++){
            Base b = new Base();
            b.setId(Util.generateUUID());
            b.setName("Base " + i);

            earnList.add(b);
        }

        earnAdapter.notifyDataSetChanged();
    }

    private void addListeners(){
        earnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                headerText.setText(addSign(total));
                setProgressBar();

                earnToggle = !earnToggle;
                displayEarnItems(earnToggle);
            }
        });

        burnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total--;
                headerText.setText(addSign(total));
                setProgressBar();

                burnToggle = !burnToggle;
                displayBurnItems(burnToggle);
            }
        });
    }

    private String addSign(int value){
        if(value > 0){
            return "+"+value;
        }else{
            return ""+value;
        }
    }

    private void displayEarnItems(boolean value){ Toast.makeText(getApplicationContext(), "earn :"+value, Toast.LENGTH_SHORT).show();
        if(value) { //display grid view
            earnGridView.setVisibility(View.VISIBLE);
            earnView.setVisibility(View.GONE);
        }else{ //hide grid view
            earnGridView.setVisibility(View.GONE);
            earnView.setVisibility(View.VISIBLE);
        }
    }

    private void displayBurnItems(boolean value){ Toast.makeText(getApplicationContext(), "burn :"+value, Toast.LENGTH_SHORT).show();
        if(value){
            burnGridView.setVisibility(View.VISIBLE);
            burnView.setVisibility(View.GONE);
        }else{
            burnGridView.setVisibility(View.GONE);
            burnView.setVisibility(View.VISIBLE);
        }
    }

    private void setProgressBar(){
        if(total > 0){
            earnProgress.setProgress(total);
            burnProgress.setProgress(0);
            headerText.setTextColor(ContextCompat.getColor(this, R.color.red));
        }else if(total == 0){
            earnProgress.setProgress(0);
            burnProgress.setProgress(0);
            headerText.setTextColor(ContextCompat.getColor(this, R.color.gray));
        }else{
            earnProgress.setProgress(0);
            burnProgress.setProgress(Math.abs(total));
            headerText.setTextColor(ContextCompat.getColor(this, R.color.blue));
        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
