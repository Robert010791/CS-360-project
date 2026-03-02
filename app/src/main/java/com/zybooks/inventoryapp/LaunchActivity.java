package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {
    //This class will be set as the home activity in the manifest
    // in the onCreate method it needs to check if a user is logged in
    // if the user is logged in then it starts the mainActivity and finish() this activity
    // if the user is not logged in then it starts the login screen.

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SessionManger sessionManger = new SessionManger(this);
        if (sessionManger.getUserLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
