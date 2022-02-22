package it.ingsw.natour21.model.callbacks;

import java.util.List;

import it.ingsw.natour21.model.entities.Itinerario;

public interface OnGetItinerari {
    void onSuccess(List<Itinerario> itinerari);
    void onFailure(String messaggioErrore);
    void onFailure();
}
