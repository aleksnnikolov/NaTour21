package it.ingsw.natour21.control.presenters;

import java.util.List;

import it.ingsw.natour21.MainActivity;
import it.ingsw.natour21.model.cache.NuovoItinerarioCache;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.ui.fragments.NuovoItinerarioMappaFragment;

public class NuovoItinerarioMappaPresenter {

    private NuovoItinerarioMappaFragment nuovoItinerarioMappaFragment;

    public NuovoItinerarioMappaPresenter(NuovoItinerarioMappaFragment fragment) {
        this.nuovoItinerarioMappaFragment = fragment;
    }

    public void salvaPercorso() {
        ((MainActivity) nuovoItinerarioMappaFragment.getActivity()).onSupportNavigateUp();
    }

    public void salvaPuntiItinerarioInCache(List<PuntoItinerario> puntiItinerario) {
        NuovoItinerarioCache.salvaPercorso(puntiItinerario);
    }

    public List<PuntoItinerario> recuperaPuntiItinerarioDaCache() {
        return NuovoItinerarioCache.recuperaPercorso();
    }

}
