package it.ingsw.natour21.ui.fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.presenters.DettagliItinerarioPresenter;
import it.ingsw.natour21.model.entities.DettagliItinerario;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.entities.Utente;
import it.ingsw.natour21.model.enums.DifficoltaItinerario;

public class DettagliItinerarioFragment extends Fragment {

    private DettagliItinerarioPresenter dettagliItinerarioPresenter;
    private View view;

    private TextView titoloTextView;
    private TextView utenteTextView;
    private TextView durataTextView;
    private TextView difficoltaTextView;
    private ImageView[] difficoltaImageViews;

    private TextView descrizioneTextView;
    private TextView descrizioneMancanteTextView;

    private Button mappaButton;
    private LinearLayout percorsoContainer;
    private TextView percorsoMancanteTextView;


    public DettagliItinerarioFragment() {super(R.layout.fragment_dettagli_itinerario);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        creaPresenter();
        initViews(view);
        aggiungiListener();
        getDettagliItinerario();
    }

    private void creaPresenter() {
        dettagliItinerarioPresenter = new DettagliItinerarioPresenter(this);
    }

    private void initViews(View view) {
        titoloTextView = view.findViewById(R.id.text_view_titolo);
        utenteTextView = view.findViewById(R.id.text_view_utente);
        durataTextView = view.findViewById(R.id.text_view_durata_itinerario);
        difficoltaTextView = view.findViewById(R.id.text_view_difficolta_itinerario);

        difficoltaImageViews = new ImageView[3];
        difficoltaImageViews[0] = view.findViewById(R.id.image_view_difficolta1);
        difficoltaImageViews[1] = view.findViewById(R.id.image_view_difficolta2);
        difficoltaImageViews[2] = view.findViewById(R.id.image_view_difficolta3);

        descrizioneTextView = view.findViewById(R.id.text_view_descrizione_itinerario);
        descrizioneMancanteTextView = view.findViewById(R.id.text_view_descrizione_mancante);

        mappaButton = view.findViewById(R.id.button_mappa);
        percorsoContainer = view.findViewById(R.id.layout_percorso_container);
        percorsoMancanteTextView = view.findViewById(R.id.text_view_percorso_mancante);
    }

    private void aggiungiListener() {
        mappaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apri schermata mappa
            }
        });
    }

    private void getDettagliItinerario() {
        String itinerarioID = DettagliItinerarioFragmentArgs.fromBundle(getArguments()).getItinerarioID();
        dettagliItinerarioPresenter.getDettagliItinerario(itinerarioID);
    }

    public void setDettagliItinerario(DettagliItinerario dettagliItinerario) {
        Utente utente = dettagliItinerario.getUtente();
        SpannableString content = new SpannableString(utente.getNomeUtente());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        utenteTextView.setText(content);

        Itinerario itinerario = dettagliItinerario.getItinerario();
        titoloTextView.setText(itinerario.getTitolo());
        durataTextView.setText(Utils.generaDurataItinerario(itinerario.getDurataInMinuti()));
        mostraDifficolta(itinerario.getDifficoltaItinerario());
        mostraDescrizione(itinerario.getDescrizione());
        mostraPercorso(itinerario);
    }

    private void mostraDifficolta(DifficoltaItinerario difficoltaItinerario) {
        difficoltaTextView.setText(difficoltaItinerario.toString());

        if (difficoltaItinerario == DifficoltaItinerario.PASSEGGIATA) {
            difficoltaImageViews[1].setVisibility(View.VISIBLE);
        }
        if (difficoltaItinerario == DifficoltaItinerario.MEDIO) {
            difficoltaImageViews[0].setVisibility(View.VISIBLE);
            difficoltaImageViews[1].setVisibility(View.VISIBLE);
        }
        if (difficoltaItinerario == DifficoltaItinerario.ARDUO) {
            difficoltaImageViews[0].setVisibility(View.VISIBLE);
            difficoltaImageViews[1].setVisibility(View.VISIBLE);
            difficoltaImageViews[2].setVisibility(View.VISIBLE);
        }
    }

    private void mostraDescrizione(String descrizione) {
        if (!descrizione.isEmpty()) {
            descrizioneTextView.setText(descrizione);
            descrizioneMancanteTextView.setVisibility(View.INVISIBLE);
        } else {
            descrizioneMancanteTextView.setVisibility(View.VISIBLE);
        }
    }

    private void mostraPercorso(Itinerario itinerario) {
        if (itinerario.itinerarioHaPercorsoVisualizzabile()) {
            int counter = 1;
            List<String> indirizziPercorso = itinerario.getIndirizziPercorso();

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
        } else {
            percorsoContainer.setVisibility(View.INVISIBLE);
            percorsoMancanteTextView.setVisibility(View.VISIBLE);
        }
    }


}
