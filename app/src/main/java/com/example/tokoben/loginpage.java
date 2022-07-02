package com.example.tokoben;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class loginpage extends AppCompatActivity {
    ///////////////////GANTI IP TIAP PINDAH////////////////////////////
    public static String serverurl = "http://192.168.1.126:8000/";
    //public static String serverurl = "http://172.22.5.182:8000/";
    ///////////////////////////////////////////////////////////////////

    public static String username;
    EditText etusername, etpassword;
    Button loginbt, registerbt;

    String surl, user_input, pass_input, password, profilename;
    public static Integer balance;

    private View.OnClickListener myClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.loginbutton:
                    user_input = etusername.getText().toString();
                    pass_input = etpassword.getText().toString();
                    surl = serverurl+"users/"+user_input;
                    new ServerConnect().execute(surl,"GET");
                    break;
                case R.id.registerbutton:
                    goToRegisterPage();
                    break;
            }
        }
    };
    private void goToHomePage(){
        Intent i = new Intent(getApplicationContext(), navbar.class);
        i.putExtra("userdata", ""+username);
        Log.d("url senduserdata", ""+username);
        startActivity(i);
    }

    private void goToRegisterPage(){
        Intent i = new Intent(this, registerpage.class);
        startActivity(i);
    }

    private void checkpass(){
        if((user_input.equals(username) && pass_input.equals(password))){
            goToHomePage();
            Toast.makeText(loginpage.this, "Logged In, Welcome "+profilename, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(loginpage.this, "Incorrect Username Or Password ", Toast.LENGTH_SHORT).show();
        }
    }

    class ServerConnect extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            try {
                URL url = new URL((uri[0]));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if(uri[1] == "GET"){
                    conn.setRequestMethod(uri[1]);
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setConnectTimeout(5000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String input;
                    StringBuffer response = new StringBuffer();
                    while ((input = in.readLine())!=null) {
                        response.append(input);
                    }
                    in.close();
                    return String.valueOf(response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "false";
            } catch (IOException e) {
                e.printStackTrace();
                return "false";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray jsarray = null;
            if (s != "false"){
                try {
                    jsarray = new JSONArray(s);
                    JSONObject jsobject = jsarray.getJSONObject(0);
                    username = jsobject.getString("username");
                    password = jsobject.getString("password");
                    profilename = jsobject.getString("name");
                    balance = jsobject.getInt("balance");
                    checkpass();
                } catch (JSONException e) {
                    checkpass();
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getBaseContext(), "Failed connect to server.\nPlease try again later!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbt = findViewById(R.id.loginbutton);
        loginbt.setOnClickListener(myClickListener);
        registerbt = findViewById(R.id.registerbutton);
        registerbt.setOnClickListener(myClickListener);

        etusername = findViewById(R.id.insusername);
        etpassword = findViewById(R.id.inspassword);
    }
}