package com.example.artemstafeev.trojanow;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by artemstafeev on 3/25/15.
 * post status updates
 */
public class StatusUpdate extends ActionBarActivity {
    String text;
    String sender;
    String location;
    Calendar date;
    public StatusUpdate(String text, String sender)
    {
        date = Calendar.getInstance();
        GPSTracker gpsTracker = new GPSTracker(StatusUpdate.this);
        String stringLatitude = "";
        String stringLongitude = "";
        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);
            location = ConvertPointToLocation(stringLatitude,stringLongitude);
        }
    }
    public String ConvertPointToLocation(String Latitude, String Longitude) {
        String address = "";
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                    Float.parseFloat(Latitude), Float.parseFloat(Longitude), 1);

            if (addresses.size() > 0) {
                for (int index = 0; index < addresses.get(0)
                        .getMaxAddressLineIndex(); index++)
                    address += addresses.get(0).getAddressLine(index) + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }
}
