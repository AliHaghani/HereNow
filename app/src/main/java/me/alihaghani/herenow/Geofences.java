package me.alihaghani.herenow;

import android.provider.SyncStateContract;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 2016-01-23.
 */
public class Geofences {
    double lat;
    double lon;
    float radius;
    long expiration;

    static Geofence ourFence;
    static List<Geofence> geoFenceList;
    static GeofencingRequest geofencingRequest;



    public Geofences(double lat, double lon, float radius, long expiration) {
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        this.expiration = expiration;
        geoFenceList = new ArrayList<Geofence>(1);
        buildGeofence();
    }

    private void buildGeofence() {
        Geofence.Builder builder = new Geofence.Builder();
        builder.setCircularRegion(lat, lon, radius);
        builder.setExpirationDuration(expiration);
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);
        builder.setRequestId("ourGeofenceYO");
        ourFence = builder.build();
        geoFenceList.add(ourFence);

        GeofencingRequest.Builder reqBuilder = new GeofencingRequest.Builder();
        reqBuilder.addGeofence(ourFence);
        geofencingRequest = reqBuilder.build();

    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        //builder.addGeofences(mGeofenceList);
        return builder.build();
    }


}