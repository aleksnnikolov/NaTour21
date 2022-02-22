package com.example.api.demoapi.dao;

import com.example.api.demoapi.dao.interfaces.UtenteDAO;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Utente;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("utente-test")
public class UtenteDAOTest {


    /*@Override
    public String getIdUtente(String email) {
        return "L'id dell'utente " + email + " è 123";
    }

    @Override
    public Utente getDettagliUtente(String id) {
        Utente utente = new Utente();
        utente.setUserID("123");
        utente.setNomeUtente("cortvlkn");
        utente.setEmail("aleks.nikolov@libero.it");
        utente.setDataCreazioneAccount(LocalDateTime.now());
        utente.setImmagineProfilo("img/abc.png");
        return utente;
    }

    @Override
    public EsitoOperazioneResponse nuovoUtente(Utente utente) {
        System.out.println("Creato utente: " + utente.toString());

        return new EsitoOperazioneResponse("OK");
    }

    @Override
    public Boolean userIdDisponibile(String codiceGenerato) {
        return null;
    }*/
}
