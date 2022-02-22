package it.ingsw.natour21.control.presenters;

import androidx.navigation.Navigation;

import java.util.List;
import java.util.concurrent.Future;

import it.ingsw.natour21.model.callbacks.OnGetItinerari;
import it.ingsw.natour21.model.dao.ItinerarioDAOImpl;
import it.ingsw.natour21.model.dao.interfaces.ItinerarioDAO;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.ui.fragments.HomeFragment;
import it.ingsw.natour21.ui.fragments.HomeFragmentDirections;

public class HomePresenter {

    private HomeFragment homeFragment;
    private ItinerarioDAO itinerarioDAO;

    public HomePresenter(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        itinerarioDAO = new ItinerarioDAOImpl(homeFragment.getContext());
    }

    public void getItinerariRecenti() {
        itinerarioDAO.getItinerariRecenti(new OnGetItinerari() {
            @Override
            public void onSuccess(List<Itinerario> itinerari) {
                homeFragment.setItinerariRecenti(itinerari);
            }

            @Override
            public void onFailure(String messaggioErrore) {
                //TODO: mostra schermata errore nel fragment
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void mostraDettagliItinerario(Itinerario itinerarioDaMostrare) {
        HomeFragmentDirections.ActionHomeFragmentToDettagliItinerarioFragment action =
                HomeFragmentDirections.actionHomeFragmentToDettagliItinerarioFragment(itinerarioDaMostrare.getId());

        if (homeFragment.getView() != null)
            Navigation.findNavController(homeFragment.getView()).navigate(action);
    }
}
