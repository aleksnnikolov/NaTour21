package it.ingsw.natour21.control.presenters;

import android.util.Log;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.time.LocalDateTime;

import it.ingsw.natour21.control.AuthenticationManager;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.control.validators.SignUpInputValidator;
import it.ingsw.natour21.control.validators.ValidatorMessageMapper;
import it.ingsw.natour21.control.validators.ValidatorResponse;
import it.ingsw.natour21.model.callbacks.OnCreateUser;
import it.ingsw.natour21.model.dao.UtenteDAOImpl;
import it.ingsw.natour21.model.dao.interfaces.UtenteDAO;
import it.ingsw.natour21.model.entities.Utente;
import it.ingsw.natour21.ui.fragments.CreaAccountFragment;
import it.ingsw.natour21.ui.fragments.CreaAccountFragmentDirections;

public class CreaAccountPresenter {

    private CreaAccountFragment creaAccountFragment;
    private UtenteDAO utenteDAO;

    public CreaAccountPresenter(CreaAccountFragment creaAccountFragment) {
        this.creaAccountFragment = creaAccountFragment;
        utenteDAO = new UtenteDAOImpl(creaAccountFragment.getContext());
    }

    public void mostraSchermataEffettuaAccesso() {
        NavDirections action = CreaAccountFragmentDirections.actionCreaAccountFragmentToEffettuaAccessoFragment();
        Navigation.findNavController(creaAccountFragment.getView()).navigate(action);
    }

    /*
    La creazione di un nuovo account avviene in 3 fasi:
        1) Controllo connessione alla rete e validazione dei campi compilati (creaAccount)
        2) Creazione del nuovo utente su Cognito (creaAccountCognito)
        3) Creazione del nuovo utente in DB (terminaCreazioneAccount)
     */
    public void creaAccount(String email, String nomeUtente, String password) {
        if (Utils.connessioneInternetDisponibile(creaAccountFragment.getActivity())) {
            creaAccountFragment.mostraIndicatoreAttesa();

            SignUpInputValidator signUpInputValidator = new SignUpInputValidator();
            ValidatorResponse validatorResponse = signUpInputValidator.validaCompilazioneCampi(email, password, nomeUtente);

            if (validatorResponse.isInputValido()) {
                creaAccountCognito(email, password, nomeUtente);
            } else {
                creaAccountFragment.nascondiIndicatoreAttesa();
                mostraMessaggioErrore(validatorResponse.getMessaggioErrore());
            }
        }
        //TODO: messaggio d'errore per mancanza internet
    }

    public void creaAccountCognito(String email, String password, String nomeUtente) {
        AuthenticationManager.getInstance().setCreaAccountPresenter(this);
        AuthenticationManager.getInstance().registraUtente(email, password, nomeUtente);
    }

    public void terminaCreazioneAccount(String email, String nomeUtente) {
        Utente utente = new Utente();
        utente.setEmail(email);
        utente.setNomeUtente(nomeUtente);
        utente.setDataCreazioneAccount(LocalDateTime.now());

        utenteDAO.nuovoUtente(utente, new OnCreateUser() {
            @Override
            public void onSuccess() {
                creaAccountFragment.nascondiIndicatoreAttesa();
                mostraVerificaAccountDialog();
            }

            @Override
            public void onFailure() {
                creaAccountFragment.nascondiIndicatoreAttesa();
                Log.e("UTENTE", "onFailure: errore nella creazione dell'utente");
            }
        });
    }

    public void mostraVerificaAccountDialog() {
        creaAccountFragment.apriVerificaAccountDialog();
    }

    public void mostraMessaggioErrore(String messaggio) {
        String messaggioCustom = ValidatorMessageMapper.getMessaggioErroreAssociatoSeEsiste(creaAccountFragment.getContext(), messaggio);

        creaAccountFragment.mostraLabelErrore(messaggioCustom != null ? messaggioCustom : messaggio);
        creaAccountFragment.nascondiIndicatoreAttesa();
    }

}
