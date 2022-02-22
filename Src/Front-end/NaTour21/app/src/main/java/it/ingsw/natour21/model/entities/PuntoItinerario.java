package it.ingsw.natour21.model.entities;

public class PuntoItinerario {

    private String indirizzo;
    private double latitudine;
    private double longitudine;
    private int posizione;

    public PuntoItinerario() {}

    public PuntoItinerario(String indirizzo, double latitudine, double longitudine, int posizione) {
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.posizione = posizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    @Override
    public String toString() {
        return "PuntoItinerario{" +
                "indirizzo='" + indirizzo + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", posizione=" + posizione +
                '}';
    }
}
