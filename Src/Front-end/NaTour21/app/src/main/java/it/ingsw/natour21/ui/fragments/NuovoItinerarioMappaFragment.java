package it.ingsw.natour21.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.osmdroid.views.MapView;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.natour21.MainActivity;
import it.ingsw.natour21.R;
import it.ingsw.natour21.control.GeocodeService;
import it.ingsw.natour21.control.LocationProvider;
import it.ingsw.natour21.control.PercorsoObserver;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.presenters.NuovoItinerarioMappaPresenter;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.ui.MapViewManager;
import it.ingsw.natour21.ui.PannelloIndirizziManager;
import it.ingsw.natour21.ui.PannelloScorrevoleManager;

public class NuovoItinerarioMappaFragment extends Fragment {

    private View view;
    private NuovoItinerarioMappaPresenter nuovoItinerarioMappaPresenter;
    private GeocodeService geocodeService;

    private Context context;

    //Helpers
    private MapViewManager mapViewManager;
    private PannelloIndirizziManager pannelloIndirizziManager;
    private PannelloScorrevoleManager pannelloScorrevoleManager;
    private PercorsoObserver percorsoObserver;

    //flag per verificare lo stato in cui si trova la schermata
    private boolean modalitaInserimentoIndirizzo;
    private boolean modalitaInserimentoDaMappa;


    public NuovoItinerarioMappaFragment() {super(R.layout.fragment_nuovo_itinerario_mappa);}


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        context = getActivity().getApplicationContext();

        creaPresenter();
        creaHelpers();
        gestisciPermessiMappa();

        setupObserver();
        recuperaStatoPuntiItinerario();
    }

    private void creaPresenter() {
        nuovoItinerarioMappaPresenter = new NuovoItinerarioMappaPresenter(this);
        geocodeService = new GeocodeService();
    }

    private void creaHelpers() {
        pannelloIndirizziManager = new PannelloIndirizziManager(this, context, view);
        pannelloScorrevoleManager = new PannelloScorrevoleManager(this, context, view);
        mapViewManager = new MapViewManager(this, context, view);
    }

    private void setupObserver() {
        percorsoObserver = new PercorsoObserver();

        percorsoObserver.addObserver(pannelloIndirizziManager);
        percorsoObserver.addObserver(mapViewManager);
    }

    private void gestisciPermessiMappa() {
        MainActivity activity = (MainActivity) getActivity();
        activity.gestisciPermessiMappa(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
    }

    public void inserisciPuntoPosizioneCorrente() {
        Location location = getPosizioneCorrente();

        if (location != null) {
            double latitudine = location.getLatitude();
            double longitudine = location.getLongitude();

            GeocodeService geocodeService = new GeocodeService();
            String indirizzo = geocodeService.getIndirizzoDaCoordinate(context, latitudine, longitudine);

            PuntoItinerario nuovoPunto = new PuntoItinerario(indirizzo, latitudine, longitudine, decodificaPosizionePunto());
            percorsoObserver.aggiungiPuntoInPosizione(nuovoPunto);
            salvaStatoPuntiItinerario();
            mapViewManager.scorriVersoPunto(latitudine, longitudine);
        } else {
            Toast.makeText(context, "Errore nel rilevamento della posizione", Toast.LENGTH_SHORT).show();
        }
    }

    public Location getPosizioneCorrente() {
        LocationProvider locationProvider = new LocationProvider(context, getActivity());
        return locationProvider.getPosizioneAttuale();
    }

    public void inserisciPuntoItinerarioDiCoordinate(double latitudine, double longitudine) {
        String indirizzo = geocodeService.getIndirizzoDaCoordinate(context, latitudine, longitudine);

        PuntoItinerario nuovoPunto = new PuntoItinerario(indirizzo, latitudine, longitudine, decodificaPosizionePunto());
        percorsoObserver.aggiungiPuntoInPosizione(nuovoPunto);
        salvaStatoPuntiItinerario();
        mapViewManager.scorriVersoPunto(latitudine, longitudine);
    }

    public void inserisciPuntoItinerarioDiIndirizzo(String indirizzo, int posizione) {
        if (indirizzo.isEmpty()) {
            percorsoObserver.rimuoviPuntoInPosizione(posizione);
        } else {
            //TODO: trova coordinate dell'indirizzo inserito e salva
        }
    }

    private int decodificaPosizionePunto() {
        return pannelloIndirizziManager.getPosizioneIndirizzoSelezionato();
    }

    public void entraInModalitaVistaMappa() {
        modalitaInserimentoIndirizzo = false;
        modalitaInserimentoDaMappa = false;

        mapViewManager.disabilitaInserimentoDaMappa();
        pannelloScorrevoleManager.abbassaSlidingPanel();
    }

    public void entraInModalitaInserimentoIndirizzo() {
        modalitaInserimentoIndirizzo = true;
        modalitaInserimentoDaMappa = false;

        mapViewManager.disabilitaInserimentoDaMappa();
        pannelloScorrevoleManager.alzaSlidingPanel();
    }

    public void entraInModalitaSelezioneDaMappa() {
        modalitaInserimentoIndirizzo = false;
        modalitaInserimentoDaMappa = true;

        mapViewManager.abilitaInserimentoDaMappa();
    }

    private void salvaStatoPuntiItinerario() {
        List<PuntoItinerario> puntiItinerario = percorsoObserver.getPercorso();
        nuovoItinerarioMappaPresenter.salvaPuntiItinerarioInCache(puntiItinerario);
    }

    private void recuperaStatoPuntiItinerario() {
        List<PuntoItinerario> puntiItinerario = nuovoItinerarioMappaPresenter.recuperaPuntiItinerarioDaCache();
        percorsoObserver.setPercorso(puntiItinerario);
    }

    public void salvaPercorso() {
        nuovoItinerarioMappaPresenter.salvaPercorso();
    }

    public void invertiPercorso() {
        entraInModalitaVistaMappa();
        percorsoObserver.invertiPersorso();
    }


    //******* Metodi lifecycle

    @Override
    public void onResume() {
        super.onResume();
        mapViewManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewManager.onPause();
        salvaStatoPuntiItinerario();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    Utils.REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}
