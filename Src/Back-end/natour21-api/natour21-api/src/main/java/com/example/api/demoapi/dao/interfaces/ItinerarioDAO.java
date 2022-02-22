package com.example.api.demoapi.dao.interfaces;

import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Itinerario;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface ItinerarioDAO {

    public List<Itinerario> getItinerariPiuRecenti();
    public List<Itinerario> getItinerariUtente(String userID);
    public Itinerario getDettagliItinerario(String id);
    public EsitoOperazioneResponse nuovoItinerario(Itinerario itinerario);
    public Boolean itinerarioIdDisponibile(String codiceGenerato);

}
