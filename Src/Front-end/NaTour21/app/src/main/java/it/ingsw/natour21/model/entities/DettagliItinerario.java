package it.ingsw.natour21.model.entities;

public class DettagliItinerario {

    private Itinerario itinerario;
    private Utente utente;

    public DettagliItinerario() {
    }

    public DettagliItinerario(Itinerario itinerario, Utente utente) {
        this.itinerario = itinerario;
        this.utente = utente;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
