package me.alihaghani.herenow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Stephen on 2016-01-24.
 */
//Unused class, attempt to get the geofence eventhandler to fire by using BroadcastReceivers instead of directly using IntentService
public class SendSMSReceiver extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent){
        Intent serviceIntent = new Intent(context, GeofenceTransitionsIntentService.class);
        serviceIntent.putExtra("service",intent);
        context.startService(serviceIntent);


    }
}
