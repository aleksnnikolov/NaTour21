package it.ingsw.natour21.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.ingsw.natour21.R;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.ui.fragments.NuovoItinerarioMappaFragment;

public class PannelloIndirizziManager implements Observer {
    /* Classe helper per NuovoItinearioMappaFragment
       Gestisce tutte le interazioni con i campi di testo degli indirizzi */

    private NuovoItinerarioMappaFragment fragment;
    private Context context;
    private View view;

    //Views
    private MaterialAutoCompleteTextView partenzaEditText;
    private MaterialAutoCompleteTextView destinazioneEditText;
    private MaterialAutoCompleteTextView selectedEditText;
    private Button salvaPercorsoButton;
    private ImageButton invertiPercorsoButton;

    //Il seguente textview appartiene al pannello scorrevole, ma è più adatto alla classe corrente
    private TextView textViewIstruzioni;

    public PannelloIndirizziManager(NuovoItinerarioMappaFragment fragment, Context context, View view) {
        this.fragment = fragment;
        this.context = context;
        this.view = view;

        inizializzaViews();
        aggiungiListener();
    }

    private void inizializzaViews() {
        partenzaEditText = view.findViewById(R.id.edit_text_partenza);
        destinazioneEditText = view.findViewById(R.id.edit_text_destinazione);
        salvaPercorsoButton = view.findViewById(R.id.button_salva_percorso);
        invertiPercorsoButton = view.findViewById(R.id.button_inverti_percorso);

        textViewIstruzioni = view.findViewById(R.id.text_view_istruzioni);
    }

    private void aggiungiListener() {

        partenzaEditText.setOnClickListener(view -> {
            if (partenzaEditText.hasFocus()) {
                selectedEditText = partenzaEditText;
                textViewIstruzioni.setText("inserire l'indirizzo di partenza, oppure...");
                fragment.entraInModalitaInserimentoIndirizzo();
            }
        });

        partenzaEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                selectedEditText = partenzaEditText;
                textViewIstruzioni.setText("inserire l'indirizzo di partenza, oppure...");
                fragment.entraInModalitaInserimentoIndirizzo();
            }
        });

        destinazioneEditText.setOnClickListener(view -> {
            if (destinazioneEditText.hasFocus()) {
                selectedEditText = destinazioneEditText;
                textViewIstruzioni.setText("inserire l'indirizzo di destinazione, oppure...");
                fragment.entraInModalitaInserimentoIndirizzo();
            }
        });

        destinazioneEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                selectedEditText = destinazioneEditText;
                textViewIstruzioni.setText("inserire l'indirizzo di destinazione, oppure...");
                fragment.entraInModalitaInserimentoIndirizzo();
            }
        });

        partenzaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    fragment.inserisciPuntoItinerarioDiIndirizzo(charSequence.toString(), 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        destinazioneEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    fragment.inserisciPuntoItinerarioDiIndirizzo(charSequence.toString(), 2);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salvaPercorsoButton.setOnClickListener(view -> fragment.salvaPercorso());
        invertiPercorsoButton.setOnClickListener(view -> fragment.invertiPercorso());
    }

    /*
    Metodo di Observer
    Aggiorna gli indirizzi in cima alla schermata in base ai punti itinerario registrate
    nell'Observable
    */
    @Override
    public void update(Observable observable, Object puntiItinerario) {
        List<PuntoItinerario> indirizziRegistrati = (List<PuntoItinerario>) puntiItinerario;

        for (PuntoItinerario puntoItinerario : indirizziRegistrati) {
            if (puntoItinerario != null && indirizziRegistrati.indexOf(puntoItinerario) == 0) {
                inserisciIndirizzoInPosizione(puntoItinerario.getIndirizzo(), 1);
            } else if (puntoItinerario != null && indirizziRegistrati.indexOf(puntoItinerario) == indirizziRegistrati.size() - 1) {
                inserisciIndirizzoInPosizione(puntoItinerario.getIndirizzo(), 2);
            }
        }
    }

    private void inserisciIndirizzoInPosizione(String indirizzo, int posizione) {
        if (posizione == 1)
            partenzaEditText.setText(indirizzo);
        if (posizione == 2)
            destinazioneEditText.setText(indirizzo);
    }

    public int getPosizioneIndirizzoSelezionato() {
        if (selectedEditText.equals(partenzaEditText))
            return 1;
        else if (selectedEditText.equals(destinazioneEditText))
            return 2;
        else
            return 0;
    }
}
