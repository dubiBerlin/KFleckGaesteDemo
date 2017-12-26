package com.dubravko.kfleck.kfleckdemo.shared;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
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
}
