package it.ingsw.natour21.ui;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import it.ingsw.natour21.BuildConfig;
import it.ingsw.natour21.R;
import it.ingsw.natour21.control.tasks.GetRoadTask;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.ui.fragments.NuovoItinerarioMappaFragment;

public class MapViewManager implements Observer {
    /* Classe helper per NuovoItinearioMappaFragment
       Gestisce tutte le interazioni con la mappa e relative configurazioni */

    private NuovoItinerarioMappaFragment fragment;
    private Context context;
    private View view;

    private MapView mapView;
    private IMapController mapController;
    private RoadManager roadManager;
    private boolean inserimentoAbilitato;
    private GeoPoint startingLocation;
    
    private ArrayList<GeoPoint> rappresentazionePercorso;
    private static List<Polyline> percorsiSuMappa;

    static {
        percorsiSuMappa = new ArrayList<>();
    }

    public MapViewManager(NuovoItinerarioMappaFragment fragment, Context context, View view) {
        this.fragment = fragment;
        this.context = context;
        this.view = view;

        this.mapView = view.findViewById(R.id.mappa);

        setupMappa();
        setupEventListenerMappa();
    }

    private void setupMappa() {
        //IMPORTANTE: permette di scaricare le tiles per la mappa, assicurati che venga chiamato
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        roadManager = new OSRMRoadManager(fragment.getActivity(), BuildConfig.APPLICATION_ID);
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_FOOT);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        MyLocationNewOverlay myLocation = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mapView);
        myLocation.enableMyLocation();
        mapView.getOverlays().add(myLocation);

        CompassOverlay compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(rotationGestureOverlay);

        mapController = mapView.getController();
        scorriVersoPunto(40.8518, 14.2681);
    }

    private void setupEventListenerMappa() {
        MapEventsReceiver eventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if (inserimentoAbilitato) {
                    mapController.animateTo(p);
                    fragment.inserisciPuntoItinerarioDiCoordinate(p.getLatitude(), p.getLongitude());
                    disabilitaInserimentoDaMappa();
                }

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay eventsOverlay = new MapEventsOverlay(eventsReceiver);
        mapView.getOverlays().add(eventsOverlay);
    }

    /*
       Metodo di Observer
       Aggiorna i marker sulla mappa in base ai punti itinerario registrate
       nell'Observable
    */
    @Override
    public void update(Observable observable, Object puntiRegistrati) {
        pulisciMappa();
        mapView.invalidate();

        List<PuntoItinerario> indirizziRegistrati = (List<PuntoItinerario>) puntiRegistrati;
        ArrayList<GeoPoint> rappresentazionePercorso = new ArrayList<>();

        Iterator<PuntoItinerario> iterator = indirizziRegistrati.iterator();
        while (iterator.hasNext()) {
            PuntoItinerario puntoItinerario = iterator.next();

            boolean isLast = false;
            if (!iterator.hasNext()) {
                isLast = true;
            }

            if (puntoItinerario != null) {
                inserisciMarker(puntoItinerario.getIndirizzo(),
                        puntoItinerario.getLatitudine(),
                        puntoItinerario.getLongitudine(),
                        isLast);

                GeoPoint punto = new GeoPoint(puntoItinerario.getLatitudine(), puntoItinerario.getLongitudine());
                rappresentazionePercorso.add(punto);
            }
        }

        if (rappresentazionePercorso.size() > 1)
            disegnaLineaPercorso(rappresentazionePercorso);
    }

    public void scorriVersoPunto(double latitudine, double longitudine) {
        GeoPoint punto = new GeoPoint(latitudine, longitudine);
        mapController.animateTo(punto, 13.0, 1L);
    }

    private void pulisciMappa() {
        for (Overlay overlay : mapView.getOverlays()) {
            if (overlay.getClass().equals(Marker.class) ||
                overlay.getClass().equals(Polyline.class)) {
                mapView.getOverlays().remove(overlay);
            }
        }
    }

    private void disegnaLineaPercorso(ArrayList<GeoPoint> rappresentazionePercorso) {
        Road road = null;
        try {
            road = new GetRoadTask(roadManager).execute(rappresentazionePercorso).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
        roadOverlay.getOutlinePaint().setColor(ContextCompat.getColor(context, R.color.red));
        percorsiSuMappa.add(roadOverlay);
        mapView.getOverlays().add(roadOverlay);
        mapView.invalidate();
    }

    private void inserisciMarker(String titolo, double latitudine, double longitudine, boolean isLast) {
        Marker marker = new Marker(mapView);
        marker.setTitle(titolo);
        marker.setPosition(new GeoPoint(latitudine, longitudine));
        if (isLast) {
            marker.setIcon(AppCompatResources.getDrawable(context, R.drawable.ic_finish_line));
        }

        mapView.getOverlays().add(marker);
        mapView.invalidate();
    }

    public void abilitaInserimentoDaMappa() {
        inserimentoAbilitato = true;
    }

    public void disabilitaInserimentoDaMappa() {
        inserimentoAbilitato = false;
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }
}
