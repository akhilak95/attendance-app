package com.example.akhil.webkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Gallery extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManager session;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    Spinner spinner;
    String[] SPINNERVALUES = {"First Year","Second Year","Third Year","Fourth Year"};
    String SpinnerValue;
    Button GOTO;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();

        //We dont need this.
        spinner =(Spinner)findViewById(R.id.spinner1);

        GOTO = (Button)findViewById(R.id.button1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Gallery.this, android.R.layout.simple_list_item_1, SPINNERVALUES);

        spinner.setAdapter(adapter);

        //Adding setOnItemSelectedListener method on spinner.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SpinnerValue = (String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        GOTO.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch(SpinnerValue){

                    case "First Year":
                        intent = new Intent(Gallery.this, FirstYear1.class);
                        startActivity(intent);
                        break;

                    case "Second Year":
                        intent = new Intent(Gallery.this, SecondYear1.class);
                        startActivity(intent);
                        break;

                    case "Third Year":
                        intent = new Intent(Gallery.this, ThirdYear1.class);
                        startActivity(intent);
                        break;
                    case "Fourth Year":
                        intent = new Intent(Gallery.this, FourthYear1.class);
                        startActivity(intent);
                        break;


                }
            }
        });



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home, menu);
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
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent h= new Intent(Gallery.this,Home.class);
                startActivity(h);
                break;
            case R.id.nav_studentinfo:
                Intent s= new Intent(Gallery.this,StudentInfo.class);
                startActivity(s);
                break;
            case R.id.nav_import:
                Intent i= new Intent(Gallery.this,Import.class);
                startActivity(i);
                break;
            case R.id.nav_gallery:
                Intent g= new Intent(Gallery.this,Gallery.class);
                startActivity(g);
                break;

            case R.id.nav_slideshow:
                Intent e= new Intent(Gallery.this,SubjectRegistered.class);
                startActivity(e);
                break;
            case R.id.nav_tools:
                session.logoutUser();
                finish();
                break;

            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
            //copy this now.
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
