package com.dubravko.kfleck.kfleckdemo;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by dp on 14.11.2017.
 */
public class AlkZutatAdapter extends RecyclerView.Adapter<AlkZutatAdapter.ZutatViewHolder>{

    //private List<Zutat> list;
    // We need a list of  StatusStarBtn in order to save the state of the starbutton.
    // The key will be the position of the button
    private HashMap<Integer, Zutat>mapStarBtns;
    // we will show the amount of choosen liters in the action bar. so we need to import it from the activity
    private ActionBar actionBar;
    // saves the addition of choosen liters
    private double amountOfLiter  = 0;
    // We will set a limit alcohol for every glas
    private double maxLiter;

    private SharedPreferenceClass spc;
    private double glasSize;

    private Activity activity;


    public AlkZutatAdapter(HashMap<Integer, Zutat>map, ActionBar actionBar, Activity activity){

        Log.i("MESSAGE OF CONSTRUCTOR","________________ADAPTER_CONSTRUCTOR_______________");

        this.actionBar = actionBar;
        spc = new SharedPreferenceClass(activity);
        glasSize = Double.valueOf(spc.getGlasSize());
        this.mapStarBtns = map;
        //spc.setAlcoholZutatenList(Helper.convertObjectToString(mapStarBtns));
        this.activity = activity;
        amountOfLiter = Double.valueOf(spc.getCurrentAmountChoosenLiters());
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
    }




    @Override
    public ZutatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Hole das Layout für die Row
        return new ZutatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.zutat_row, parent, false));
    }

    // onBindViewHolder is called for every single item in the RecyclerView
    @Override
    public void onBindViewHolder(final ZutatViewHolder holder, final int position){

        final Zutat zutat = mapStarBtns.get(position);
        holder.setZutatName(zutat.getName());
        holder.setZutatMenge(zutat.getLiter());


        System.out.println("________________"+zutat.getName()+" "+zutat.getLiter()+" pos: "+position);


        if(mapStarBtns.get(position).getStatus()==-1){
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_off));
        } else {
            holder.starBtn.setImageDrawable(ContextCompat.getDrawable(activity,android.R.drawable.btn_star_big_on));
        }

        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                                                                                                                        // getStarBtnStatus(position)
                System.out.println("___________________________________onClickListener pos:"+position+" aktueller Status: "+mapStarBtns.get(position)+" "+zutat.getName()+" "+zutat.getLiter());

                Zutat tempZutat = mapStarBtns.get(position);

                // is the star button selected or not
                int aktuellerStatus = tempZutat.getStatus(); //getStarBtnStatus(position);
                aktuellerStatus = aktuellerStatus * (-1);
                tempZutat.setStatus(aktuellerStatus);

                mapStarBtns.put(position,tempZutat);
                //setStatus(position, aktuellerStatus);


                String name  = tempZutat.getName();
                String liter = tempZutat.getLiter();

                amountOfLiter = Double.valueOf(spc.getCurrentAmountChoosenLiters());

                // the button is selected
                if(aktuellerStatus==1){
                    System.out.println("onClickListener aktueller status == 1");

                    // did user
                    if(Helper.roundDouble(amountOfLiter + Double.valueOf(liter))<=maxLiter){
                        // we are rounding the value
                        amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));
                        spc.updateCurrentAmountChoosenLiters(String.valueOf(amountOfLiter));
                        actionBar.setTitle(amountOfLiter+" | "+glasSize);
                        holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_on));
                    }else{
                        //if(amountOfLiter>maxLiter){
                        Toast.makeText(view.getContext(), "Mehr Alkohol geht leider nicht mehr. 0,2dl ist die Grenze.", Toast.LENGTH_LONG).show();

                        tempZutat = mapStarBtns.get(position);
                        tempZutat.setStatus(-1);

                        mapStarBtns.put(position, tempZutat);

                    }
                }else{

                    System.out.println("onClickListener aktueller status == -1");

                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_off));
                    amountOfLiter = Helper.roundDouble(amountOfLiter - Double.valueOf(liter));

                    if(amountOfLiter>=0){
                        actionBar.setTitle(amountOfLiter+" | "+glasSize);
                    }else{
                        amountOfLiter = Helper.roundDouble(amountOfLiter + Double.valueOf(liter));
                    }

                    spc.updateCurrentAmountChoosenLiters(String.valueOf(amountOfLiter));
                }


                spc.updateAlcoholZutatenList(Helper.convertObjectToString(mapStarBtns));
                //printMap(mapStarBtns);
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
        return mapStarBtns.size();
    }

    /*public void printMap(Map mp) {
        System.out.println("___________________PRINTHASHMAP_________________"+mp.size());
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("KEY(Position): "+pair.getKey() + "  VALUE(Status):" + pair.getValue()+"   ITEM:"+list.get(Integer.valueOf(pair.getKey().toString())).getName()+"    "+list.get(Integer.valueOf(pair.getKey().toString())).getLiter());
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }*/

    public void setHashMap(HashMap<Integer, Zutat> map){
        mapStarBtns = map;
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
