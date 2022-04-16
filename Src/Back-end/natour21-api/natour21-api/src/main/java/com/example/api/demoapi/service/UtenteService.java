package com.example.api.demoapi.service;

import com.example.api.demoapi.dao.interfaces.UtenteDAO;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UtenteService {

    @Autowired
    @Qualifier("utente-mysql")
    private UtenteDAO utenteDAO;

    /*public String getIdUtente(String mail){
        return this.utenteDAO.getIdUtente(mail);
    }*/

    public Utente getDettagliUtente(String email){
        return this.utenteDAO.getDettagliUtenteByEmail(email);
    }

    public EsitoOperazioneResponse utenteEsistente(String email, String provider) {
        return utenteDAO.utenteEsistente(email, provider);
    }

    public EsitoOperazioneResponse nuovoUtente(Utente utente) {
        String userID = generaIdUtente();

        //controlla se l'userID generato è già presente in DB, in caso positivo ne genera uno diverso
        while (!utenteDAO.userIdDisponibile(userID)) {
            userID = generaIdUtente();
        }

        utente.setUserID(userID);
        return utenteDAO.nuovoUtente(utente);
    }

    private String generaIdUtente() {
        Random rnd = new Random();
        int numero = rnd.nextInt(999999);

        return String.format("%06d", numero);
    }

}
