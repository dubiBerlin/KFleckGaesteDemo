package com.dubravko.knutschfleck.knutschfleckdemo;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dubravko.knutschfleck.knutschfleckdemo.model.Zutat;
import com.dubravko.knutschfleck.knutschfleckdemo.shared.SharedPreferenceClass;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dp on 14.11.2017.
 */
public class ZutatAdapter extends RecyclerView.Adapter<ZutatAdapter.ZutatViewHolder>{

    private List<Zutat> list;
    // We need a hashmap in order to save the state of the starbutton.
    // The key will be the position of the button
    private HashMap<Integer, Integer>map;
    // we will show the amount of choosen liters in the action bar. so we need to import it from the activity
    private ActionBar actionBar;
    // saves the addition of choosen liters
    private double amountOfLiter  = 0;

    private SharedPreferenceClass spc;
    private String glasSize;

    public ZutatAdapter(List<Zutat> list, ActionBar actionBar, Activity activity){
        this.list = list;
        map = new HashMap<Integer, Integer>();
        this.actionBar = actionBar;
        spc = new SharedPreferenceClass(activity);
        glasSize = spc.getGlasSize();

        actionBar.setTitle(amountOfLiter+"/"+glasSize);
    }



    @Override
    public ZutatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Hole das Layout für die Row
        return new ZutatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.zutat_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ZutatViewHolder holder, final int position){
        final Zutat zutat = list.get(position);
        holder.setZutatName(zutat.getName());
        holder.setZutatMenge(zutat.getLiter());

        map.put(Integer.valueOf(position),-1);

        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // is the star button selected or not
                int aktuellerStatus = map.get(position);

                aktuellerStatus = aktuellerStatus * (-1);

                map.put(Integer.valueOf(position), aktuellerStatus);

                String name = list.get(position).getName();
                String liter = list.get(position).getLiter();



                if(aktuellerStatus==1){
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_on));
                    amountOfLiter = amountOfLiter + Double.valueOf(liter);
                    if(amountOfLiter<=Double.valueOf(glasSize)){
                        actionBar.setTitle(amountOfLiter+"/"+glasSize);
                    }else{
                        Toast.makeText(view.getContext(), "Es passt nicht mehr in ihr Glas rein!", Toast.LENGTH_LONG).show();
                        amountOfLiter = amountOfLiter - Double.valueOf(liter);
                    }
                }else{
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_off));
                    amountOfLiter = amountOfLiter - Double.valueOf(liter);

                    if(amountOfLiter>=0){
                        actionBar.setTitle(amountOfLiter+"/"+glasSize);
                    }else{
                        amountOfLiter = amountOfLiter + Double.valueOf(liter);
                    }

                }



                // does the amount of liters fit into the choosen glas?

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    /*
    * ViewHolder class
    * */
    class ZutatViewHolder extends   RecyclerView.ViewHolder {

        // Enthält das Row-Layout
        View view;

        // final
        private ImageButton starBtn;
        private TextView zutatName;
        private TextView zutatMenge;


        public ZutatViewHolder(View viewItem){
            super(viewItem);
            this.view = viewItem;

            starBtn = (ImageButton)viewItem.findViewById(R.id.starBtn);
            zutatName = (TextView)viewItem.findViewById(R.id.zutatName);
            zutatMenge = (TextView)viewItem.findViewById(R.id.zutatMenge);

        }


        public void setZutatName(String name){
            zutatName.setText(name);
        }

        public void setZutatMenge(String menge){
            zutatMenge.setText(menge);
        }





    }



}
