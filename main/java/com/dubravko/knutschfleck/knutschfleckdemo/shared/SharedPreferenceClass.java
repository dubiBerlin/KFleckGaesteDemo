package com.dubravko.knutschfleck.knutschfleckdemo.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.dubravko.knutschfleck.knutschfleckdemo.R;

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




}
