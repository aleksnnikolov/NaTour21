package com.example.api.demoapi.service;

import com.example.api.demoapi.dao.interfaces.ItinerarioDAO;
import com.example.api.demoapi.dao.interfaces.PuntoItinerarioDAO;
import com.example.api.demoapi.dao.interfaces.UtenteDAO;
import com.example.api.demoapi.dao.responses.ElencoItinerari;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.DettagliItinerario;
import com.example.api.demoapi.entity.Itinerario;
import com.example.api.demoapi.entity.PuntoItinerario;
import com.example.api.demoapi.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ItinerarioService {

    @Autowired
    @Qualifier("itinerario-mysql")
    private ItinerarioDAO itinerarioDAO;

    @Autowired
    @Qualifier("punto-itinerario-mysql")
    private PuntoItinerarioDAO puntoItinerarioDAO;

    @Autowired
    @Qualifier("utente-mysql")
    private UtenteDAO utenteDAO;


    public ElencoItinerari getItinerariPiuRecenti() {
        List<Itinerario> itinerari = itinerarioDAO.getItinerariPiuRecenti();
        return new ElencoItinerari(itinerari);
    }

    public ElencoItinerari getItinerariUtente(String userID) {
        List<Itinerario> itinerari = itinerarioDAO.getItinerariUtente(userID);
        return new ElencoItinerari(itinerari);
    }

    public DettagliItinerario getDettagliItinerario(String id) {
        Itinerario itinerario = itinerarioDAO.getDettagliItinerario(id);

        List<PuntoItinerario> percorsoItinerario = puntoItinerarioDAO.getPercorsoItinerario(itinerario.getItinerarioID());
        itinerario.setPercorso(percorsoItinerario);

        Utente utente = utenteDAO.getDettagliUtenteByID(itinerario.getUserID());

        DettagliItinerario dettagliItinerario = new DettagliItinerario();
        dettagliItinerario.setItinerario(itinerario);
        dettagliItinerario.setUtente(utente);

        return dettagliItinerario;
    }

    public EsitoOperazioneResponse nuovoItinerario(Itinerario itinerario) {
        String itinerarioID = generaIdItinerario();

        //controlla se l'id generato è già presente in DB, in caso positivo ne genera uno diverso
        while (!itinerarioDAO.itinerarioIdDisponibile(itinerarioID)) {
            itinerarioID = generaIdItinerario();
        }
        itinerario.setItinerarioID(itinerarioID);

        if (itinerario.isHasPercorso()) {
            for (PuntoItinerario puntoItinerario : itinerario.getPercorso()) {
                puntoItinerario.setItinerarioID(itinerario.getItinerarioID());
            }
        }

        System.out.println(itinerario);
        EsitoOperazioneResponse esitoOperazione = itinerarioDAO.nuovoItinerario(itinerario);
        if (esitoOperazione.getEsitoOperazione().equals("OK") && itinerario.isHasPercorso()) {
            for (PuntoItinerario puntoItinerario : itinerario.getPercorso())
                puntoItinerarioDAO.inserisciPuntoItinerario(puntoItinerario);
        }

        return esitoOperazione;
    }

    private String generaIdItinerario() {
        Random rnd = new Random();
        int numero = rnd.nextInt(99999);
        int numero2 = rnd.nextInt(99999);

        return String.format("%05d%05d", numero, numero2);
    }

}
