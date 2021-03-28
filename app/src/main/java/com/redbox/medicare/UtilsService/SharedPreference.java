package com.redbox.medicare.UtilsService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String USER_PREF = "medicare_user";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferenceEditor;

    public SharedPreference(Context context){

        sharedPreferences = context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE);
        this.preferenceEditor = sharedPreferences.edit();
    }

    // this is used for storing token

    public int getValueInt(String key){

        return sharedPreferences.getInt(key, 0);
    }

    public void setValueInt(String key, int value){

        preferenceEditor.putInt(key, value).commit();
    }


    public String getValueString(String key){
        return sharedPreferences.getString(key, "");
    }

    public void setValueString(String key, String  value){
        preferenceEditor.putString(key, value).commit();
    }


    public boolean getValueBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void setValueBoolean(String key, boolean value){
        preferenceEditor.putBoolean(key, value).commit();
    }


    // this is used for storing userType-- doctor or patient

    public int getUserInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public void setUserInt(String key, int value){
        preferenceEditor.putInt(key, value).commit();
    }

    public String getUserString(String key){
        return sharedPreferences.getString(key, "");
    }

    public void setUserString(String key, String  value){
        preferenceEditor.putString(key, value).commit();
    }

    public boolean getUserBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void setUserBoolean(String key, boolean value){
        preferenceEditor.putBoolean(key, value).commit();
    }

    // clear storing data when logged out
    public void clear(){
        preferenceEditor.clear().commit();
    }

}
