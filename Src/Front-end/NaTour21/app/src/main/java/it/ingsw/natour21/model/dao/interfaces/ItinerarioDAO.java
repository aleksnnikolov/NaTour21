package it.ingsw.natour21.model.dao.interfaces;

import java.util.List;

import it.ingsw.natour21.model.callbacks.OnCreateItinerario;
import it.ingsw.natour21.model.callbacks.OnGetItinerari;
import it.ingsw.natour21.model.callbacks.OnGetItinerario;
import it.ingsw.natour21.model.entities.Itinerario;

public interface ItinerarioDAO {
    void nuovoItinerario(Itinerario itinerario, OnCreateItinerario callback);
    void getDettagliItinerario(String itinerarioID, OnGetItinerario callback);
    void getItinerariRecenti(OnGetItinerari callback);
    void getItinerariUtente(String utenteID, OnGetItinerari callback);
}
