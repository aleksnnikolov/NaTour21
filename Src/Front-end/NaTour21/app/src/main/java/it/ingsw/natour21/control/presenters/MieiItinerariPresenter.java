package it.ingsw.natour21.control.presenters;

import androidx.navigation.Navigation;

import java.util.List;

import it.ingsw.natour21.model.callbacks.OnGetItinerari;
import it.ingsw.natour21.model.dao.ItinerarioDAOImpl;
import it.ingsw.natour21.model.dao.interfaces.ItinerarioDAO;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.ui.fragments.HomeFragmentDirections;
import it.ingsw.natour21.ui.fragments.MieiItinerariFragment;
import it.ingsw.natour21.ui.fragments.MieiItinerariFragmentDirections;

public class MieiItinerariPresenter {

    private MieiItinerariFragment mieiItinerariFragment;
    private ItinerarioDAO itinerarioDAO;

    public MieiItinerariPresenter(MieiItinerariFragment mieiItinerariFragment) {
        this.mieiItinerariFragment = mieiItinerariFragment;
        itinerarioDAO = new ItinerarioDAOImpl(mieiItinerariFragment.getContext());
    }

    public void getItinerariUtente(String userID) {
        itinerarioDAO.getItinerariUtente(userID, new OnGetItinerari() {
            @Override
            public void onSuccess(List<Itinerario> itinerari) {
                mieiItinerariFragment.setMieiItinerari(itinerari);
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
        MieiItinerariFragmentDirections.ActionMieiItinerariFragmentToDettagliItinerarioFragment action =
                MieiItinerariFragmentDirections.actionMieiItinerariFragmentToDettagliItinerarioFragment(itinerarioDaMostrare.getId());

        if (mieiItinerariFragment.getView() != null)
            Navigation.findNavController(mieiItinerariFragment.getView()).navigate(action);
    }
}
