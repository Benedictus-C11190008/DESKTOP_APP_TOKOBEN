package com.example.tokoben;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayAdapter<String> listAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    String surl, item_name, item_price, item_id, item_stock;
    ListView lvitem;
    ImageButton cartbt;
    ArrayList<String>item_namelist = new ArrayList<>();
    ArrayList<String>item_pricelist = new ArrayList<>();
    ArrayList<String>item_stocklist = new ArrayList<>();
    ArrayList<String>item_idlist = new ArrayList<>();

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
                    jsarray = new JSONArray(s);
                    for (int i=0; i<jsarray.length();i++){
                        JSONObject jsobject = jsarray.getJSONObject(i);
                        item_name = jsobject.getString("item_name");
                        item_price = jsobject.getString("item_price");
                        item_id = jsobject.getString("item_id");
                        item_stock = jsobject.getString("item_stock");

                        item_namelist.add(item_name);
                        item_pricelist.add(item_price);
                        item_stocklist.add(item_stock);
                        item_idlist.add(item_id);

                    }

                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), item_namelist, item_pricelist,item_stocklist, item_idlist); lvitem.setAdapter(customAdapter);
                    lvitem.setAdapter(customAdapter);

                    lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            Intent i = new Intent(getActivity(), itempage.class);
                            i.putExtra("userdata", ""+navbar.getuser);
                            i.putExtra("id", item_idlist.get(pos));
                            startActivity(i);
                        }
                    });


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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lvitem = (ListView) view.findViewById(R.id.listviewitem);
        surl = loginpage.serverurl+"item";
        new ServerConnect().execute(surl,"GET");
        Log.d("url", surl);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartbt = view.findViewById(R.id.cartbutton);
        cartbt.setOnClickListener(this);
        Log.d("test", "test");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cartbutton:
                Intent i = new Intent(getActivity(), cartpage.class);
                i.putExtra("userdata", ""+navbar.getuser);
                Log.d("getdata tocart", ""+navbar.getuser);
                startActivity(i);
                break;
        }

    }


}