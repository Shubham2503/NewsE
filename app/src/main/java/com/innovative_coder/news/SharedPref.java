package com.innovative_coder.news;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPref;
    public SharedPref(Context context){
        mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("Night_Mode", state);
        editor.commit();
    }//Save NIghtMode Stata

    public Boolean loadNightModeState(){
        Boolean state = mySharedPref.getBoolean("Night_Mode",false);
        return state;
    }
}
