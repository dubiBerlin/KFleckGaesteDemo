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
import com.dubravko.knutschfleck.knutschfleckdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoAlkZutatenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;

    // everything for RecyclerView
    private RecyclerView recyclerView;
    private List<Zutat> zutaten;
    private NoAlkZutatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_alk_zutaten);

        mStatusDB = FirebaseDatabase.getInstance().getReference().child("NoAlkohol");

        // RecyclerView settings
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.noAlkListeRecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        zutaten = new ArrayList<Zutat>();
        getList();
       // getList2();


        //zutaten = getStatusesList();
        adapter = new NoAlkZutatAdapter(zutaten,getSupportActionBar(), this);
        recyclerView.setItemViewCacheSize(zutaten.size());
        recyclerView.setAdapter(adapter);
    }


    // ActionBar
    // We add the next button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_button_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_bar_next_btn:
                startActivity(new Intent(NoAlkZutatenActivity.this, RestlicheZutatenActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*  */
    private void getList(){
        mStatusDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Zutat zutat = dataSnapshot.child(key).getValue(Zutat.class);
                Log.i("FETCH----key: ",key);




                //Zutat zutat = dataSnapshot.getValue(Zutat.class);
                // Log.i("FETCH----zutat: ",key+" "+zutat.getName()+" "+zutat.getLiter() );

                int i = 0;

                Zutat zt = new Zutat();
                zt.setActivityName(getString(R.string.noAlkZutatActivity));

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String childKEy = snapshot.getKey();
                    String value = snapshot.getValue().toString();
                    if(i==0){
                        zt.setLiter(value);
                    }else{
                        zt.setName(value);
                    }


                    Log.i("FETCH----childKEy: ",childKEy+" : "+value+" "+i);


                    i = i+1;

                }

                zutaten.add(zt);
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
