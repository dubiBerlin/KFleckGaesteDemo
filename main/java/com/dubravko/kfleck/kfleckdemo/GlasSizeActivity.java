package com.dubravko.kfleck.kfleckdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;

public class GlasSizeActivity extends AppCompatActivity {

    // we will save the choosen amount of liters by the user and the glas size
    private SharedPreferenceClass sharedpreferences;


    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("***********************GlasSizeActivity -> onStart()");

    }

    @Override
    protected void onResume() {
        super.onResume();



    }


    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("***********************GlasSizeActivity -> onPause()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("***********************GlasSizeActivity -> onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("***********************GlasSizeActivity -> onRestart()");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("***********************GlasSizeActivity -> onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glas_size);

        Button glas03Btn = (Button)findViewById(R.id.glas03LBtn);
        Button glas05Btn = (Button)findViewById(R.id.glas05LBtn);
        Button glas1Btn = (Button)findViewById(R.id.glas1LBtn);

        // Call for saving glassize
        sharedpreferences = new SharedPreferenceClass(getBaseContext());

        sharedpreferences.clearSharedPreference();

        glas03Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences.setGlasSize("0.3");
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });

        glas05Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences.setGlasSize("0.5");
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });

        glas1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences.setGlasSize("1");
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });



    }
}
