package com.dubravko.kfleck.kfleckdemo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.kfleck.kfleckdemo.shared.Helper;
import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;

import java.util.HashMap;

/**
 * Created by dp on 27.11.2017.
 */
public class NoAlkZutatAdapter extends RecyclerView.Adapter<NoAlkZutatAdapter.ZutatViewHolder>{


    //private List<Zutat> list;
    // We need a list of  StatusStarBtn in order to save the state of the starbutton.
    // The key will be the position of the button
    private HashMap<Integer, Zutat> mapStarBtns;
    // we will show the amount of choosen liters in the action bar. so we need to import it from the activity
    private ActionBar actionBar;
    // saves the addition of choosen liters
    private double amountOfLiter  = 0;
    // We will set a limit alcohol for every glas
    private double maxLiter;

    private SharedPreferenceClass spc;
    private double glasSize;

    private Activity activity;
    private Context context;


    public NoAlkZutatAdapter(HashMap<Integer, Zutat> map, ActionBar actionBar, Activity activity){
        //this.list = list;
        this.actionBar = actionBar;
        mapStarBtns = map;
        spc = new SharedPreferenceClass(activity);
        spc.setNoneAlcoholZutatenList(Helper.convertObjectToString(mapStarBtns));
        spc.updateNoneAlcoholZutatenList(Helper.convertObjectToString(map));
        glasSize = Double.valueOf(spc.getGlasSize());
        this.activity = activity;
        amountOfLiter = Double.valueOf(spc.getCurrentAmountChoosenLiters());
        // according to glassize we set max amount of choosen alc for user
        if(glasSize==0.3){
            maxLiter = 0.3;
        }else{
            if(glasSize==0.5){
                maxLiter = 0.5;
            }else{
                maxLiter=1;
            }
        }

        System.out.println("NoAlkZutatAdapter() ");
        Helper.printMap(mapStarBtns);

        actionBar.setTitle(amountOfLiter+" | "+glasSize);
    }

    public NoAlkZutatAdapter(Context context, HashMap<Integer, Zutat> map, ActionBar actionBar){
        this.context = context;
        mapStarBtns = map;
        this.actionBar = actionBar;
        spc = new SharedPreferenceClass(context);
        spc.setNoneAlcoholZutatenList(Helper.convertObjectToString(map));
        spc.updateNoneAlcoholZutatenList(Helper.convertObjectToString(map));

        System.out.println("NoAlkZutatAdapter() ");
        Helper.printMap(mapStarBtns);

        glasSize = Double.valueOf(spc.getGlasSize());
        amountOfLiter = Double.valueOf(spc.getCurrentAmountChoosenLiters());
        // according to glassize we set max amount of choosen alc for user
        if(glasSize==0.3){
            maxLiter = 0.3;
        }else{
            if(glasSize==0.5){
                maxLiter = 0.5;
            }else{
                maxLiter=1;
            }
        }

        actionBar.setTitle(amountOfLiter+" | "+glasSize);

    }

    @Override
    public void onBindViewHolder(final ZutatViewHolder holder, final int position){

        final Zutat zutat = mapStarBtns.get(position);
        holder.setZutatName(zutat.getName());
        holder.setZutatMenge(zutat.getLiter());


        System.out.println("NoAlkAdapter => onBindViewHolder ________________"+zutat.getName()+" "+zutat.getLiter()+" pos: "+position);

        if(mapStarBtns.get(position).getStatus()==-1){
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_off));
        } else {
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_on));
        }


        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Zutat tempZutat = mapStarBtns.get(position);

                // is the star button selected or not
                int aktuellerStatus = tempZutat.getStatus(); //getStarBtnStatus(position);
                aktuellerStatus = aktuellerStatus * (-1);
                tempZutat.setStatus(aktuellerStatus);

                mapStarBtns.put(position, tempZutat);
                //setStatus(position, aktuellerStatus);


                String name  = tempZutat.getName();
                String liter = tempZutat.getLiter();

                amountOfLiter = Double.valueOf(spc.getCurrentAmountChoosenLiters());

                // the button is selected
                if(aktuellerStatus==1){
                    // did user
                    if(Helper.roundDouble(amountOfLiter + Double.valueOf(liter))<=maxLiter){
                        // we are rounding the value
                        amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));
                        spc.updateCurrentAmountChoosenLiters(String.valueOf(amountOfLiter));
                        actionBar.setTitle(amountOfLiter+" | "+glasSize);
                        holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_on));
                    }else{
                        //if(amountOfLiter>maxLiter){
                        Toast.makeText(view.getContext(), "Mehr Alk geht leider nicht mehr", Toast.LENGTH_LONG).show();
                        Zutat tempZutat2 = mapStarBtns.get(position);
                        tempZutat2.setStatus(-1);

                        mapStarBtns.put(position, tempZutat2);
                    }
                }else{
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_off));
                    amountOfLiter = Helper.roundDouble(amountOfLiter - Double.valueOf(liter));

                    if(amountOfLiter>=0){
                        actionBar.setTitle(amountOfLiter+" | "+glasSize);
                    }else{
                        amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));
                    }
                    spc.updateCurrentAmountChoosenLiters(String.valueOf(amountOfLiter));
                }

                spc.updateNoneAlcoholZutatenList(Helper.convertObjectToString(mapStarBtns));
                Helper.printMap(mapStarBtns);
                // does the amount of liters fit into the choosen glas?
            }
        });
    }

