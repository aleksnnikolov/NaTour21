package it.ingsw.natour21.control.presenters;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.AuthenticationManager;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.validators.LoginInputValidator;
import it.ingsw.natour21.control.validators.ValidatorResponse;
import it.ingsw.natour21.ui.fragments.EffettuaAccessoFragment;
import it.ingsw.natour21.ui.fragments.EffettuaAccessoFragmentDirections;

public class EffettuaAccessoPresenter {

    private EffettuaAccessoFragment effettuaAccessoFragment;

    public EffettuaAccessoPresenter(EffettuaAccessoFragment effettuaAccessoFragment) {
        this.effettuaAccessoFragment = effettuaAccessoFragment;
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
