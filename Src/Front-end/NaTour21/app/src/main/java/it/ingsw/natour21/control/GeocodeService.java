package it.ingsw.natour21.control;

import android.content.Context;
import android.location.Geocoder;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;

public class GeocodeService {

    public String getIndirizzoDaCoordinate(Context context, double latitudine, double longitudine){
        String address = "";
        try {
            Geocoder geocoder = new Geocoder(context);
            address = geocoder.getFromLocation(latitudine, longitudine, 1).get(0).getAddressLine(0);

        } catch (IOException e) {
            address = "";
        }

        return address;
    }

    public String getIndirizzoDaCoordinate(Context context, GeoPoint p){
        String address = "";
        try {
            Geocoder geocoder = new Geocoder(context);
            address = geocoder.getFromLocation(p.getLatitude(), p.getLongitude(), 1).get(0).getAddressLine(0);

        } catch (IOException e) {
            address = "";
        }

        return address;
    }

}
