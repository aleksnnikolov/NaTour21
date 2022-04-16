package com.example.api.demoapi.dao.interfaces;

import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Utente;

import java.util.Collection;

public interface UtenteDAO {

    //public String getIdUtente(String email);
    public Utente getDettagliUtenteByEmail(String email);
    public Utente getDettagliUtenteByID(String id);
    public EsitoOperazioneResponse utenteEsistente(String email, String provider);
    public EsitoOperazioneResponse nuovoUtente(Utente utente);
    public Boolean userIdDisponibile(String codiceGenerato);

}
