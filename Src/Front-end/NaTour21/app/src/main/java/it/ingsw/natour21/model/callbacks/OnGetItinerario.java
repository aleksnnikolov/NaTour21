package it.ingsw.natour21.model.callbacks;

import java.util.List;

import it.ingsw.natour21.model.entities.DettagliItinerario;
import it.ingsw.natour21.model.entities.Itinerario;

public interface OnGetItinerario {
    void onSuccess(DettagliItinerario itinerario);
    void onFailure(String messaggioErrore);
    void onFailure();
}
