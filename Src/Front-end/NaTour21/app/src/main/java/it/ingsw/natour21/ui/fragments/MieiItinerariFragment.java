package it.ingsw.natour21.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.UtenteCorrenteHolder;
import it.ingsw.natour21.control.presenters.MieiItinerariPresenter;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.ui.widgets.ItinerarioCardAdapter;

public class MieiItinerariFragment extends Fragment implements ItinerarioCardAdapter.OnItinerarioCardListener {

    private View view;
    private MieiItinerariPresenter mieiItinerariPresenter;

    private TextView numeroItinerariTextView;
    private RecyclerView recyclerView;
    private ItinerarioCardAdapter itinerarioCardAdapter;

    public MieiItinerariFragment() {super(R.layout.fragment_miei_itinerari);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        inizializzaViews();
        creaPresenter();
        setupRecyclerView();
        riempiHomeItinerariRecenti();
    }

    private void inizializzaViews() {
        numeroItinerariTextView = view.findViewById(R.id.text_view_numero_itinerari);
        recyclerView = view.findViewById(R.id.recycler_view_itinerari);
    }

    private void creaPresenter() {
        mieiItinerariPresenter = new MieiItinerariPresenter(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDivider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDivider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider));
        recyclerView.addItemDecoration(itemDivider);

        itinerarioCardAdapter = new ItinerarioCardAdapter(this,this);
        recyclerView.setAdapter(itinerarioCardAdapter);
    }

    private void riempiHomeItinerariRecenti() {
        mieiItinerariPresenter.getItinerariUtente(UtenteCorrenteHolder.utente.getUserID());
    }

    public void setMieiItinerari(List<Itinerario> mieiItinerari) {
        itinerarioCardAdapter.setItinerari(mieiItinerari);
        numeroItinerariTextView.setText(mieiItinerari.size() + " itinerari");
    }

    public void eliminaItinerario() {
        Toast.makeText(getContext(), "COMING SOON", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItinerarioClick(int position) {
        Itinerario itinerarioDaMostrare = itinerarioCardAdapter.getItinerarioInPosizione(position);
        mieiItinerariPresenter.mostraDettagliItinerario(itinerarioDaMostrare);
    }
}
