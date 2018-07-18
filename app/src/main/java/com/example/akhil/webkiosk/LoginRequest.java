package com.example.akhil.webkiosk;

/**
 * Created by akhil on 1/3/18.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://192.168.43.202/w.php";


    private Map<String, String> params;

    public LoginRequest(String username, String password ,Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,  listener, null);



        params = new HashMap<>();

        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

}