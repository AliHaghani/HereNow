package me.alihaghani.herenow;

import android.Manifest;
import android.app.Fragment;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class MainActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    GoogleApiClient mGoogleApiClient;
    PendingIntent mGeofencePendingIntent;
    private LocationServices mLocationService;
    private double lat;
    private double lon;
    private float radius = 1000;
    private long expiration = 36000;


    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            //--Snippet
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .enableAutoManage(this, 0, this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

             mGoogleApiClient.connect();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng ourLatlng = place.getLatLng();
                lat = ourLatlng.latitude;
                lon = ourLatlng.longitude;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("statusError", "An error occurred: " + status);
            }
        });




    }

        @Override
        protected void onStart() {
            super.onStart();
            if (mGoogleApiClient != null)
                mGoogleApiClient.connect();
        }

        @Override
        protected void onStop() {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
            super.onStop();

            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//                mAdapter.setGoogleApiClient(null);
                mGoogleApiClient.disconnect();
            }
            super.onStop();

        }


        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

        @Override
        public void onConnected(Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {
            if(mGeofencePendingIntent != null){
                LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, mGeofencePendingIntent);
            }

        }



        private static final int CONTACT_PICKER_RESULT = 1001;
        private int RESULT_OK;
        private String DEBUG_TAG;

    //start the contact launcher
    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    //Get the contact they picked to notify
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_PICKER_RESULT) {
            Uri uri = data.getData();
            Cursor cur = getContentResolver().query(uri, null, null, null, null);
            cur.moveToFirst();
            int col = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String num = cur.getString(col);
            Contacts.add(num);
        }


    }


    //wrapper to send an sms to the person they selected to notify
    public void sendSMSToPerson(View view){


    }

    public void onReadyToStart(View view){
        if(mGoogleApiClient.isConnected()){
            Geofences geofences = new Geofences(lat, lon, radius, expiration);

            mGeofencePendingIntent = getGeofenceTransitionPendingIntent();
            if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, Geofences.geofencingRequest , mGeofencePendingIntent);
                //below is deprecated
                //LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, Geofences.geoFenceList, mGeofencePendingIntent);
            }
        }

    }


}