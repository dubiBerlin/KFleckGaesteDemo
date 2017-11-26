package com.dubravko.kfleck.kfleckdemo;


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

import com.dubravko.kfleck.kfleckdemo.model.StatusStarBtn;
import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.kfleck.kfleckdemo.shared.Helper;
import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dp on 14.11.2017.
 */
public class ZutatAdapter extends RecyclerView.Adapter<ZutatAdapter.ZutatViewHolder>{

    private List<Zutat> list;
    // We need a list of  StatusStarBtn in order to save the state of the starbutton.
    // The key will be the position of the button
    private List<StatusStarBtn>starBtns;
    private HashMap<Integer, Integer>mapStarBtns;
    // we will show the amount of choosen liters in the action bar. so we need to import it from the activity
    private ActionBar actionBar;
    // saves the addition of choosen liters
    private double amountOfLiter  = 0;
    // We will set a limit alcohol for every glas
    private double maxLiter;

    private SharedPreferenceClass spc;
    private double glasSize;

    private Activity activity;

    public ZutatAdapter(List<Zutat> list, ActionBar actionBar, Activity activity){

        this.list = list;
        this.actionBar = actionBar;
        spc = new SharedPreferenceClass(activity);
        glasSize = Double.valueOf(spc.getGlasSize());
        starBtns = new ArrayList<StatusStarBtn>();
        mapStarBtns = new HashMap<Integer, Integer>();
        this.activity = activity;
        // according to glassize we set max amount of choosen alc for user
        if(glasSize==0.3){
            maxLiter = 0.2;
        }else{
            if(glasSize==0.5){
                maxLiter = 0.3;
            }else{
                maxLiter=0.6;
            }
        }
        actionBar.setTitle(amountOfLiter+"/"+glasSize);
    }




    @Override
    public ZutatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Hole das Layout für die Row
        return new ZutatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.zutat_row, parent, false));
    }

    // onBindViewHolder is called for every single item in the RecyclerView
    @Override
    public void onBindViewHolder(final ZutatViewHolder holder, final int position){
        final Zutat zutat = list.get(position);
        holder.setZutatName(zutat.getName());
        holder.setZutatMenge(zutat.getLiter());

        // For every single item we will create an object which saves the position and the
        // state for the starbtn
        /*if(!starBtnExists(position)){
            StatusStarBtn statusStarBtn = new StatusStarBtn(position, -1);
            starBtns.add(statusStarBtn);
        }*/
       // mapStarBtns.put(Integer.valueOf(position), -1);
        // First we check if Key exists
        if(!mapStarBtns.containsKey(position)){
            mapStarBtns.put(position, -1);
        }
        printMap(mapStarBtns);
        System.out.println("________________"+zutat.getName()+" "+zutat.getLiter()+" pos: "+position);

        if(mapStarBtns.get(position)==-1){
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_off));
        } else {
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_on));
        }


        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                                                                                                                        // getStarBtnStatus(position)
                System.out.println("___________________________________onClickListener pos:"+position+" aktueller Status: "+mapStarBtns.get(position)+" "+zutat.getName()+" "+zutat.getLiter());

                // is the star button selected or not
                int aktuellerStatus = mapStarBtns.get(Integer.valueOf(position)); //getStarBtnStatus(position);
                aktuellerStatus = aktuellerStatus * (-1);

                mapStarBtns.put(position, aktuellerStatus);
                //setStatus(position, aktuellerStatus);


                String name  = list.get(position).getName();
                String liter = list.get(position).getLiter();

                // so the button is selected
                if(aktuellerStatus==1){
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_on));

                    // we are rounding the value
                    amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));

                    if(amountOfLiter<=maxLiter){
                        actionBar.setTitle(amountOfLiter+"/"+glasSize);
                    }else{
                        Toast.makeText(view.getContext(), "Mehr Alk geht leider nicht mehr", Toast.LENGTH_LONG).show();
                        amountOfLiter = Helper.roundDouble(amountOfLiter - Double.valueOf(liter));
                        holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_off));
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

                printMap(mapStarBtns);
                // does the amount of liters fit into the choosen glas?
            }
        });
    }

/*    private int getStarBtnStatus(int position){
        System.out.println("getStarBtnStatus() pos: "+position);
        int status = 10;
        for(int i = 0; i < starBtns.size(); i++){
            if(starBtns.get(i).getPosition()==position){
                status = starBtns.get(i).getStatus();
                System.out.println("getStarBtnStatus() position found: "+position);
            }
        }
        return status;
    }

    private void setStatus(int pos, int status){
        for(int i = 0; i < starBtns.size(); i++){
            if(starBtns.get(i).getPosition()==pos){
                starBtns.get(i).setStatus(status);
            }
        }
    }

    private boolean starBtnExists(int pos){
        for(int i = 0; i < starBtns.size(); i++){
            if(starBtns.get(i).getPosition()==pos){
                return true;
            }
        }
        return false;
    }*/

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void printMap(Map mp) {
        System.out.println("___________________PRINTHASHMAP_________________"+mp.size());
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("KEY(Position): "+pair.getKey() + "  VALUE(Status):" + pair.getValue()+"   ITEM:"+list.get(Integer.valueOf(pair.getKey().toString())).getName()+"    "+list.get(Integer.valueOf(pair.getKey().toString())).getLiter());
            //it.remove(); // avoids a ConcurrentModificationException
        }
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
