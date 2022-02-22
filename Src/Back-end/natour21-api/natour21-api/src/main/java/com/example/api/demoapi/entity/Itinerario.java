package com.example.api.demoapi.entity;

import com.example.api.demoapi.entity.enums.AmbienteItinerario;
import com.example.api.demoapi.entity.enums.DifficoltaItinerario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class Itinerario {

    private String userID;
    private String itinerarioID;
    private String titolo;
    private String descrizione;
    private int durata;
    private DifficoltaItinerario difficolta;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dataInserimento;

    private boolean hasPercorso;
    private List<PuntoItinerario> percorso;

}
