package it.ingsw.natour21.control.presenters;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.AuthenticationManager;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.validators.SignUpInputValidator;
import it.ingsw.natour21.control.validators.ValidatorResponse;
import it.ingsw.natour21.ui.fragments.CreaAccountFragment;
import it.ingsw.natour21.ui.fragments.CreaAccountFragmentDirections;

public class CreaAccountPresenter {

    private CreaAccountFragment creaAccountFragment;

    public CreaAccountPresenter(CreaAccountFragment creaAccountFragment) {
        this.creaAccountFragment = creaAccountFragment;
    }

    public void mostraSchermataEffettuaAccesso() {
        NavDirections action = CreaAccountFragmentDirections.actionCreaAccountFragmentToEffettuaAccessoFragment();
        Navigation.findNavController(creaAccountFragment.getView()).navigate(action);
    }

    public void creaAccount(String email, String nomeUtente, String password) {
        if (Utils.connessioneInternetDisponibile(creaAccountFragment.getActivity())) {
            creaAccountFragment.mostraIndicatoreAttesa();

            SignUpInputValidator signUpInputValidator = new SignUpInputValidator(creaAccountFragment);
            ValidatorResponse validatorResponse = signUpInputValidator.validaCompilazioneCampi(email, password, nomeUtente);

            if (validatorResponse.isInputValido()) {
                AuthenticationManager.getInstance().setCreaAccountPresenter(this);
                AuthenticationManager.getInstance().registraUtente(email, password);
            } else {
                mostraMessaggioErrore(validatorResponse.getMessaggioErrore());
            }

            creaAccountFragment.nascondiIndicatoreAttesa();
        }
    }

    public void mostraVerificaAccountDialog() {
        creaAccountFragment.apriVerificaAccountDialog();
    }

    public void mostraMessaggioErrore(String messaggio) {
        String messaggioCustom = messaggio;

        if (messaggio.equals("Username already exists in the system."))
            messaggioCustom = creaAccountFragment.getString(R.string.avviso_mail_esistente);


        creaAccountFragment.mostraLabelErrore(messaggioCustom);
    }

}
