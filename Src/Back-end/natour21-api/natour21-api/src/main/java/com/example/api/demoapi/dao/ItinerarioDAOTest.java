package com.example.api.demoapi.dao;

import com.example.api.demoapi.dao.interfaces.ItinerarioDAO;
import com.example.api.demoapi.entity.Itinerario;
import com.example.api.demoapi.entity.enums.DifficoltaItinerario;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("itinerario-test")
public class ItinerarioDAOTest {

    /*
    @Override
    public List<Itinerario> getItinerariPiuRecenti() {
        return null;
    }

    @Override
    public Itinerario getDettagliItinerario(String id) {
        Itinerario itinerario = new Itinerario();

        itinerario.setItinerarioID("456");
        itinerario.setTitolo("Il grande cammino");
        itinerario.setDescrizione("Si cammina tanto in questo cammino");
        itinerario.setDurata(30);
        itinerario.setDifficoltaItinerario(DifficoltaItinerario.ARDUO);
        itinerario.setDataInserimento(LocalDateTime.now());
        itinerario.setUserID("123");

        return itinerario;
    }

    @Override
    public void nuovoItinerario(Itinerario itinerario) {
        System.out.println("L'utente " + itinerario.getUserID() + " ha creato il seguente itinerario: \n" + itinerario.toString());
    }
    */
}
