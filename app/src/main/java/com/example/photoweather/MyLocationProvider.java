package com.example.photoweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;


public class MyLocationProvider {

    LocationManager locationManager;
    boolean canGetLocation;
    Location location;
    Context context;
    int MINTIMEBETWEENUPDATES=5*1000;
    int MINDISTANCEBETWEENUPDATES=10;
    LocationListener myLocationListener;

    public MyLocationProvider(Context context, LocationListener locationListener) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        location=null;
        canGetLocation=false;
        this.myLocationListener=locationListener;
    }

    @SuppressLint("MissingPermission")
    public Location getDeviceLocation(){
        String provider=null;
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            provider=LocationManager.GPS_PROVIDER;
        }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            provider=LocationManager.NETWORK_PROVIDER;
        }else {
            canGetLocation=false;
            location=null;
            return null;
        }


        if(myLocationListener!=null)
            locationManager.requestLocationUpdates(provider,
                    MINTIMEBETWEENUPDATES,
                    MINDISTANCEBETWEENUPDATES,
                    myLocationListener);

        location=locationManager.getLastKnownLocation(provider);
        if(location==null)
           location=getBestLastKnownLocation();

        return location;

    }



    @SuppressLint("MissingPermission")
    Location getBestLastKnownLocation(){

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;


        for (String provider : providers) {
           Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        return bestLocation;
    }

}
