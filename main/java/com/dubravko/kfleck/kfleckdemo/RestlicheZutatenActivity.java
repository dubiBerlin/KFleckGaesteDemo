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

public class RestlicheZutatenActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;

    // everything for RecyclerView
    private RecyclerView recyclerView;
    //private List<Zutat> list;
    private HashMap<Integer, Zutat> map;
    private RestlicheZutatenAdapter adapter;
    private SharedPreferenceClass spc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restliche_zutaten);

        getSupportActionBar().setTitle("Weitere Zutaten");

        mStatusDB = FirebaseDatabase.getInstance().getReference().child("Zutaten");

        // RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize(); // Every item has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spc = new SharedPreferenceClass(this);


        if(spc.getRestZutatHashMap()!=null && spc.getRestZutatHashMap().size()>0){
            map = spc.getRestZutatHashMap();
        }else{
            map = new HashMap<Integer, Zutat>();
            spc.setRestZutatenHashMap(Helper.convertObjectToString(map));
            // Call data from Firebase database
            fillMapFromFirebase();
        }

       // map = Helper.getHashMapDataFromFireBase("Zutaten", getString(R.string.noAlkZutatActivity));

        adapter = new RestlicheZutatenAdapter(this, map);

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        SharedPreferenceClass spc = new SharedPreferenceClass(this);

        HashMap<Integer, Zutat> map = spc.getRestZutatHashMap();//Helper.getZutatenListFromHashMap(spc.getRestZutatHashMap(), list);

        //list = Helper.getZutatenListFromHashMap(map);
        adapter = new RestlicheZutatenAdapter(this, map);
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

/*    private HashMap<Integer, Zutat> getMap(){
        HashMap<Integer, Zutat> map = spc.getRestZutatHashMap();
        return map;
    }*/

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

                    zutat.setName(value);
                    zutat.setActivityName(getString(R.string.restlicheZutatActivity));
                    zutat.setLiter("");
                    zutat.setStatus(-1);
                    System.out.println(i+"RestZutatenActivity______ChildValue: "+value+"  position: "+position);
                    i++;
                }

                map.put(position, zutat);

                spc.updateRestZutatenHashMap(Helper.convertObjectToString(map));
                position++;
                //list.add(zutat);
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

 /*   private void getList(){
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
    }*/




}
