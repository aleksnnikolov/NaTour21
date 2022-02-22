package it.ingsw.natour21.control.presenters;

import android.util.Log;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.AuthenticationManager;
import it.ingsw.natour21.control.UtenteCorrenteHolder;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.validators.LoginInputValidator;
import it.ingsw.natour21.control.validators.ValidatorResponse;
import it.ingsw.natour21.model.callbacks.OnCreateUser;
import it.ingsw.natour21.model.callbacks.OnLoginUser;
import it.ingsw.natour21.model.dao.UtenteDAOImpl;
import it.ingsw.natour21.model.dao.interfaces.UtenteDAO;
import it.ingsw.natour21.model.entities.Utente;
import it.ingsw.natour21.ui.fragments.EffettuaAccessoFragment;
import it.ingsw.natour21.ui.fragments.EffettuaAccessoFragmentDirections;

public class EffettuaAccessoPresenter {

    private EffettuaAccessoFragment effettuaAccessoFragment;
    private UtenteDAO utenteDAO;

    public EffettuaAccessoPresenter(EffettuaAccessoFragment effettuaAccessoFragment) {
        this.effettuaAccessoFragment = effettuaAccessoFragment;
        utenteDAO = new UtenteDAOImpl(effettuaAccessoFragment.getContext());
    }

    public void mostraSchermataCreaAccount() {
        NavDirections action = EffettuaAccessoFragmentDirections.actionEffettuaAccessoFragmentToCreaAccountFragment();
        Navigation.findNavController(effettuaAccessoFragment.getView()).navigate(action);
    }

    public void effettuaAccesso(String email, String password) {
        if (Utils.connessioneInternetDisponibile(effettuaAccessoFragment.getActivity())) {
            LoginInputValidator loginInputValidator = new LoginInputValidator(effettuaAccessoFragment);
            ValidatorResponse validatorResponse = loginInputValidator.validaCompilazioneCampi(email, password);

            if (validatorResponse.isInputValido()) {
                AuthenticationManager.getInstance().setEffettuaAccessoPresenter(this);
                AuthenticationManager.getInstance().signInUtente(email, password);
            } else {
                mostraMessaggioErrore(validatorResponse.getMessaggioErrore());
            }
        }
    }

    public void getDatiUtente(String email) {
        utenteDAO.getDettagliUtente(email, new OnLoginUser() {
            @Override
            public void onSuccess(Utente utente) {
                UtenteCorrenteHolder.utente = utente;
                mostraSchermataHome();
            }

            @Override
            public void onFailure() {
                Log.e("UTENTE", "onFailure: errore nel retrieve dei dati");
            }
        });
    }

    public void mostraSchermataHome() {
        effettuaAccessoFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NavDirections action = EffettuaAccessoFragmentDirections.actionEffettuaAccessoFragmentToHomeFragment();
                Navigation.findNavController(effettuaAccessoFragment.getView()).navigate(action);
            }
        });
    }

    public void mostraMessaggioErrore(String messaggio) {
        String messaggioCustom = messaggio;

        if (messaggio.equals("Failed since user is not authorized."))
            messaggioCustom = effettuaAccessoFragment.getString(R.string.avviso_credenziali_errate);

        if (messaggio.equals("User not found in the system."))
            messaggioCustom = effettuaAccessoFragment.getString(R.string.avviso_credenziali_errate);

        effettuaAccessoFragment.mostraLabelErrore(messaggioCustom);
    }

}
