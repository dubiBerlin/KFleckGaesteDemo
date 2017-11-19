package com.dubravko.knutschfleck.knutschfleckdemo;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dubravko.knutschfleck.knutschfleckdemo.model.Zutat;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dp on 14.11.2017.
 */
public class ZutatAdapter extends RecyclerView.Adapter<ZutatAdapter.ZutatViewHolder>{

    private List<Zutat> list;
    // Sets the state of star_btn
    private int starBtnIsEnable = -1;
    // We need a hashmap in order to save the state of the starbutton.
    // The key will be the position of the button
    private HashMap<Integer, Integer>map;

    public ZutatAdapter(List<Zutat> list){
        this.list = list;
        map = new HashMap<Integer, Integer>();
    }



    @Override
    public ZutatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Hole das Layout für die Row
        return new ZutatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.zutat_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ZutatViewHolder holder, final int position){
        Zutat zutat = list.get(position);
        holder.setZutatName(zutat.getName());
        holder.setZutatMenge(zutat.getLiter());

        map.put(Integer.valueOf(position),-1);

        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int aktuellerStatus = map.get(position);

                aktuellerStatus = aktuellerStatus * (-1);

                map.put(Integer.valueOf(position), aktuellerStatus);

                Toast.makeText(view.getContext(), "CLICK on position: "+position+" state: "+starBtnIsEnable, Toast.LENGTH_LONG).show();

                if(aktuellerStatus==1){
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_on));
                }else{
                    holder.starBtn.setImageDrawable(ContextCompat.getDrawable(view.getContext(),android.R.drawable.btn_star_big_off));
                }


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
