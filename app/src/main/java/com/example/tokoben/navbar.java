package com.example.tokoben;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.tokoben.databinding.ActivityNavbarBinding;

import java.util.zip.Inflater;

public class navbar extends AppCompatActivity {

    ActivityNavbarBinding binding;
    public static String getuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ProfileFragment());

        getuser = getIntent().getStringExtra("userdata");
        Log.d("getdata", ""+getuser);
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

            switch (item.getItemId()){
                case R.id.profilenav:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.homenav:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.transactionsnav:
                    replaceFragment(new TransactionsFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}