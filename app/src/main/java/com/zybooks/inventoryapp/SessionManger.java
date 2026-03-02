package com.zybooks.inventoryapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManger {

        // SessionManger is used to add data to sharedPreferences
        // which stores persistent data. The data that is stored is used
    // to determine if a user is logged in. If they are logged in then the application opens
    // to the inventory screen and to the login screen otherwise. The sessionManager is
    // also used when adding new items to attach the userId to new items.
        private static final String PREF_NAME = "userPref";
        private static final String USER_ID_KEY = "userId";
        private static final String USER_LOGGED_IN = "IsUserLoggedIn";

        private final SharedPreferences sharedPrefs;
        private final SharedPreferences.Editor editor;

        public SessionManger(Context context){
            sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPrefs.edit();
        }

        public void createLoginSession(Long userId){
            editor.putLong(USER_ID_KEY, userId);
            editor.putBoolean(USER_LOGGED_IN, true);
            editor.apply();
        }



        public long getUserId(){
            return sharedPrefs.getLong(USER_ID_KEY, -1);
        }

        public boolean getUserLoggedIn(){
            return sharedPrefs.getBoolean(USER_LOGGED_IN, false);
        }

        public void logout(){
            editor.clear();
            editor.apply();
        }

}
