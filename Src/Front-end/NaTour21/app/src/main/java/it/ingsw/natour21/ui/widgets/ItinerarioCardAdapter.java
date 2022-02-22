package it.ingsw.natour21.ui.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.enums.DifficoltaItinerario;
import it.ingsw.natour21.ui.fragments.HomeFragment;
import it.ingsw.natour21.ui.fragments.MieiItinerariFragment;

public class ItinerarioCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // costanti che permettono di differenziare la recycler view in cui ci troviamo
    private static final int CARD_HOME = 0;
    private static final int CARD_ITINERARI_UTENTE = 1;

    private final Fragment ownerFragment;
    private final OnItinerarioCardListener onItinerarioCardListener;

    private List<Itinerario> itinerari = new ArrayList<>();

    public ItinerarioCardAdapter(Fragment ownerFragment, OnItinerarioCardListener onItinerarioCardListener) {
        this.ownerFragment = ownerFragment;
        this.onItinerarioCardListener = onItinerarioCardListener;
    }

    //Alla creazione dell'Adapter, imposta la card come layout di ogni singola view
    @NonNull
    @Override
    public ItinerarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == CARD_HOME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_itinerario_home, parent, false);
            return new ItinerarioHolder(view, onItinerarioCardListener);
        } else if (viewType == CARD_ITINERARI_UTENTE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_itinerario_miei_itin, parent, false);
            return new MioItinerarioHolder(view, onItinerarioCardListener);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Itinerario itinerario = itinerari.get(position);
        ItinerarioHolder itinerarioHolder;

        //TODO: if-else per scelta tipo itinerario
        if (getItemViewType(position) == CARD_HOME)
            itinerarioHolder = (ItinerarioHolder) holder;
        else
            itinerarioHolder = (MioItinerarioHolder) holder;

        itinerarioHolder.dataInserimentoTextView.setText(Utils.generaIntervalloTemporale(itinerario.getDataInserimento()));
        itinerarioHolder.titoloTextView.setText(itinerario.getTitolo());
        itinerarioHolder.durataTextView.setText(Utils.generaDurataItinerario(itinerario.getDurataInMinuti()));

        mostraDifficolta(itinerario, itinerarioHolder);
        mostraDescrizioneSePresente(itinerario, itinerarioHolder);
        mostraMappaSePresente(itinerario, itinerarioHolder);
    }

    private void mostraDifficolta(Itinerario itinerario, ItinerarioHolder itinerarioHolder) {
        if (itinerario.getDifficoltaItinerario() == DifficoltaItinerario.PASSEGGIATA) {
            itinerarioHolder.difficoltaImageViews[1].setVisibility(View.VISIBLE);
        }
        if (itinerario.getDifficoltaItinerario() == DifficoltaItinerario.MEDIO) {
            itinerarioHolder.difficoltaImageViews[0].setVisibility(View.VISIBLE);
            itinerarioHolder.difficoltaImageViews[1].setVisibility(View.VISIBLE);
        }
        if (itinerario.getDifficoltaItinerario() == DifficoltaItinerario.ARDUO) {
            itinerarioHolder.difficoltaImageViews[0].setVisibility(View.VISIBLE);
            itinerarioHolder.difficoltaImageViews[1].setVisibility(View.VISIBLE);
            itinerarioHolder.difficoltaImageViews[2].setVisibility(View.VISIBLE);
        }
    }

    private void mostraDescrizioneSePresente(Itinerario itinerario, ItinerarioHolder itinerarioHolder) {
        if (!itinerario.getDescrizione().isEmpty()) {
            itinerarioHolder.descrizioneTextView.setText(itinerario.getDescrizione());
            itinerarioHolder.descrizioneMancanteTextView.setVisibility(View.INVISIBLE);
        } else {
            itinerarioHolder.descrizioneMancanteTextView.setVisibility(View.VISIBLE);
        }
    }

    private void mostraMappaSePresente(Itinerario itinerario, ItinerarioHolder itinerarioHolder) {
        if (itinerario.getHasPercorso()) {
            itinerarioHolder.mappaImageView.setVisibility(View.VISIBLE);
        } else {
            itinerarioHolder.mappaImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (ownerFragment.getClass() == HomeFragment.class)
            return CARD_HOME;
        else
            return CARD_ITINERARI_UTENTE;
    }

    @Override
    public int getItemCount() {
        return itinerari.size();
    }

    //Reimposta la lista di film e aggiorna il RecyclerView
    public void setItinerari(List<Itinerario> itinerari) {
        this.itinerari = itinerari;
        notifyDataSetChanged();
    }

    public List<Itinerario> getItinerari() {
        return itinerari;
    }

    public Itinerario getItinerarioInPosizione(int position) {return itinerari.get(position);}

    public void removeAt(int position) {
        itinerari.remove(position);
        notifyItemRemoved(position);
    }

    public class ItinerarioHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // definizione delle views
        protected TextView dataInserimentoTextView;
        protected TextView titoloTextView;
        protected TextView durataTextView;
        protected ImageView[] difficoltaImageViews;
        protected TextView descrizioneTextView;
        protected TextView descrizioneMancanteTextView;
        protected ImageView mappaImageView;

        protected OnItinerarioCardListener onItinerarioCardListener;

        public ItinerarioHolder(@NonNull View itemView, OnItinerarioCardListener onItinerarioCardListener) {
            super(itemView);
            inizializzaViews(itemView);
            this.onItinerarioCardListener = onItinerarioCardListener;

            itemView.setOnClickListener(this);
        }

        private void inizializzaViews(View itemView) {
            dataInserimentoTextView = itemView.findViewById(R.id.text_view_data_inserimento);
            titoloTextView = itemView.findViewById(R.id.text_view_titolo);
            durataTextView = itemView.findViewById(R.id.text_view_durata);
            difficoltaImageViews = new ImageView[3];
            difficoltaImageViews[0] = itemView.findViewById(R.id.image_view_difficolta1);
            difficoltaImageViews[1] = itemView.findViewById(R.id.image_view_difficolta2);
            difficoltaImageViews[2] = itemView.findViewById(R.id.image_view_difficolta3);
            descrizioneTextView = itemView.findViewById(R.id.text_view_descrizione);
            descrizioneMancanteTextView = itemView.findViewById(R.id.text_view_descrizione_mancante);
            mappaImageView = itemView.findViewById(R.id.image_view_mappa);
        }

        @Override
        public void onClick(View view) {
            onItinerarioCardListener.onItinerarioClick(getAdapterPosition());
        }
    }

    public class MioItinerarioHolder extends ItinerarioHolder implements View.OnClickListener {

        private ImageButton eliminaItinerarioButton;

        public MioItinerarioHolder(@NonNull View itemView, OnItinerarioCardListener onItinerarioCardListener) {
            super(itemView, onItinerarioCardListener);
            inizializzaViews(itemView);
        }

        private void inizializzaViews(View itemView) {
            eliminaItinerarioButton = itemView.findViewById(R.id.button_elimina_itinerario);
            eliminaItinerarioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ownerFragment instanceof MieiItinerariFragment)
                        ((MieiItinerariFragment) ownerFragment).eliminaItinerario();
                }
            });
        }
    }

    public interface OnItinerarioCardListener {
        void onItinerarioClick(int position);
    }
}
