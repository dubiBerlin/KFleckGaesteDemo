package com.dubravko.knutschfleck.knutschfleckdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GlasSizeActivity extends AppCompatActivity {

    // we will save the choosen amount of liters by the user and the glas size
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glas_size);

        Button glas03Btn = (Button)findViewById(R.id.glas03LBtn);
        Button glas05Btn = (Button)findViewById(R.id.glas05LBtn);
        Button glas1Btn = (Button)findViewById(R.id.glas1LBtn);


        glas03Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });



    }
}
