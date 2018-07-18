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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Sat1 extends AppCompatActivity {


    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;
    private String TAG = Sat1.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lvs;
    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    // URL to get contacts JSON
    private static String url = "http://192.168.43.202/R.php";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat);

        showPopupBtn = (Button) findViewById(R.id.showPopupBtn);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        contactList = new ArrayList<>();

        lvs = (ListView) findViewById(R.id.list);


        session = new SessionManager(getApplicationContext());



        new Sat1.GetContacts("http://192.168.43.202/R.php?username=" + session.getUsername()).execute();

        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) Sat1.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup,null);

                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     */
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
            pDialog = new ProgressDialog(Sat1.this);
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
                    JSONArray contacts = jsonObj.getJSONArray("time table");

                    // looping through All Contacts
                    for (int i =10; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String t1 = c.getString("9");
                        String t2 = c.getString("10");
                        String t3 = c.getString("11");
                        String t4 = c.getString("12");
                        String t5 = c.getString("1");
                        String t6 = c.getString("2");
                        String t7 = c.getString("3");
                        String t8 = c.getString("4");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("9", t1);
                        contact.put("10", t2);
                        contact.put("11", t3);
                        contact.put("12", t4);
                        contact.put("1", t5);
                        contact.put("2", t6);
                        contact.put("3", t7);
                        contact.put("4", t8);


                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Saturday" );
;

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
            ListAdapter adapter = new SimpleAdapter(Sat1.this, contactList,
                    R.layout.list_item2, new String[]{"9", "10", "11", "12", "1", "2", "3", "4"
            }, new int[]{R.id.t1, R.id.t2,
                    R.id.t3, R.id.t4, R.id.t5, R.id.t6, R.id.t7, R.id.t8});

            lvs.setAdapter(adapter);
        }

    }
}