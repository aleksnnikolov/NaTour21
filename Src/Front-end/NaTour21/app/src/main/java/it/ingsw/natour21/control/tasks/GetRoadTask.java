package it.ingsw.natour21.control.tasks;

import android.os.AsyncTask;

import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

import it.ingsw.natour21.BuildConfig;

public class GetRoadTask extends AsyncTask<ArrayList<GeoPoint>, Void, Road> {

    private RoadManager roadManager;

    public GetRoadTask(RoadManager roadManager) {
        this.roadManager = roadManager;
    }

    @SafeVarargs
    @Override
    public final Road doInBackground(ArrayList<GeoPoint>... percorso) {
        return roadManager.getRoad(percorso[0]);
    }
}
