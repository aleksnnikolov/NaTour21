package com.example.api.demoapi.entity;

import lombok.Data;

@Data
public class PuntoItinerario {

    private String indirizzo;
    private int posizione; // va da 1 a N
    private double latitudine;
    private double longitudine;
    private String itinerarioID;

}
