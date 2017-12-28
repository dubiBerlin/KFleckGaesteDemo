package com.dubravko.kfleck.kfleckdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.kfleck.kfleckdemo.shared.Helper;
import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AlkZutatenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;

    // everything for RecyclerView
    private RecyclerView recyclerView;
    //private List<Zutat> zutaten;
    private HashMap<Integer, Zutat> map;
    private SharedPreferenceClass spc;
    private AlkZutatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alk_zutaten);

        mStatusDB = FirebaseDatabase.getInstance().getReference().child("Alkohol");

        // RecyclerView settings
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.alkListeRecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        spc = new SharedPreferenceClass(this);


        if(spc.getAlcoholZutatHashMap()!=null && spc.getAlcoholZutatHashMap().size()>0){
            map = spc.getAlcoholZutatHashMap();
        }else{
            map = new HashMap<Integer, Zutat>();
            spc.setAlcoholZutatenList(Helper.convertObjectToString(map));
            // Call data from Firebase database
            fillMapFromFirebase();
        }
        //zutaten = new ArrayList<Zutat>();
        //getList();
        //getList2();

        // Daten von Firebase holen

        Log.d("AlkZutatenActivity","\n\n________________AlkZutatenActivity_onCreate______________\n\n");


        //zutaten = getStatusesList();
        adapter = new AlkZutatAdapter(map ,getSupportActionBar(), this);
        recyclerView.setItemViewCacheSize(map.size());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("***********************AlkZutatenActivity -> onRestart()");

        HashMap<Integer, Zutat> map = spc.getAlcoholZutatHashMap();

        double currentLiterChoosen = Double.valueOf(spc.getCurrentAmountChoosenLiters());

        getSupportActionBar().setTitle(currentLiterChoosen+" | "+spc.getGlasSize());


        adapter = new AlkZutatAdapter(map ,getSupportActionBar(), this);
        recyclerView.setItemViewCacheSize(map.size());
        recyclerView.setAdapter(adapter);
    }


/*    private void getList2(){
        for(int i = 1; i < 6; i++){
            Zutat zutat = new Zutat("Alkohol "+i, "0.0"+i);
            zutat.setActivityName(getString(R.string.alkZutatActivity));
            zutaten.add(zutat);

        }
    }*/

    // ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_button_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_bar_next_btn:
                startActivity(new Intent(AlkZutatenActivity.this, NoAlkZutatenActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    int position = 0;
    private void fillMapFromFirebase(){
        mStatusDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.i("count ",position+" ");
                //String key = dataSnapshot.getKey();
                Zutat zutat = new Zutat();// dataSnapshot.child(key).getValue(RestlicheZutat.class);
                zutat.setPosition(position);
                //String value = dataSnapshot.getValue().toString();

                //Log.i("FETCH---KeyVALUE: ",value+" "+key+" "+dataSnapshot.getChildren().toString());

                int i = 0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String value = snapshot.getValue().toString();

                    if(i==0){
                        zutat.setLiter(value);
                    }else{
                        zutat.setName(value);
                    }

                    System.out.println(i+"NoAlkActivity => fillMapFromFirebase ______ChildValue: "+value);
                    i++;
                }

                zutat.setActivityName(getString(R.string.alkZutatActivity));
                zutat.setStatus(-1);
                zutat.setPosition(position);

                map.put(position, zutat);

                spc.updateAlcoholZutatenList(Helper.convertObjectToString(map));


                position++;
                //list.add(zutat);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
