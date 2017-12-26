package com.dubravko.kfleck.kfleckdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestlicheZutatenActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;

    // everything for RecyclerView
    private RecyclerView recyclerView;
    private List<Zutat> list;
    private RestlicheZutatenAdapter adapter;
    private SharedPreferenceClass spc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restliche_zutaten);

        spc = new SharedPreferenceClass(this);

        mStatusDB = FirebaseDatabase.getInstance().getReference().child("Zutaten");

        // RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize(); // Every item has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Zutat>();

        getList();

        adapter = new RestlicheZutatenAdapter(this, list);

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        HashMap<Integer, Zutat> map = spc.getRestZutatHashMap();//Helper.getZutatenListFromHashMap(spc.getRestZutatHashMap(), list);

        list = Helper.getZutatenListFromHashMap(map);
        adapter = new RestlicheZutatenAdapter(this, map, list );
        recyclerView.setAdapter(adapter);
    }



    /**************
     * ACTIONBAR
     *************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_button_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_bar_next_btn:
                startActivity(new Intent(RestlicheZutatenActivity.this, BestellungUebersichtActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void getList(){
        mStatusDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                //String key = dataSnapshot.getKey();
                Zutat zutat = new Zutat();// dataSnapshot.child(key).getValue(RestlicheZutat.class);

                //String value = dataSnapshot.getValue().toString();

                //Log.i("FETCH---KeyVALUE: ",value+" "+key+" "+dataSnapshot.getChildren().toString());

                int i = 0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String value = snapshot.getValue().toString();

                    zutat.setName(value);
                    zutat.setActivityName(getString(R.string.restlicheZutatActivity));
                    zutat.setLiter("");
                    System.out.println(i+"______ChildValue: "+value);
                    i++;
                }


                list.add(zutat);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
