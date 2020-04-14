package com.nuum.inouicar_login.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesTools {

    private static final String PREFS = "PREFS";
    private static final String PREFS_AGE = "PREFS_AGE";
    private static final String PREFS_NAME = "PREFS_NAME";
    private static final String PREFS_SCORE = "PREFS_SCORE";


    SharedPreferences sharedPreferences;

    public SharedPreferencesTools(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveUser(String nom, int nouveau_score){
        int best_score = sharedPreferences.getInt("PREFS_SCORE", 0);
        sharedPreferences
                .edit()
                .putString("PREFS_NAME", nom)
                .putInt("PREFS_SCORE", nouveau_score)
                .apply();
    }

    public String getUserName() {
        return sharedPreferences.getString("PREFS_NAME", "");
    }
    public int getUserScore() {
        return sharedPreferences.getInt("PREFS_SCORE", 0);
    }

}
