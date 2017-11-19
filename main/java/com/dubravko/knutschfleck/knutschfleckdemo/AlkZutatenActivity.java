package com.dubravko.knutschfleck.knutschfleckdemo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dubravko.knutschfleck.knutschfleckdemo.model.Zutat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlkZutatenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;

    // everything for RecyclerView
    private RecyclerView recyclerView;
    private List<Zutat> zutaten;
    private ZutatAdapter adapter;

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

        zutaten = new ArrayList<>();
        getList();
        //zutaten = getStatusesList();
        adapter = new ZutatAdapter(zutaten);
        recyclerView.setAdapter(adapter);

    }

    private List<Zutat> getStatusesList(){
        List<Zutat> list = new ArrayList<Zutat>();

        for(int i = 0; i < 10; i++){
            list.add(new Zutat("Wodka", "Liter_"+i));
        }
        return list;
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
