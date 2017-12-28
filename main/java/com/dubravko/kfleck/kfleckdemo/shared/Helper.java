package com.dubravko.kfleck.kfleckdemo.shared;

import android.util.Log;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dp on 16.11.2017.
 */
public class Helper {



    // round the double value on two numbers after comma
    public static double roundDouble(double value){
        return value = Math.round(100.0 * value) / 100.0;
    }

    public static void printMap(Map mp) {
        System.out.println("************ HELPER printMap() ***************");
        Iterator it = mp.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            String key = pair.getKey().toString();
            Zutat zutat = (Zutat)pair.getValue();
            System.out.println("key: "+key + " \nname: " + zutat.getName()+"\nstatus: "+zutat.getStatus()+"\nposition: "+zutat.getPosition()+"\n");
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public static List<Zutat> getZutatenListFromHashMap(HashMap<Integer, Zutat> map){
        List<Zutat> list = new ArrayList<Zutat>();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Zutat zutat = (Zutat)pair.getValue();
            list.add(zutat);
        }
        return list;
    }

    public static String convertObjectToString(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static HashMap<Integer, Zutat> convertStringToHashMap(String str){
        Gson gson = new Gson();
        HashMap<Integer, Zutat> obj = (HashMap )gson.fromJson(str, HashMap.class);
        return obj;
    }



    static int position = 0;

    public static void resetPosition(){
        position = 0;
    }


    public static HashMap<Integer,Zutat>  getHashMapDataFromFireBase(String databaseName, final String activityName){

        final DatabaseReference mStatusDB = FirebaseDatabase.getInstance().getReference().child(databaseName);
        final HashMap<Integer,Zutat> map = new HashMap<Integer, Zutat>();

        resetPosition();

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
                zt.setActivityName(activityName);//(getString(R.string.noAlkZutatActivity));

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

                map.put(position, zt);
                position++;
                //adapter.notifyDataSetChanged();
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

        return map;
    }



}
