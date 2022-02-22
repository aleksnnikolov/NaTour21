package it.ingsw.natour21.control.presenters;

import it.ingsw.natour21.model.callbacks.OnGetItinerario;
import it.ingsw.natour21.model.dao.ItinerarioDAOImpl;
import it.ingsw.natour21.model.dao.interfaces.ItinerarioDAO;
import it.ingsw.natour21.model.entities.DettagliItinerario;
import it.ingsw.natour21.ui.fragments.DettagliItinerarioFragment;

public class DettagliItinerarioPresenter {

    private DettagliItinerarioFragment dettagliItinerarioFragment;
    private ItinerarioDAO itinerarioDAO;

    public DettagliItinerarioPresenter(DettagliItinerarioFragment dettagliItinerarioFragment) {
        this.dettagliItinerarioFragment = dettagliItinerarioFragment;
        itinerarioDAO = new ItinerarioDAOImpl(dettagliItinerarioFragment.getContext());
    }

    public void getDettagliItinerario(String itinerarioID) {
        itinerarioDAO.getDettagliItinerario(itinerarioID, new OnGetItinerario() {

            @Override
            public void onSuccess(DettagliItinerario dettagliItinerario) {
                dettagliItinerarioFragment.setDettagliItinerario(dettagliItinerario);
            }

            @Override
            public void onFailure(String messaggioErrore) {
                // mostra messaggio errore
            }

            @Override
            public void onFailure() {

            }
        });
    }

}
