package com.example.tokoben;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class registerpage extends AppCompatActivity {

    EditText etnewusername, etnewpassword, etname, ettelp, etaddress;
    Button registerbt;
    String surl, user_input, pass_input, username, password, toast1, name_input, address_input, telp_input;


    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.registerbutton_rp:
                    user_input = etnewusername.getText().toString();
                    pass_input = etnewpassword.getText().toString();
                    name_input = etname.getText().toString();
                    address_input = etaddress.getText().toString();
                    telp_input = ettelp.getText().toString();
                    surl = loginpage.serverurl+"users";
                    new ServerConnect().execute(surl,"POST");
                    break;
            }
        }
    };

    private void toaster(){
        if (toast1.equals("success")){
            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getBaseContext(), loginpage.class);
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(), "Username Is Already In Use, Try Another Username", Toast.LENGTH_SHORT).show();
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
                else if (uri[1] == "POST"){
                    conn.setRequestMethod(uri[1]);
                    conn.setRequestProperty("Content-type", "application/json");
                    conn.setDoOutput(false);
                    conn.setDoInput(true);

                    JSONObject data = new JSONObject();
                    data.put("username", user_input);
                    data.put("password", pass_input);
                    data.put("name", name_input);
                    data.put("address", address_input);
                    data.put("telp", telp_input);

                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(data.toString());
                    out.flush();
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String input;
                    while((input = in.readLine())!=null){
                        toast1 = input;
                    }

                    return "ok";
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "false";
            } catch (IOException e) {
                e.printStackTrace();
                return "false";
            } catch (JSONException e) {
                e.printStackTrace();
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
                } catch (JSONException e) {
                    toaster();
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
        setContentView(R.layout.activity_registerpage);

        registerbt = findViewById(R.id.registerbutton_rp);
        registerbt.setOnClickListener(myClickListener);

        etnewusername = findViewById(R.id.insnewusername);
        etnewpassword = findViewById(R.id.insnewpassword);
        etname = findViewById(R.id.insname);
        etaddress = findViewById(R.id.insaddress);
        ettelp = findViewById(R.id.instelp);
    }
}