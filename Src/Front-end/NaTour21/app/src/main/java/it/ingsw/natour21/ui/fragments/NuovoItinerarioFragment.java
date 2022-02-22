package it.ingsw.natour21.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.util.List;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.UtenteCorrenteHolder;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.presenters.NuovoItinerarioPresenter;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.model.enums.DifficoltaItinerario;
import it.ingsw.natour21.ui.dialogs.ModificaDurataDialog;
import it.ingsw.natour21.ui.dialogs.UscitaNuovoItinerarioDialog;

public class NuovoItinerarioFragment extends Fragment implements ModificaDurataDialog.ModificaDialogListener, UscitaNuovoItinerarioDialog.UscitaNuovoItinerarioListener {

    private NuovoItinerarioPresenter nuovoItinerarioPresenter;
    private View view;

    private EditText titoloItinerarioEditText;
    private TextView durataTextView;
    private Spinner difficoltaSpinner;
    private EditText descrizioneEditText;
    private ImageButton aggiungiDurataButton;

    private ImageButton eliminaPercorsoButton;
    private Button mappaButton;
    private Button gpxButton;
    private LinearLayout percorsoContainer;

    private Button salvaButton;
    private Button indietroButton;

    private TextView percorsoMancanteTextView;
    private TextView erroreTitoloTextView;
    private TextView errorePercorsoTextView;

    private DifficoltaItinerario difficoltaSelezionata;
    private int durataInMinuti = 0;

    public NuovoItinerarioFragment() {super(R.layout.fragment_nuovo_itinerario);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        creaPresenter();

        inizializzaViews();
        aggiungiListener();
    }

    private void creaPresenter() {
        nuovoItinerarioPresenter = new NuovoItinerarioPresenter(this);
    }

    private void inizializzaViews() {
        titoloItinerarioEditText = view.findViewById(R.id.edit_text_titolo_itinerario);
        durataTextView = view.findViewById(R.id.text_view_durata_itinerario);
        difficoltaSpinner = view.findViewById(R.id.spinner_difficolta_itinerario);
        descrizioneEditText = view.findViewById(R.id.text_area_descrizione_itinerario);
        aggiungiDurataButton = view.findViewById(R.id.button_aggiungi_durata);
        percorsoContainer = view.findViewById(R.id.layout_percorso_container);

        eliminaPercorsoButton = view.findViewById(R.id.button_elimina_percorso);
        mappaButton = view.findViewById(R.id.button_mappa);
        gpxButton = view.findViewById(R.id.button_gpx);

        salvaButton = view.findViewById(R.id.button_salva_itinerario);
        indietroButton = view.findViewById(R.id.button_indietro);

        percorsoMancanteTextView = view.findViewById(R.id.text_view_percorso_mancante);
        erroreTitoloTextView = view.findViewById(R.id.label_titolo_errore);
        errorePercorsoTextView = view.findViewById(R.id.label_percorso_errore);

        setupSpinnerDifficolta();
        mostraPercorso();
    }

    private void setupSpinnerDifficolta() {
        List<String> difficolta = Utils.getDifficoltaItinerario();
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, difficolta);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficoltaSpinner.setAdapter(adapterCategorie);
        difficoltaSpinner.setSelection(0, false);

        difficoltaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        difficoltaSelezionata = DifficoltaItinerario.PASSEGGIATA;
                        break;
                    case 1:
                        difficoltaSelezionata = DifficoltaItinerario.MEDIO;
                        break;
                    case 2:
                        difficoltaSelezionata = DifficoltaItinerario.ARDUO;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        difficoltaSelezionata = DifficoltaItinerario.PASSEGGIATA;
    }

    public void mostraPercorso() {
        if (nuovoItinerarioPresenter.itinerarioHaPercorso()) {
            int counter = 1;
            List<String> indirizziPercorso = nuovoItinerarioPresenter.getIndirizziPercorso();

            for (String indirizzo : indirizziPercorso) {
                LayoutInflater inflater = getLayoutInflater();
                View indirizzoCard = inflater.inflate(R.layout.card_indirizzo, percorsoContainer, false);

                TextView classificazioneTextView = indirizzoCard.findViewById(R.id.text_view_classificazione);
                TextView indirizzoTextView = indirizzoCard.findViewById(R.id.text_view_indirizzo);

                if (counter == 1)
                    classificazioneTextView.setText(R.string.partenza);
                else if (counter == indirizziPercorso.size())
                    classificazioneTextView.setText(R.string.destinazione);
                else {
                    String labelTappa = "   " + getResources().getString(R.string.tappa, String.valueOf(counter - 1));
                    classificazioneTextView.setText(labelTappa);
                }

                indirizzoTextView.setText(indirizzo);

                percorsoContainer.addView(indirizzoCard);
                counter++;
            }

            percorsoContainer.setVisibility(View.VISIBLE);
            percorsoMancanteTextView.setVisibility(View.INVISIBLE);
            eliminaPercorsoButton.setVisibility(View.VISIBLE);
        } else {
            percorsoContainer.setVisibility(View.INVISIBLE);
            percorsoMancanteTextView.setVisibility(View.VISIBLE);
            eliminaPercorsoButton.setVisibility(View.INVISIBLE);
        }
    }

    private void aggiungiListener() {
        aggiungiDurataButton.setOnClickListener(view -> apriDialogDurata());

        eliminaPercorsoButton.setOnClickListener(view -> {
            nuovoItinerarioPresenter.svuotaCacheItinerario();
            mostraPercorso();
        });

        mappaButton.setOnClickListener(view -> nuovoItinerarioPresenter.mostraSchermataMappa());

        gpxButton.setOnClickListener(view -> nuovoItinerarioPresenter.apriStorageDispositivo());

        salvaButton.setOnClickListener(view -> creaNuovoItinearario());

        indietroButton.setOnClickListener(view -> apriDialogUscita());
    }

    private void creaNuovoItinearario() {
        Itinerario itinerario = popolaDatiItinerario();
        nuovoItinerarioPresenter.creaNuovoItinerario(itinerario);
    }

    private Itinerario popolaDatiItinerario() {
        Itinerario itinerario = new Itinerario();

        itinerario.setTitolo(titoloItinerarioEditText.getText().toString());
        itinerario.setDescrizione(descrizioneEditText.getText().toString());
        itinerario.setDurataInMinuti(durataInMinuti);
        itinerario.setDifficoltaItinerario(difficoltaSelezionata);
        itinerario.setDataInserimento(LocalDateTime.now());
        itinerario.setIdUtenteCreatore(UtenteCorrenteHolder.utente.getUserID());
        itinerario.setPercorso(nuovoItinerarioPresenter.getPercorsoInCache());

        if (itinerario.itinerarioHaPercorsoVisualizzabile()) {
            List<PuntoItinerario> percorso = nuovoItinerarioPresenter.getPercorsoInCache();
            itinerario.setHasPercorso(true);
            itinerario.setPercorso(percorso);
        } else {
            itinerario.setHasPercorso(false);
            itinerario.setPercorso(null);
        }

        return itinerario;
    }

    private void apriDialogDurata() {
        ModificaDurataDialog modificaDurataDialog = new ModificaDurataDialog(durataInMinuti);
        modificaDurataDialog.setTargetFragment(NuovoItinerarioFragment.this, 1);
        modificaDurataDialog.show(getParentFragmentManager(), "modifica durata");
    }

    public void apriDialogUscita() {
        UscitaNuovoItinerarioDialog uscitaNuovoItinerarioDialog = new UscitaNuovoItinerarioDialog();
        uscitaNuovoItinerarioDialog.setTargetFragment(NuovoItinerarioFragment.this, 1);
        uscitaNuovoItinerarioDialog.show(getParentFragmentManager(), "modifica durata");
    }

    public void mostraLabelErrore(String qualeLabel, String messaggio) {
        switch (qualeLabel) {
            case "titolo":
                erroreTitoloTextView.setText(messaggio);
                erroreTitoloTextView.setVisibility(View.VISIBLE);
                break;
            case "percorso":
                errorePercorsoTextView.setText(messaggio);
                errorePercorsoTextView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void nascondiMessaggiErrore() {
        erroreTitoloTextView.setVisibility(View.INVISIBLE);
        errorePercorsoTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void confermaUscita() {
        nuovoItinerarioPresenter.tornaInSchermataPrecedente();
    }

    @Override
    public void impostaDurataItinerario(int giorni, int ore, int minuti) {
        // Imposta la durata nell'oggetto di dominio
        durataInMinuti = giorni * 1440 + ore * 60 + minuti;
        durataTextView.setText(Utils.generaDurataItinerario(durataInMinuti));

        aggiungiDurataButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_edit));
    }

    public NuovoItinerarioPresenter getPresenter() {
        return nuovoItinerarioPresenter;
    }
}
