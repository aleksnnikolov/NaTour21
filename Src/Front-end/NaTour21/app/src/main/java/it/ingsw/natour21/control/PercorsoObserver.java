package it.ingsw.natour21.control;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.ingsw.natour21.model.entities.PuntoItinerario;

public class PercorsoObserver extends Observable {

    private static final int MAX_PUNTI_ITINERARIO = 2;

    private List<Observer> observables;

    private List<PuntoItinerario> percorso;

    public PercorsoObserver() {
        observables = new ArrayList<>();
        percorso = Arrays.asList(new PuntoItinerario[MAX_PUNTI_ITINERARIO]);
    }

    public void aggiungiPuntoInPosizione(PuntoItinerario puntoItinerario) {
        int posizioneDaInserire = puntoItinerario.getPosizione() - 1;
        percorso.set(posizioneDaInserire, puntoItinerario);

        notifyObservers();
    }

    public void rimuoviPuntoInPosizione(int posizione) {
        int posizioneDaRimuovere = posizione - 1;
        percorso.set(posizioneDaRimuovere, null);

        notifyObservers();
    }

    public void invertiPersorso() {
        Collections.reverse(percorso);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();

        for (Observer o : observables) {
            o.update(this, percorso);
        }

        Log.i("OBSERVER", "\npartenza:    " + percorso.get(0) + "\ndestinazione: " + percorso.get(1));
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);

        if (!observables.contains(o))
            observables.add(o);
    }

    public List<PuntoItinerario> getPercorso() {
        return percorso;
    }

    public void setPercorso(List<PuntoItinerario> percorso) {
        if (percorso != null) {
            this.percorso = percorso;
            notifyObservers();
        }
    }
}
