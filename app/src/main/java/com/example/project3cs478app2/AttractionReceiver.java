package com.example.project3cs478app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AttractionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       String action = intent.getAction();
       if(action.equals("Chicago Attractions")){
           Intent aIntent = new Intent(context, Attractions.class);
           context.startActivity(aIntent);
       }
    }
}