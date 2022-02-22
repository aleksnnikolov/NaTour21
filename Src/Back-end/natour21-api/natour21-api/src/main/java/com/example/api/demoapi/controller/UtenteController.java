package com.example.api.demoapi.controller;

import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Utente;
import com.example.api.demoapi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/natour/user")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping(value = "/data", produces = "application/json")
    public Utente getDettagliUtente(@RequestParam(name = "email") String email) {
        return utenteService.getDettagliUtente(email);
    }

    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    public EsitoOperazioneResponse creaNuovoUtente(@RequestBody Utente utente) {
        return utenteService.nuovoUtente(utente);
    }

}
