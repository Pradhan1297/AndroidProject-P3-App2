package com.example.project3cs478app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityManager extends AppCompatActivity {
     BroadcastReceiver aBroadcastReceiver;
     BroadcastReceiver hBroadcastReceiver;

     //ActivityManager registers broadcast receivers
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        aBroadcastReceiver = new AttractionReceiver();
        hBroadcastReceiver = new HotelReceiver();


        IntentFilter aIntentFilter = new IntentFilter("Chicago Attractions");
        IntentFilter hIntentFilter = new IntentFilter("Chicago Hotels");

        registerReceiver(aBroadcastReceiver,aIntentFilter,"edu.uic.cs478.fall22.mp3",null);
        registerReceiver(hBroadcastReceiver,hIntentFilter,"edu.uic.cs478.fall22.mp3",null);
    }
    public void onDestroy() {
        super.onDestroy() ;
        unregisterReceiver(aBroadcastReceiver);
        unregisterReceiver(hBroadcastReceiver) ;
    }
}