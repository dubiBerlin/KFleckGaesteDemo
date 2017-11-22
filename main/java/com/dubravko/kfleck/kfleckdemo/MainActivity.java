package com.dubravko.kfleck.kfleckdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dubravko.knutschfleck.knutschfleckdemo.R;

public class MainActivity extends AppCompatActivity {

    private String kindOfCocktail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The two buttons
        Button withAlkBtn = (Button)findViewById(R.id.setAlcBtn);
        Button withoutAlkBtn = (Button)findViewById(R.id.setNoAlcBtn);

        withAlkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // We access the String out of res/values/strings.xml
                kindOfCocktail = getBaseContext().getString(R.string.alcoholic_cocktail);

                // We give the value to the new Intent
                sendToNExtActivity(kindOfCocktail);
            }
        });

        withoutAlkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // We access the String out of res/values/strings.xml
                kindOfCocktail = getBaseContext().getString(R.string.non_alcoholic_cocktail);

                // We give the value to the new Intent
                sendToNExtActivity(kindOfCocktail);
            }
        });

    }

    private void sendToNExtActivity(String value){
        Intent intent = new Intent(getApplicationContext(), GlasSizeActivity.class);
        // R.string.key_main_activity returns "Key"
        intent.putExtra(getBaseContext().getString(R.string.key_main_activity),value);

        startActivity(intent);
    }
}
