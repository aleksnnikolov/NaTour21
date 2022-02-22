package com.example.api.demoapi.dao.interfaces;

import com.example.api.demoapi.entity.PuntoItinerario;

import java.util.List;

public interface PuntoItinerarioDAO {
    List<PuntoItinerario> getPercorsoItinerario(String itinerarioID);
    void inserisciPuntoItinerario(PuntoItinerario puntoItinerario);
}
