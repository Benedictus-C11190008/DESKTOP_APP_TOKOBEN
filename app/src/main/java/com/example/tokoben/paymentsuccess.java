package com.example.tokoben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class paymentsuccess extends AppCompatActivity {
    Button returntohpbt;

    private View.OnClickListener myClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.returntohpbutton:
                    finish();
                    Intent i = new Intent(getApplicationContext(), navbar.class);
                    startActivity(i);
                    onBackPressed();

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentsuccess);
        returntohpbt = findViewById(R.id.returntohpbutton);
        returntohpbt.setOnClickListener(myClickListener);
    }
}