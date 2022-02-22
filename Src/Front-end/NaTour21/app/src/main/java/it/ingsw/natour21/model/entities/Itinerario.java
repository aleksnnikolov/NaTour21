package it.ingsw.natour21.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.natour21.model.enums.DifficoltaItinerario;

public class Itinerario {

    private String idUtenteCreatore;
    private String id;
    private String titolo;
    private String descrizione;
    private int durataInMinuti;;
    private DifficoltaItinerario difficoltaItinerario;
    private LocalDateTime dataInserimento;
    private boolean hasPercorso;
    private List<PuntoItinerario> percorso;

    public Itinerario() {

    }

    public Itinerario(String idUtenteCreatore,
                      String id,
                      String titolo,
                      String descrizione,
                      int durataInMinuti,
                      DifficoltaItinerario difficoltaItinerario,
                      LocalDateTime dataInserimento,
                      boolean hasPercorso,
                      List<PuntoItinerario> percorso) {
        this.idUtenteCreatore = idUtenteCreatore;
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.durataInMinuti = durataInMinuti;
        this.difficoltaItinerario = difficoltaItinerario;
        this.dataInserimento = dataInserimento;
        this.hasPercorso = hasPercorso;
        this.percorso = percorso;
    }

    public boolean itinerarioHaPercorsoVisualizzabile() {
        boolean hasPercorso = false;

        if (percorso != null) {
            for (PuntoItinerario puntoItinerario : percorso) {
                if (puntoItinerario != null) {
                    hasPercorso = true;
                    break;
                }
            }
        }

        return hasPercorso;
    }

    public List<String> getIndirizziPercorso() {
        List<String> indirizzi = new ArrayList<>();

        if (percorso != null) {
            for (PuntoItinerario puntoItinerario : percorso) {
                if (puntoItinerario == null)
                    indirizzi.add(null);
                else
                    indirizzi.add(puntoItinerario.getIndirizzo());
            }
        }

        return indirizzi;
    }

    public String getIdUtenteCreatore() {
        return idUtenteCreatore;
    }

    public void setIdUtenteCreatore(String idUtenteCreatore) {
        this.idUtenteCreatore = idUtenteCreatore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getDurataInMinuti() {
        return durataInMinuti;
    }

    public void setDurataInMinuti(int durataInMinuti) {
        this.durataInMinuti = durataInMinuti;
    }

    public DifficoltaItinerario getDifficoltaItinerario() {
        return difficoltaItinerario;
    }

    public void setDifficoltaItinerario(DifficoltaItinerario difficoltaItinerario) {
        this.difficoltaItinerario = difficoltaItinerario;
    }

    public LocalDateTime getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(LocalDateTime dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public boolean getHasPercorso() {
        return hasPercorso;
    }

    public void setHasPercorso(boolean hasPercorso) {
        this.hasPercorso = hasPercorso;
    }

    public List<PuntoItinerario> getPercorso() {
        return percorso;
    }

    public void setPercorso(List<PuntoItinerario> percorso) {
        this.percorso = percorso;
    }

    @Override
    public String toString() {
        return "Itinerario{" +
                "idUtenteCreatore='" + idUtenteCreatore + '\'' +
                ", id='" + id + '\'' +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", durataInMinuti=" + durataInMinuti +
                ", difficoltaItinerario=" + difficoltaItinerario +
                ", dataInserimento=" + dataInserimento +
                ", percorso=" + percorso +
                '}';
    }
}
