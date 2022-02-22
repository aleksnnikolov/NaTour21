package com.example.api.demoapi.dao.responses;

import com.example.api.demoapi.entity.Itinerario;
import lombok.Data;

import java.util.List;

@Data
public class ElencoItinerari {

    private List<Itinerario> itinerari;

    public ElencoItinerari(List<Itinerario> itinerari) {
        this.itinerari = itinerari;
    }

}
