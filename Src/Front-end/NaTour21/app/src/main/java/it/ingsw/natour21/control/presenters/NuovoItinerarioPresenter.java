package it.ingsw.natour21.control.presenters;

import android.net.Uri;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import java.util.List;
import it.ingsw.natour21.MainActivity;
import it.ingsw.natour21.control.GPXParserService;
import it.ingsw.natour21.control.validators.NuovoItinerarioValidator;
import it.ingsw.natour21.control.validators.ValidatorMessageMapper;
import it.ingsw.natour21.control.validators.ValidatorResponseItinerario;
import it.ingsw.natour21.model.cache.NuovoItinerarioCache;
import it.ingsw.natour21.model.callbacks.OnCreateItinerario;
import it.ingsw.natour21.model.dao.ItinerarioDAOImpl;
import it.ingsw.natour21.model.dao.interfaces.ItinerarioDAO;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.ui.fragments.NuovoItinerarioFragment;
import it.ingsw.natour21.ui.fragments.NuovoItinerarioFragmentDirections;

public class NuovoItinerarioPresenter {

    private NuovoItinerarioFragment nuovoItinerarioFragment;
    private ItinerarioDAO itinerarioDAO;

    public NuovoItinerarioPresenter(NuovoItinerarioFragment nuovoItinerarioFragment) {
        this.nuovoItinerarioFragment = nuovoItinerarioFragment;
        itinerarioDAO = new ItinerarioDAOImpl(nuovoItinerarioFragment.getContext());
    }


    public void tornaInSchermataPrecedente() {
        svuotaCacheItinerario();
        ((MainActivity) nuovoItinerarioFragment.getActivity()).onSupportNavigateUp();
    }

    public void mostraSchermataMappa() {
        NavDirections action = NuovoItinerarioFragmentDirections.actionNuovoItinerarioFragmentToNuovoItinerarioMappaFragment();
        Navigation.findNavController(nuovoItinerarioFragment.getView()).navigate(action);
    }


    public List<PuntoItinerario> getPercorsoInCache() {
        List<PuntoItinerario> percorso = NuovoItinerarioCache.recuperaPercorso();
        return percorso;
    }

    public void svuotaCacheItinerario() {
        NuovoItinerarioCache.svuotaCache();
    }


    public boolean itinerarioHaPercorso() {
        Itinerario itinerarioInCache = NuovoItinerarioCache.recuperaItinerario();
        return itinerarioInCache.itinerarioHaPercorsoVisualizzabile();
    }

    public List<String> getIndirizziPercorso() {
        Itinerario itinerarioInCache = NuovoItinerarioCache.recuperaItinerario();
        return itinerarioInCache.getIndirizziPercorso();
    }


    public void creaNuovoItinerario(Itinerario itinerario) {

        NuovoItinerarioValidator validator = new NuovoItinerarioValidator();
        ValidatorResponseItinerario validatorResponse = (ValidatorResponseItinerario) validator.validaCompilazioneCampi(itinerario.getTitolo(), itinerario.getPercorso());

        if (validatorResponse.isInputValido()) {
            // effettua operazione di validazione dei dati inseriti e che il percorso sia valido
            itinerarioDAO.nuovoItinerario(itinerario, new OnCreateItinerario() {
                @Override
                public void onSuccess() {
                    nuovoItinerarioFragment.confermaUscita();
                }

                @Override
                public void onFailure(String messaggioErrore) {
                    String messaggio = ValidatorMessageMapper.getMessaggioErroreAssociatoSeEsiste(nuovoItinerarioFragment.getContext(),messaggioErrore);
                    if (messaggio != null) {
                        nuovoItinerarioFragment.mostraLabelErrore("titolo", messaggio);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        } else {
            String messaggio = ValidatorMessageMapper.getMessaggioErroreAssociatoSeEsiste(nuovoItinerarioFragment.getContext(), validatorResponse.getMessaggioErrore());
            nuovoItinerarioFragment.mostraLabelErrore(validatorResponse.getLabelErrore(), messaggio);
        }
    }


    public void apriStorageDispositivo() {
        ((MainActivity) nuovoItinerarioFragment.getActivity()).openFile();
    }

    public void parseFileGPX(Uri fileUri) {
        GPXParserService gpxParserService = new GPXParserService();
        List<PuntoItinerario> percorsoGPX = gpxParserService.parseFileGPX(nuovoItinerarioFragment.getContext(), fileUri);

        NuovoItinerarioCache.salvaPercorso(percorsoGPX);
        nuovoItinerarioFragment.mostraPercorso();
    }
}
