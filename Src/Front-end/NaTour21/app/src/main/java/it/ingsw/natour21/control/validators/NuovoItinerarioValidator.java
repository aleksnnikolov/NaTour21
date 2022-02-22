package it.ingsw.natour21.control.validators;

import java.util.List;

import it.ingsw.natour21.model.entities.PuntoItinerario;

public class NuovoItinerarioValidator {

   public ValidatorResponse validaCompilazioneCampi(String titolo, List<PuntoItinerario> percorso) {
       boolean esitoValidazione = true;
       String messaggioErrore = "";
       String labelErrore = "";

       if (!isTitoloValido(titolo)) {
           esitoValidazione = false;
           messaggioErrore = "campo titolo obbligatorio";
           labelErrore = "titolo";
       }

       if (!isPercorsoValido(percorso)) {
           esitoValidazione = false;
           messaggioErrore = "percorso non può contenere un solo indirizzo";
           labelErrore = "percorso";
       }

       return new ValidatorResponseItinerario(esitoValidazione, messaggioErrore, labelErrore);
   }

   private boolean isTitoloValido(String titolo) {
       return !titolo.isEmpty();
   }

   private boolean isPercorsoValido(List<PuntoItinerario> percorso) {

       if (percorso != null) {
           // check se tutti gli elementi sono null
           boolean tuttiElementiNull = true;
           for (PuntoItinerario puntoItinerario : percorso) {
               if (puntoItinerario != null) {
                   tuttiElementiNull = false;
                   break;
               }
           }
           if (tuttiElementiNull)
               return true;

           // check se tutti i punti sono diversi da null
           // per la verifica di sopra è impossibile che arrivi un percorso con tutti i punti null
           for (PuntoItinerario puntoItinerario : percorso) {
               if (puntoItinerario == null) {
                   return false;
               }
           }

           return true;
       } else {
           return true;
       }

   }

}
