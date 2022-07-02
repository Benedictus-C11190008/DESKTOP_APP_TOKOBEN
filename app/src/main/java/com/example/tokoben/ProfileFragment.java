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
import android.widget.Button;
import android.widget.TextView;
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
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    String username, password, surl;
    public static String name, address, telp;
    Integer balance;
    TextView tvprofilename, tvprofileaddress, tvprofiletelp, tvprofilebalance;
    Button logoutbt;




    class ServerConnect extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            try{
                URL url = new URL((uri[0]));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //GET Method
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
                    JSONObject jsobject = jsarray.getJSONObject(0);
                    username = jsobject.getString("username");
                    password = jsobject.getString("password");
                    name = jsobject.getString("name");
                    address = jsobject.getString("address");
                    telp = jsobject.getString("telp");
                    balance = jsobject.getInt("balance");

                    tvprofilename.setText(""+name);
                    tvprofileaddress.setText(""+address);
                    tvprofiletelp.setText(""+telp);
                    tvprofilebalance.setText("Rp. "+balance+".000");

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
        surl = loginpage.serverurl+"users/"+loginpage.username;
        new ServerConnect().execute(surl,"GET");

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvprofilename = (TextView) view.findViewById(R.id.text_profilename);
        tvprofileaddress = (TextView) view.findViewById(R.id.text_profileaddress);
        tvprofiletelp = (TextView) view.findViewById(R.id.text_profiletelp);
        tvprofilebalance = (TextView) view.findViewById(R.id.text_profilebalance);
        logoutbt = (Button) view.findViewById(R.id.logoutbutton);
        logoutbt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.logoutbutton:
                Toast.makeText(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Intent i = new Intent(getActivity(), loginpage.class);
                startActivity(i);
                getActivity().onBackPressed();
        }
    }


}