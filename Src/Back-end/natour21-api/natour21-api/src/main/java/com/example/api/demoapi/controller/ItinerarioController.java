package com.example.api.demoapi.controller;

import com.example.api.demoapi.dao.responses.ElencoItinerari;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.DettagliItinerario;
import com.example.api.demoapi.entity.Itinerario;
import com.example.api.demoapi.service.ItinerarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/natour/route")
public class ItinerarioController {

    @Autowired
    private ItinerarioService itinerarioService;


    @GetMapping(value = "/recent/", produces = "application/json")
    public ElencoItinerari getItinerariPiuRecenti() {
        return itinerarioService.getItinerariPiuRecenti();
    }

    @GetMapping("/user/{userID}")
    public ElencoItinerari getItinerariUtente(@PathVariable String userID) {
        return itinerarioService.getItinerariUtente(userID);
    }

    @GetMapping("/{routeID}")
    public DettagliItinerario getDettagliItinerario(@PathVariable String routeID) {
        return itinerarioService.getDettagliItinerario(routeID);
    }

    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    public EsitoOperazioneResponse creaNuovoItinerario(@RequestBody Itinerario itinerario) {
        return itinerarioService.nuovoItinerario(itinerario);
    }

    @DeleteMapping("/{routeID}")
    public EsitoOperazioneResponse deleteItinerario(@PathVariable String routeID) {
        return itinerarioService.deleteItinerario(routeID);
    }

}
