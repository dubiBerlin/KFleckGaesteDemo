package com.dubravko.kfleck.kfleckdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BestellungUebersichtActivity extends AppCompatActivity {

    private HashMap<Integer, Zutat> alklisteZutaten;
    private HashMap<Integer, Zutat> restlisteZutaten;
    private HashMap<Integer, Zutat> noneAlklisteZutaten;
    private LinearLayout rootLayout;
    private List<Zutat> gewaehlteZutatenListe;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellung);

        getSupportActionBar().setTitle("Meine Bestellung");

        // Call list
        SharedPreferenceClass spc = new SharedPreferenceClass(this);
        alklisteZutaten = spc.getAlcoholZutatHashMap();
        restlisteZutaten = spc.getRestZutatHashMap();
        noneAlklisteZutaten = spc.getNoneAlcoholZutatHashMap();

        gewaehlteZutatenListe = getList(alklisteZutaten,noneAlklisteZutaten, restlisteZutaten);


        recyclerView = (RecyclerView)findViewById(R.id.bestellung_recyclerView);
        recyclerView.hasFixedSize(); // Every item has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BestellungUebersichtAdapter(gewaehlteZutatenListe, this);

        recyclerView.setAdapter(adapter);

    }

    private List<Zutat> getList(Map mp1, Map mp2, Map mp3){
        List<Zutat> liste = new ArrayList<Zutat>();

        Iterator it = mp1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Zutat zutat = (Zutat)pair.getValue();

            if(zutat.getStatus()==1){
                liste.add(zutat);
            }
        }

        it = mp2.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Zutat zutat = (Zutat)pair.getValue();

            if(zutat.getStatus()==1){
                liste.add(zutat);
            }
        }

        it = mp3.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Zutat zutat = (Zutat)pair.getValue();

            if(zutat.getStatus()==1){
                liste.add(zutat);
            }
        }
        return liste;
    }


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellung);

        rootLayout = (LinearLayout) findViewById(R.id.bestellung_root_layout);

        alklisteZutaten = new HashMap<Integer, Zutat>();

        SharedPreferenceClass spc = new SharedPreferenceClass(this);

        alklisteZutaten = spc.getAlcoholZutatHashMap();

        printMap(alklisteZutaten);

        createGuiElement(alklisteZutaten);
    }

    private void createGuiElement(Map mp){

        Iterator it = mp.entrySet().iterator();
        int i = 0;

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            Zutat zutat = (Zutat)pair.getValue();

            if(zutat.getStatus()==1){

                // Create LinearLayout
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);


                LinearLayout layoutInner = new LinearLayout(this);
                layoutInner.setOrientation(LinearLayout.VERTICAL);

                // Create TextView
                TextView zutatName = new TextView(this);
                zutatName.setText(zutat.getName());
                layoutInner.addView(zutatName);

                // Create TextView
                TextView zutatLiter = new TextView(this);
                zutatLiter.setText(zutat.getLiter());
                layoutInner.addView(zutatLiter);

                layout.addView(layoutInner);


                Button deleteBtn = new Button(this);
                deleteBtn.setId(i);
                deleteBtn.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_delete, 0, 0, 0);
                deleteBtn.setBackgroundColor(Color.WHITE);

                layout.addView(deleteBtn);
                rootLayout.addView(layout);
            }
            i++;
        }
    }*/

    public static void printMap(Map mp) {
        System.out.println("************ HELPER printMap() ***************");
        Iterator it = mp.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            Zutat zutat = (Zutat)pair.getValue();


            System.out.println(pair.getKey() + " = " + zutat.getName()+" "+ zutat.getLiter()+" "+ zutat.getStatus());
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
