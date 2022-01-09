package it.ingsw.natour21.control.validators;

import androidx.fragment.app.Fragment;

import it.ingsw.natour21.R;

public class SignUpInputValidator {

    private Fragment fragment;

    public SignUpInputValidator(Fragment fragment) {
        this.fragment = fragment;
    }


    public ValidatorResponse validaCompilazioneCampi(String email, String password, String nomeUtente) {
        boolean esitoValidazione = true;
        String messaggioErrore = "";

        if (ValidationUtils.formatoNomeUtenteInvalido(nomeUtente)) {
            esitoValidazione = false;
            messaggioErrore = fragment.getString(R.string.avviso_formato_nome_utente_invalido);
        }

        if (ValidationUtils.formatoPasswordInvalido(password)) {
            esitoValidazione = false;
            messaggioErrore = fragment.getString(R.string.avviso_formato_password_invalido);
        }

        if (ValidationUtils.formatoMailInvalido(email)) {
            esitoValidazione = false;
            messaggioErrore = fragment.getString(R.string.avviso_formato_mail_invalido);
        }

        if (ValidationUtils.campoVuoto(nomeUtente) ||
                ValidationUtils.campoVuoto(email) ||
                ValidationUtils.campoVuoto(password)) {

            esitoValidazione = false;
            messaggioErrore = fragment.getString(R.string.avviso_campi_vuoti);
        }

        return new ValidatorResponse(esitoValidazione, messaggioErrore);
    }
}
