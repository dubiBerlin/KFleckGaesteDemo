package com.dubravko.knutschfleck.knutschfleckdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dubravko.knutschfleck.knutschfleckdemo.shared.SharedPreferenceClass;

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

        // Call for saving glassize
        final SharedPreferenceClass spc = new SharedPreferenceClass(getBaseContext());

        glas03Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spc.setGlasSize("0.3");
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });

        glas05Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spc.setGlasSize("0.5");
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });

        glas1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spc.setGlasSize("1");
                startActivity(new Intent(GlasSizeActivity.this, AlkZutatenActivity.class));
            }
        });



    }
}
