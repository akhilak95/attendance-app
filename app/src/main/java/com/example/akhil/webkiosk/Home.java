package com.example.akhil.webkiosk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManager session;
    SQLiteDatabase db;

    Cursor cursor;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();



    private TextView textViewResult;
    private ProgressDialog loading;

    private String TAG = Home.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "http://192.168.43.202/e.php";
    ArrayList<HashMap<String, String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        // Session class instance
        session = new SessionManager(getApplicationContext());


        TextView etTotal = (TextView) findViewById(R.id.total);
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();
        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        new Home.GetContacts("http://192.168.43.202/e.php?session=" + session.getKeySession()).execute();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        getMenuInflater().inflate(R.menu.subject_registered, menu);
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

        int id=item.getItemId();
        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(Home.this, Home.class);
                startActivity(h);
                break;
            case R.id.nav_studentinfo:
                Intent s = new Intent(Home.this, StudentInfo.class);
                startActivity(s);
                break;
            case R.id.nav_import:
                Intent i = new Intent(Home.this, Import.class);
                startActivity(i);
                break;
            case R.id.nav_gallery:
                Intent g = new Intent(Home.this, Gallery.class);
                startActivity(g);
                break;
            case R.id.nav_slideshow:
                Intent e = new Intent(Home.this, SubjectRegistered.class);
                startActivity(e);
                break;

            case R.id.nav_tools:
                session.logoutUser();
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void parseJSON(View view)
    {



        {
            Intent intent = new Intent(this,DetailedAttendance.class);

            startActivity(intent);
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        private String url;

        public GetContacts(String _url){
            this.url = _url;
            Log.e(TAG, "url: " + _url);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(this.url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);


                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("attendance");

                    // looping through All Contacts
                    for (int i = 1; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String name = c.getString("name");
                        String  email= c.getString("code");
                        String mobile = c.getString("Lecture");
                        String tut = c.getString("Lecture");
                        String total = c.getString("Total");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("name", name);
                        contact.put("code", email);
                        contact.put("Lecture",mobile);
                        contact.put("Lecture",tut);
                        contact.put("Total",total);

                        // adding contact to contact list
                        contactList.add(contact);
                    }  Log.e(TAG, "Response from url: " + jsonStr);
                    Log.e(TAG, "Response from url: " + "Total");
                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "" ,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                ;

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Home.this, contactList,
                    R.layout.list_item, new String[]{"name","code","Lecture","Lecture","Total"
            }, new int[]{ R.id.name,R.id.email,
                    R.id.mobile,R.id.tutorial, R.id.total});

            lv.setAdapter(adapter);
        }

    }
}
