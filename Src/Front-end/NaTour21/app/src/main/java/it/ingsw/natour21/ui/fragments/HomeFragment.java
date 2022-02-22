package it.ingsw.natour21.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.List;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.UtenteCorrenteHolder;
import it.ingsw.natour21.control.presenters.HomePresenter;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.entities.Utente;
import it.ingsw.natour21.ui.widgets.ItinerarioCardAdapter;

public class HomeFragment extends Fragment implements ItinerarioCardAdapter.OnItinerarioCardListener {

    private View view;
    private HomePresenter homePresenter;

    private RecyclerView recyclerView;
    private ItinerarioCardAdapter itinerarioCardAdapter;
    private FloatingActionButton nuovoItinerarioButton;

    public HomeFragment() {super(R.layout.fragment_home);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        cablaUtentePerTesting();
        inizializzaViews();
        creaPresenter();
        setupRecyclerView();
        riempiHomeItinerariRecenti();
    }

    private void inizializzaViews() {
        recyclerView = view.findViewById(R.id.recycler_view_itinerari);
        nuovoItinerarioButton = view.findViewById(R.id.button_nuovo_itinerario);

        nuovoItinerarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToNuovoItinerarioFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    private void creaPresenter() {
        homePresenter = new HomePresenter(this);
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
        homePresenter.getItinerariRecenti();
    }

    public void setItinerariRecenti(List<Itinerario> itinerariRecenti) {
        itinerarioCardAdapter.setItinerari(itinerariRecenti);
    }

    @Override
    public void onItinerarioClick(int position) {
        Itinerario itinerarioDaMostrare = itinerarioCardAdapter.getItinerarioInPosizione(position);
        homePresenter.mostraDettagliItinerario(itinerarioDaMostrare);
    }


    //TODO: da eliminare
    private void cablaUtentePerTesting() {
        Utente utente = new Utente("685433", "cortvlkn", "aleks.nikolov@libero.it", "/img.png", LocalDateTime.now());
        UtenteCorrenteHolder.utente = utente;
    }
}