/*    @Override
    public void onBindViewHolder(final ZutatViewHolder holder, final int position){
        final Zutat zutat = list.get(position);
        holder.setZutatName(zutat.getName());
        holder.setZutatMenge(zutat.getLiter());

        // For every single item we will create an object which saves the position and the
        // state for the starbtn
        // First we check if Key exists
        if(!mapStarBtns.containsKey(position)){
            zutat.setStatus(-1);
            zutat.setPosition(position);
            mapStarBtns.put(position, zutat);
            spc.updateNoneAlcoholZutatenList(Helper.convertObjectToString(mapStarBtns));
        }
        Helper.printMap(mapStarBtns);
        System.out.println("________________"+zutat.getName()+" "+zutat.getLiter()+" pos: "+position);

        if(mapStarBtns.get(position).getStatus()==-1){
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_off));
        } else {
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_on));
        }


        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Zutat tempZutat = mapStarBtns.get(Integer.valueOf(position));

                // is the star button selected or not
                int aktuellerStatus = tempZutat.getStatus(); //getStarBtnStatus(position);
                aktuellerStatus = aktuellerStatus * (-1);
                tempZutat.setStatus(aktuellerStatus);

                mapStarBtns.put(position, tempZutat);
                //setStatus(position, aktuellerStatus);


                String name  = list.get(position).getName();
                String liter = list.get(position).getLiter();

                // the button is selected
                if(aktuellerStatus==1){
                    // did user
                    if(Helper.roundDouble(amountOfLiter + Double.valueOf(liter))<=maxLiter){
                        // we are rounding the value
                        amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));
                        spc.updateCurrentAmountChoosenLiters(String.valueOf(amountOfLiter));
                        actionBar.setTitle(amountOfLiter+"/"+glasSize);
                        holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_on));
                    }else{
                        //if(amountOfLiter>maxLiter){
                        Toast.makeText(view.getContext(), "Mehr Alk geht leider nicht mehr", Toast.LENGTH_LONG).show();
                        Zutat tempZutat2 = mapStarBtns.get(position);
                        tempZutat2.setStatus(-1);

                        mapStarBtns.put(position, tempZutat2);
                    }
                }else{
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_off));
                    amountOfLiter = Helper.roundDouble(amountOfLiter - Double.valueOf(liter));

                    if(amountOfLiter>=0){
                        actionBar.setTitle(amountOfLiter+"/"+glasSize);
                    }else{
                        amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));
                    }
                }

                spc.updateNoneAlcoholZutatenList(Helper.convertObjectToString(mapStarBtns));
                Helper.printMap(mapStarBtns);
                // does the amount of liters fit into the choosen glas?
            }
        });
    }*/

    @Override
    public ZutatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Hole das Layout für die Row
        return new ZutatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.zutat_row, parent, false));
    }




    @Override
    public int getItemCount() {
        return mapStarBtns.size();
    }



    class ZutatViewHolder extends   RecyclerView.ViewHolder {

        // Enthält das Row-Layout
        View view;

        // final
        private ImageButton starBtn;
        private TextView zutatName;
        private TextView zutatMenge;

        private boolean selected = false;


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
