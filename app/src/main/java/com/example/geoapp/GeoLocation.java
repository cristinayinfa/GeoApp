package com.example.geoapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocation {
    static void getAddress(String location, Context context, Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String latitudeStr = null;
                String longitudeStr = null;
                try {
                    List addressList = geocoder.getFromLocationName(location, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        latitudeStr = String.valueOf(address.getLatitude());
                        longitudeStr= String.valueOf(address.getLongitude());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (longitudeStr != null && latitudeStr !=null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", location);
                        bundle.putString("latitude", latitudeStr);
                        bundle.putString("longitude", longitudeStr);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
