package com.dubravko.kfleck.kfleckdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.kfleck.kfleckdemo.shared.Helper;
import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dp on 21.12.2017.
 */
public class BestellungUebersichtAdapter extends RecyclerView.Adapter<BestellungUebersichtAdapter.ViewHolder>{

    private HashMap<Integer, Zutat> alklisteZutaten;
    private List<Zutat> list;
    private Context context;
    private SharedPreferenceClass spc;

    public BestellungUebersichtAdapter(List<Zutat> gewaehlteZutaten, Context context) {
        this.list = gewaehlteZutaten;
        this.context = context;
        spc = new SharedPreferenceClass(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bestellung_uebersicht_row, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Zutat zutat = list.get(position);

        Log.i("_________AUSGABE:::___" ,zutat.getName()+"  Status: "+zutat.getStatus()+" ZutatPos: "+zutat.getPosition()+"\nactName: "+zutat.getActivityName()+"\npos: "+position+"\n");

        if(zutat.getStatus()==1){
            holder.name.setText(zutat.getName());
            holder.liter.setText(zutat.getLiter());
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Zutat zutat = list.get(position);
                String activityName = zutat.getActivityName();
                int pos = zutat.getPosition();
                Toast.makeText(view.getContext(), "wurde gelöscht: "+zutat.getName()+" "+zutat.getActivityName(), Toast.LENGTH_LONG).show();
                list.remove(position);
                notifyDataSetChanged();
                notifyItemRemoved(position);
                System.out.println("onClick");
                System.out.println("Name: "+zutat.getName()+"\nStatus"+zutat.getStatus());
                if(activityName.equals(context.getString(R.string.alkZutatActivity))){
                    spc.updateStatusInAlcoholZutatHashMap(pos, -1);
                    Helper.printMap(spc.getAlcoholZutatHashMap());
                }else{
                    if(activityName.equals(context.getString(R.string.noAlkZutatActivity))){
                        spc.updateStatusInNoneAlcoholZutatHashMap(pos, -1);
                        Helper.printMap(spc.getNoneAlcoholZutatHashMap());
                    }else{
                        spc.updateStatusInRestZutatHashMap(pos, -1);
                        Helper.printMap(spc.getRestZutatHashMap());
                    }
                }

                if(zutat.getLiter()!="" && zutat.getLiter()!=null){

                    double liter =  Double.valueOf(spc.getCurrentAmountChoosenLiters());

                    liter = Helper.roundDouble(liter - Double.valueOf(zutat.getLiter()));

                    spc.updateCurrentAmountChoosenLiters(String.valueOf(liter));

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteFromHashMap(){

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout outer;
        public LinearLayout inner;
        public TextView name;
        public TextView liter;
        public Button deleteBtn;

        public ViewHolder(View itemView){
            super(itemView);

            // Den Listener setzen
            outer = (LinearLayout) itemView.findViewById(R.id.bestellung_outer_layout);
            inner = (LinearLayout) itemView.findViewById(R.id.bestellung_inner_layout);
            name = (TextView)itemView.findViewById(R.id.bestellung_name);
            liter = (TextView)itemView.findViewById(R.id.bestellung_liter);
            deleteBtn = (Button)itemView.findViewById(R.id.bestellung_deleteButton);

        }





    }
}
