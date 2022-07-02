package com.example.tokoben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class itempage extends AppCompatActivity {
    Button addtocartbt, addbt, minbt;
    ImageButton backbt, deleteitembt; ;
    TextView tvitemname, tvitemprice, tvitemdesc, tvitemstock, tvitemamount;
    Integer item_amount = 1;
    String item_name, item_desc, pos, surl, getuser, toaster;
    Integer item_price, item_stock, item_id;

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.backbutton:
                    finish();
                    break;
                case R.id.addbutton:
                    minbt.setAlpha(1f);
                    minbt.setClickable(true);
                    if(item_amount>=item_stock-1){
                        item_amount++;
                        addbt.setAlpha(.5f);
                        addbt.setClickable(false);
                    }
                    else {
                        item_amount++;

                    }
                    tvitemamount.setText(""+item_amount);
                    break;
                case R.id.minbutton:
                    addbt.setAlpha(1f);
                    addbt.setClickable(true);
                    if(item_amount<=2) {
                        item_amount--;
                        minbt.setAlpha(.5f);
                        minbt.setClickable(false);
                    }
                    else{
                        item_amount--;
                    }
                    tvitemamount.setText(""+item_amount);
                    break;
                case R.id.addtocartbutton:

                        addtocartbt.setAlpha(1f);
                        addtocartbt.setClickable(true);
                        AddItemToCart();


                    break;
                case R.id.deleteitemincartbutton:
                    DeleteItemInCart();
                    break;
            }
        }


    };

    private void goToHomePage() {
        Intent i = new Intent(this, navbar.class);

        startActivity(i);
    }

    private void AddItemToCart() {
        getuser = getIntent().getStringExtra("userdata");
        Log.d("initem getuser", ""+getuser);
        surl = loginpage.serverurl+"users/cart/"+loginpage.username;
        new ServerConnect().execute(surl,"POST");
        Toast.makeText(getApplicationContext(), "Cart Updated", Toast.LENGTH_SHORT).show(); //runtoast gbs
        finish();

    }

    private void runToaster(){
        Toast.makeText(getBaseContext(), ""+toaster, Toast.LENGTH_SHORT).show();
    }

    private void DeleteItemInCart(){
        getuser = getIntent().getStringExtra("userdata");
        surl = loginpage.serverurl+"remove/cart/"+getuser+"/"+item_id;
        new ServerConnect().execute(surl,"GET");
        Toast.makeText(itempage.this, "Item Deleted From Cart", Toast.LENGTH_SHORT).show();
        finish();
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
                    conn.setConnectTimeout(5000); //set timeout to 5 seconds
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
                    data.put("item_id", item_id);
                    data.put("item_name", item_name);
                    data.put("item_price", item_price);
                    data.put("item_amount", item_amount);
                    data.put("item_stock", item_stock);

                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(data.toString());
                    out.flush();
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String input;
                    while((input = in.readLine())!=null){
                        toaster = input;
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
                return "false";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray jsarray = null;
            if(s != "false") {
                try {
                    jsarray = new JSONArray(s);
                    for (int i=0; i<jsarray.length();i++){
                        JSONObject jsobject = jsarray.getJSONObject(i);
                        item_id = jsobject.getInt("item_id");
                        item_name = jsobject.getString("item_name");
                        item_price = jsobject.getInt("item_price");
                        item_desc = jsobject.getString("item_desc");
                        item_stock = jsobject.getInt("item_stock");


                    }

                    tvitemname.setText(item_name);
                    tvitemprice.setText("Rp. "+item_price+".000");
                    tvitemdesc.setText(item_desc);
                    tvitemstock.setText("Remaining: "+item_stock);

                    if (item_stock.equals(0))
                    {
                        addtocartbt.setAlpha(.5f);
                        addtocartbt.setClickable(false);
                        addbt.setAlpha(.5f);
                        addbt.setClickable(false);
                    }

                    if (item_stock.equals(1))
                    {
                        addbt.setAlpha(.5f);
                        addbt.setClickable(false);
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itempage);
        addtocartbt = findViewById(R.id.addtocartbutton);
        addtocartbt.setOnClickListener(myClickListener);

        backbt = findViewById(R.id.backbutton);
        backbt.setOnClickListener(myClickListener);

        addbt = findViewById(R.id.addbutton);
        addbt.setOnClickListener(myClickListener);

        minbt = findViewById(R.id.minbutton);
        minbt.setOnClickListener(myClickListener);
        minbt.setAlpha(.5f);
        minbt.setClickable(false);



        deleteitembt = findViewById(R.id.deleteitemincartbutton);
        deleteitembt.setOnClickListener(myClickListener);

        tvitemname = (TextView) findViewById(R.id.text_itemname);
        tvitemprice = (TextView) findViewById(R.id.text_itemprice);
        tvitemdesc = (TextView) findViewById(R.id.text_itemdesc);
        tvitemamount = (TextView) findViewById(R.id.text_itemamount);
        tvitemstock = (TextView) findViewById(R.id.text_itemstock);

        pos = getIntent().getStringExtra("id");
        surl = loginpage.serverurl+"item/"+pos;
        new ServerConnect().execute(surl,"GET");
    }
}