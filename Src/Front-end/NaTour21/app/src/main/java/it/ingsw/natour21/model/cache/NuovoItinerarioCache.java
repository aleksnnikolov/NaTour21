package it.ingsw.natour21.model.cache;

import java.util.List;

import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.entities.PuntoItinerario;

public class NuovoItinerarioCache {

    private static Itinerario itinerario = new Itinerario();

    public static Itinerario recuperaItinerario() {
        return itinerario;
    }

    public static void salvaPercorso(List<PuntoItinerario> puntiItinerario) {
        itinerario.setPercorso(puntiItinerario);
    }

    public static List<PuntoItinerario> recuperaPercorso() {
        return itinerario.getPercorso();
    }

    public static void svuotaCache() {
        itinerario.setPercorso(null);
    }

}
