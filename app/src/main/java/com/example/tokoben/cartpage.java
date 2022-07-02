package com.example.tokoben;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.ArrayList;

public class cartpage extends AppCompatActivity {

    ImageButton backbt_cp, deletebt;
    Button paybt;
    TextView tvtotalitemprice, tvtotalprice, tvshipping;
    String item_name, ip, item_id, surl, getuser;
    Integer item_price, item_amount, price_sum, total_price, totalpriceperitem, newbalance, t_id, amount_sum, item_stock, stock_left;
    ListView lvcart;
    ArrayList<String> cartlist = new ArrayList<>();
    ArrayList<String> item_namelist_cart = new ArrayList<>();
    ArrayList<Integer> item_pricelist_cart = new ArrayList<>();
    ArrayList<Integer> item_amountlist_cart = new ArrayList<>();
    ArrayList<String> item_idlist_cart = new ArrayList<>();
    ArrayList<Integer> totalpricelist = new ArrayList<Integer>();
    ArrayList<Integer> stockleftlist = new ArrayList<Integer>();
    ArrayAdapter<String> listAdapter;

    int x, y;



    private View.OnClickListener myClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.backbutton_cart:
                    finish();
                    break;
                case R.id.paybutton:
                    if(loginpage.balance < total_price){
                        Toast.makeText(cartpage.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(cartpage.this, "Processing...", Toast.LENGTH_SHORT).show();
                        newbalance = loginpage.balance - total_price;
                        surl = loginpage.serverurl+"users/"+loginpage.username+"/update";
                        new ServerConnect().execute(surl,"PUT");


                        for(x = 0; x < item_idlist_cart.size();x++){
                            surl = loginpage.serverurl+"item/stock/update";
                            new ServerConnect().execute(surl,"UPDATE");
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                        Toast.makeText(cartpage.this, "Payment Success", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent i = new Intent(getApplicationContext(), paymentsuccess.class);
                        startActivity(i);
                    }
                    break;
                case R.id.deletebutton:
                    surl = loginpage.serverurl+"remove/cart/"+getuser;
                    new ServerConnect().execute(surl,"GET");
                    finish();
                    startActivity(getIntent());
                    break;
            }
        }
    };
    class ServerConnect extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            try{
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
                else if (uri[1] == "PUT"){
                    conn.setRequestMethod(uri[1]);
                    conn.setRequestProperty("Content-type", "application/json");
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    JSONObject data = new JSONObject();
                    data.put("username", loginpage.username);
                    data.put("balance", newbalance);
                    data.put("name", ProfileFragment.name);
                    data.put("address", ProfileFragment.address);
                    data.put("telp", ProfileFragment.telp);
                    data.put("item_amount", amount_sum);
                    data.put("item_price", total_price);
                    data.put("status", "Paid");
                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(data.toString());
                    out.flush();
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String input;
                    while((input = in.readLine())!=null){
                    }
                    return "ok";
                }
                else if (uri[1] == "UPDATE"){
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-type", "application/json");
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    JSONObject data = new JSONObject();
                    data.put("item_id", item_idlist_cart.get(x));
                    data.put("item_stock", stockleftlist.get(x));
                    Log.d("loop id", "" + item_idlist_cart.get(x));
                    Log.d("loop stock", "" + stockleftlist.get(x));
                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(data.toString());
                    out.flush();
                    out.close();
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String input;
                    while ((input = in.readLine()) != null) {
                    }

                    return "ok";

                }
                else if (uri[1] == "PUT_TO_ITEM"){
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-type", "application/json");
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    JSONObject data = new JSONObject();
                    data.put("transaction_id", "1");
                    data.put("item_name", item_namelist_cart.get(y));
                    data.put("item_price", item_pricelist_cart.get(y));
                    data.put("item_amount", item_amountlist_cart.get(y));
                    data.put("status", "Paid");
                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    out.write(data.toString());
                    out.flush();
                    out.close();
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String input;
                    while ((input = in.readLine()) != null) {
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
            if(s != "false") {
                try {
                    jsarray = new JSONArray(s);
                    for (int i=0; i<jsarray.length();i++){
                        JSONObject jsobject = jsarray.getJSONObject(i);
                        item_name = jsobject.getString("item_name");
                        item_price = jsobject.getInt("item_price");
                        item_amount = jsobject.getInt("item_amount");
                        item_id = jsobject.getString("item_id");
                        item_stock = jsobject.getInt("item_stock");

                        item_namelist_cart.add(item_name);
                        item_pricelist_cart.add(item_price);
                        item_amountlist_cart.add(item_amount);
                        item_idlist_cart.add(item_id);

                        stock_left = item_stock - item_amount;
                        stockleftlist.add(stock_left);

                        totalpriceperitem = item_price * item_amount;
                        cartlist.add(item_name +'\n'+ "Rp. " + item_price+".000" + " x "+item_amount+'\n'+'\n'+"Total: Rp. "+totalpriceperitem+".000");
                        totalpricelist.add(totalpriceperitem);
                    }

                    Log.d("data incart", "yes");
                    cartCustomAdapter customAdapter = new cartCustomAdapter(getBaseContext(), item_namelist_cart, item_pricelist_cart, item_amountlist_cart, item_idlist_cart, totalpricelist);
                    lvcart.setAdapter(customAdapter);

                    lvcart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            finish();
                            Intent go = new Intent(getBaseContext(), itempage.class);
                            go.putExtra("userdata", ""+navbar.getuser);
                            go.putExtra("id", item_idlist_cart.get(i));
                            startActivity(go);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                price_sum = 0;
                total_price = 0;
                amount_sum = 0;
                for(int a = 0; a < totalpricelist.size();a++)
                {
                    price_sum+=totalpricelist.get(a);
                }
                if (price_sum !=0){
                    paybt.setAlpha(1f);
                    paybt.setClickable(true);
                    tvshipping.setText("Rp. 19.000");
                    tvtotalitemprice.setText("Rp. "+price_sum+".000");
                    total_price = price_sum + 19;
                    tvtotalprice.setText("Rp. "+total_price+".000");
                }
                else{
                    tvtotalitemprice.setText("Rp. 0");
                    tvtotalprice.setText("Rp. 0");
                    tvshipping.setText("Rp. 0");
                }
                for(int a = 0; a < item_amountlist_cart.size();a++)
                {
                    amount_sum+=item_amountlist_cart.get(a);
                }
            } else {
                Toast.makeText(getBaseContext(), "Failed connect to server.\nPlease try again later!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartpage);

        paybt = findViewById(R.id.paybutton);
        backbt_cp = findViewById(R.id.backbutton_cart);

        paybt.setOnClickListener(myClickListener);
        backbt_cp.setOnClickListener(myClickListener);

        paybt.setAlpha(0.5f);
        paybt.setClickable(false);

        tvtotalitemprice = (TextView) findViewById(R.id.texttotalitemprice);
        tvtotalprice = (TextView) findViewById(R.id.texttotalprice);
        tvshipping = (TextView) findViewById(R.id.textshipping);

        deletebt = findViewById(R.id.deletebutton);
        deletebt.setOnClickListener(myClickListener);
        lvcart = (ListView) findViewById(R.id.cart_listview);
            getuser = getIntent().getStringExtra("userdata");
            surl = loginpage.serverurl+"cart/"+loginpage.username;
            new ServerConnect().execute(surl,"GET");


    }
}