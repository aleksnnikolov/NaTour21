package it.ingsw.natour21.control;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;

public class LocationProvider {

    private static final int REQUEST_LOCATION = 1;

    private Context context;
    private Activity activity;

    private LocationManager locationManager;

    public LocationProvider(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    /*public  Location getPosizioneAttuale() {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = locationProviderClient.getCurrentLocation(ACCESS_FINE_LOCATION, new CancellationToken() {
                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }

                @Override
                public boolean isCancellationRequested() {
                    return false;
                }
            });
            while (!task.isComplete()) {}

            Location location = task.getResult();

            return location;

        } else {
            activity.requestPermissions(new String[] {ACCESS_FINE_LOCATION}, 99);
        }
        return null;
    }*/

    public  Location getPosizioneAttuale() {
        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        }

        return null;
    }

}
