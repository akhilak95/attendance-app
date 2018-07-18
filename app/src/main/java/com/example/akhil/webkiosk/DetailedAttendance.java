package com.example.akhil.webkiosk;

/**
 * Created by akhil on 1/3/18.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import android.database.sqlite.SQLiteDatabase;

public class DetailedAttendance extends AppCompatActivity {
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    SQLiteDatabase sqLiteDatabase;

    // Session Manager Class
    SessionManager session;

    private String TAG = DetailedAttendance.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lvs;

    // URL to get contacts JSON
    private static String url = "http://192.168.43.202/new3.php";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_attendance);

        contactList = new ArrayList<>();

        lvs = (ListView) findViewById(R.id.list1);

        new GetContacts().execute();

        SQLiteDataBaseBuild();

        SQLiteTableBuild();

        DeletePreviousData();




    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DetailedAttendance.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("DetailAttendance");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String name = c.getString("date");
                        String  email= c.getString("sr");
                        String mobile = c.getString("classType");
                        String tut = c.getString("faculty");

                        Log.e(TAG, "Response from url: " + contacts.length());
                        String SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (subjectName,subjectFullForm) VALUES('"+email+"', '"+contacts.length()+"');";

                        sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("date", name);
                        contact.put("sr", email);
                        contact.put("classType",mobile);
                        contact.put("faculty",tut);


                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
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
                    DetailedAttendance.this, contactList,
                    R.layout.list_item1, new String[]{"date","sr","classType","faculty"
            }, new int[]{ R.id.name,R.id.email,
                    R.id.mobile,R.id.tutorial});

            lvs.setAdapter(adapter);
        }

    }
    public void SQLiteDataBaseBuild(){

        sqLiteDatabase = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild(){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+"("+SQLiteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+SQLiteHelper.Table_Column_1_Subject_Name+" VARCHAR, "+SQLiteHelper.Table_Column_2_SubjectFullForm+" VARCHAR);");

    }

    public void DeletePreviousData(){

        sqLiteDatabase.execSQL("DELETE FROM "+SQLiteHelper.TABLE_NAME+"");

    }}
