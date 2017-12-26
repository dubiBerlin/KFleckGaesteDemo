package com.dubravko.kfleck.kfleckdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dp on 03.12.2017.
 */
public class Bestellung {

    private List<GewaehlteZutat> list;

    public Bestellung(){
        list = new ArrayList<GewaehlteZutat>();
    }

    public Bestellung(List<GewaehlteZutat> list){
        this.list = list;
    }

    public void setGewaehlteZutat(GewaehlteZutat gz){
        list.add(gz);
    }

    public List<GewaehlteZutat> getGewaehlteZutatenAsList(){
        return list;
    }

    public GewaehlteZutat getGewahlteZutatAtPos(int i){
        return list.get(i);
    }



}
