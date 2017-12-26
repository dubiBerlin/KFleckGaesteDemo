package com.dubravko.kfleck.kfleckdemo.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.dubravko.kfleck.kfleckdemo.model.Zutat;
import com.dubravko.knutschfleck.knutschfleckdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by dp on 19.11.2017.
 *
 * We will use this class in order to save and get the glas size which will be
 * choosen in the GlasSizeActivity and the actual amount of choosen liters.
 *
 *
 */
public class SharedPreferenceClass {

    // name of our shared preference file
    private static String preference_file_key;
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public SharedPreferenceClass(Context context){
        // we give the context
        this.context = context;
        // we call the name of the file
        preference_file_key = context.getString(R.string.kfleck_preference_file_key);
        // shared preference with filename and we set it on private
        sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        // editor
        editor = sharedPref.edit();
    }

    public void setGlasSize(String value){
        editor.putString(context.getString(R.string.shared_glas_size), value);
        editor.commit();
    }


    public void updateGlasSize(String value){
        editor.putString(context.getString(R.string.shared_glas_size), value);
        editor.apply();
    }

    public void setCurrentAmountChoosenLiters(String liter){
        editor.putString(context.getString(R.string.shared_amount_liter), liter);
        editor.commit();
    }


    public void updateCurrentAmountChoosenLiters(String liter){
        editor.putString(context.getString(R.string.shared_amount_liter), liter);
        editor.apply();
    }

    public String getGlasSize(){
        return sharedPref.getString(context.getString(R.string.shared_glas_size), null);
    }

    public String getCurrentAmountChoosenLiters(){
        return sharedPref.getString(context.getString(R.string.shared_amount_liter), null);
    }

    public void clearSharedPreference(){
        editor.remove(preference_file_key);
        editor.commit();
    }


    /**
     * function name: NoAlkHashMap
     * @param list
     */
    public void setNoneAlcoholZutatenList(String list){
        editor.putString(context.getString(R.string.shared_none_alcohol_zutaten_hashlist), list);
        editor.commit();
    }

    public void updateNoneAlcoholZutatenList(String list){
        editor.putString(context.getString(R.string.shared_none_alcohol_zutaten_hashlist), list);
        editor.apply();
    }

    public String getNoneAlcoholZutatenListAsString(){
        return sharedPref.getString(context.getString(R.string.shared_none_alcohol_zutaten_hashlist), null);
    }

    public HashMap<Integer,Zutat> getNoneAlcoholZutatHashMap(){
        String strHashMap = getNoneAlcoholZutatenListAsString();
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer,Zutat>>(){}.getType();

        HashMap<Integer,Zutat> hashMap = gson.fromJson(strHashMap, type);
        return hashMap;
    }

    public void updateStatusInNoneAlcoholZutatHashMap(int pos, int status){
        HashMap<Integer,Zutat> hashMap = getNoneAlcoholZutatHashMap();
        Zutat zutat = hashMap.get(pos);
        zutat.setStatus(status);
        hashMap.put(pos,zutat);

        updateNoneAlcoholZutatenList(Helper.convertObjectToString(hashMap));
    }

    /**
     * function name: setAlcoholZutatenList
     * purpose: Saves the HAshMap of Zutaten
     *
     */
     public void setAlcoholZutatenList(String list){
         editor.putString(context.getString(R.string.shared_alcohol_zutaten_hashlist), list);
         editor.commit();
     }

    public void updateAlcoholZutatenList(String list){
        editor.putString(context.getString(R.string.shared_alcohol_zutaten_hashlist), list);
        editor.apply();
    }

    public String getAlcoholZutatenListAsString(){
        return sharedPref.getString(context.getString(R.string.shared_alcohol_zutaten_hashlist), null);
    }

    public HashMap<Integer,Zutat> getAlcoholZutatHashMap(){
        String strHashMap = getAlcoholZutatenListAsString();
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer,Zutat>>(){}.getType();

        HashMap<Integer,Zutat> hashMap = gson.fromJson(strHashMap, type);
        return hashMap;
    }

    public void updateStatusInAlcoholZutatHashMap(int pos, int status){
        HashMap<Integer,Zutat> hashMap = getAlcoholZutatHashMap();
        Zutat zutat = hashMap.get(pos);
        zutat.setStatus(status);
        hashMap.put(pos,zutat);

        updateAlcoholZutatenList(Helper.convertObjectToString(hashMap));
    }

    /********************************
     *                              *
     *      RESTLICHE ZUTATEN       *
     *                              *
     ********************************/
    public void setRestZutatenList(String list){
        editor.putString(context.getString(R.string.shared_restliche_zutaten_hashlist), list);
        editor.commit();
    }

    public void updateRestZutatenList(String list){
        editor.putString(context.getString(R.string.shared_restliche_zutaten_hashlist), list);
        editor.apply();
    }

    public String getRestZutatenListAsString(){
        return sharedPref.getString(context.getString(R.string.shared_restliche_zutaten_hashlist), null);
    }

    public HashMap<Integer,Zutat> getRestZutatHashMap(){
        String strHashMap = getRestZutatenListAsString();
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer,Zutat>>(){}.getType();

        HashMap<Integer,Zutat> hashMap = gson.fromJson(strHashMap, type);
        return hashMap;
    }

    public void updateStatusInRestZutatHashMap(int pos, int status){


        System.out.println("updateStatusInRestZutatHashMap()___pos: "+pos+" status: "+status);

        HashMap<Integer,Zutat> hashMap = getRestZutatHashMap();
        Zutat zutat = hashMap.get(pos);

        System.out.println("zutatname "+zutat.getName()+" status: "+status);


        zutat.setStatus(status);
        hashMap.put(pos,zutat);

        updateRestZutatenList(Helper.convertObjectToString(hashMap));
    }

}
