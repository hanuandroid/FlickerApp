package com.android.flickerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "task_pref";

    private static final String KEY_FIRST = "key_first";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public boolean isFirstTime(){
        return pref.getBoolean(KEY_FIRST, true);
    }


    public void setFirstTime(boolean status) {

        editor.putBoolean(KEY_FIRST, status);
        editor.commit();
    }


}
