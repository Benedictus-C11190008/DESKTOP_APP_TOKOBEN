package com.example.tokoben;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsFragment newInstance(String param1, String param2) {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    String surl, status;
    Integer transaction_id, item_amount, item_price;
    ListView lvtransactions;
    ArrayList<Integer> transactionid_list = new ArrayList<>();
    ArrayList<Integer>item_amount_list = new ArrayList<>();
    ArrayList<Integer>itemprice_list = new ArrayList<>();
    ArrayList<String>status_list = new ArrayList<>();

    class ServerConnect extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            try{
                URL url = new URL((uri[0]));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(uri[1] == "GET"){
                    Log.d("tca", "infragget");
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
            if(s != "false") {
                try {
                    Log.d("tca", "infragfalse");
                    jsarray = new JSONArray(s);
                    for (int i=0; i<jsarray.length();i++){
                        JSONObject jsobject = jsarray.getJSONObject(i);
                        transaction_id = jsobject.getInt("master_transaction_id");
                        item_price = jsobject.getInt("item_price");
                        item_amount = jsobject.getInt("item_amount");
                        status = jsobject.getString("status");

                        transactionid_list.add(transaction_id);
                        itemprice_list.add(item_price);
                        item_amount_list.add(item_amount);
                        status_list.add(status);

                    }

                    transactionsCustomAdapter customAdapter = new transactionsCustomAdapter(getActivity(), transactionid_list, itemprice_list, item_amount_list, status_list); lvtransactions.setAdapter(customAdapter);
                    lvtransactions.setAdapter(customAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Failed connect to server.\nPlease try again later!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        lvtransactions = (ListView) view.findViewById(R.id.transactionslistview);
        surl = loginpage.serverurl+"users/"+loginpage.username+"/transactions";
        new ServerConnect().execute(surl,"GET");
        Log.d("tca", surl);
        return view;
    }
}