package it.ingsw.natour21.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.ingsw.natour21.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import it.ingsw.natour21.ui.fragments.NuovoItinerarioMappaFragment;

public class PannelloScorrevoleManager {
    /* Classe helper per NuovoItinearioMappaFragment
       Gestisce tutte le interazioni con il sliding panel
     */

    private NuovoItinerarioMappaFragment fragment;
    private Context context;
    private View view;

    private SlidingUpPanelLayout slidingLayout;
    private Button posizioneAttualeButton;
    private Button scegliSuMappaButton;

    public PannelloScorrevoleManager(NuovoItinerarioMappaFragment fragment, Context context, View view) {
        this.fragment = fragment;
        this.context = context;
        this.view = view;

        inizializzaViews();
        aggiungiListener();
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void inizializzaViews() {
        slidingLayout = view.findViewById(R.id.sliding_layout);
        posizioneAttualeButton = view.findViewById(R.id.button_posizione_attuale);
        scegliSuMappaButton = view.findViewById(R.id.button_scegli_su_mappa);
    }

    private void aggiungiListener() {
        posizioneAttualeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.inserisciPuntoPosizioneCorrente();
                abbassaSlidingPanel();
            }
        });

        scegliSuMappaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.entraInModalitaSelezioneDaMappa();
                abbassaSlidingPanel();
            }
        });
    }

    public void alzaSlidingPanel() {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        Log.i("PANEL", "alzaSlidingPanel");
    }

    public void abbassaSlidingPanel() {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

}
