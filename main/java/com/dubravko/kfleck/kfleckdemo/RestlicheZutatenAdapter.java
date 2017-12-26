package com.dubravko.kfleck.kfleckdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.kfleck.kfleckdemo.shared.Helper;
import com.dubravko.kfleck.kfleckdemo.shared.SharedPreferenceClass;
import com.dubravko.knutschfleck.knutschfleckdemo.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dp on 17.12.2017.
 */
public class RestlicheZutatenAdapter  extends RecyclerView.Adapter<RestlicheZutatenAdapter.ViewHolder>  {

    private Context context;
    private List<Zutat>  listItems;
    private HashMap<Integer, Zutat> mapStarBtns;

    private SharedPreferenceClass spc;

    public RestlicheZutatenAdapter(Context context, List listItem){
        this.context = context;
        this.listItems = listItem;
        mapStarBtns = new HashMap<Integer, Zutat>();
        spc = new SharedPreferenceClass(context);
        spc.setRestZutatenList(Helper.convertObjectToString(mapStarBtns));
    }


    public RestlicheZutatenAdapter( Context context, HashMap<Integer, Zutat> map, List list){
        this.context = context;
        this.listItems = list;
        mapStarBtns = map;
        System.out.println("________________RestlicheZutatenAdapter after reStart");
        Helper.printMap(mapStarBtns);
        spc = new SharedPreferenceClass(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.restliche_zutat_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Zutat zutat = listItems.get(position);

        if(!mapStarBtns.containsKey(position)){
            System.out.println("________________"+zutat.getName()+" "+zutat.getLiter()+" pos: "+position);
            zutat.setStatus(-1);
            zutat.setPosition(position);
            mapStarBtns.put(position, zutat);
            spc.updateRestZutatenList(Helper.convertObjectToString(mapStarBtns));
        }
        //printMap(mapStarBtns);
        System.out.println("________________"+zutat.getName()+" "+zutat.getLiter()+" pos: "+position);


        if(mapStarBtns.get(position).getStatus()==-1){
            holder.cardview.setCardBackgroundColor(Color.rgb(255, 255, 255));
        } else {
            holder.cardview.setCardBackgroundColor(Color.rgb(168, 166, 166));
        }

        holder.name.setText(zutat.getName());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.cardview.setCardBackgroundColor(Color.rgb(168, 166, 166));

                Zutat tempZutat = mapStarBtns.get(position);

                // is the star button selected or not
                int aktuellerStatus = tempZutat.getStatus(); //getStarBtnStatus(position);
                aktuellerStatus = aktuellerStatus * (-1);
                tempZutat.setStatus(aktuellerStatus);

                mapStarBtns.put(position,tempZutat);

                if(aktuellerStatus==1){
                    holder.cardview.setCardBackgroundColor(Color.rgb(168, 166, 166));
                    //holder.name.setTextColor();
                }else{
                    holder.cardview.setCardBackgroundColor(Color.rgb(255, 255, 255));
                    //holder.name.setTextColor();
                }

                spc.updateRestZutatenList(Helper.convertObjectToString(mapStarBtns));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    /*
        * ViewHolder class
        * */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
            cardview = (CardView)itemView.findViewById(R.id.restliche_zutat_cardView);
        }

    }

}
