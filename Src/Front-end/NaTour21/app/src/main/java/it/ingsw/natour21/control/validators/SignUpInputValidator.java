package it.ingsw.natour21.control.validators;

import android.content.Context;

import androidx.fragment.app.Fragment;

import it.ingsw.natour21.R;

public class SignUpInputValidator {

    public ValidatorResponse validaCompilazioneCampi(String email, String password, String nomeUtente) {
        boolean esitoValidazione = true;
        String messaggioErrore = "";

        if (ValidationRules.formatoNomeUtenteInvalido(nomeUtente)) {
            esitoValidazione = false;
            messaggioErrore = "formato nome utente invalido";
        }

        if (ValidationRules.formatoPasswordInvalido(password)) {
            esitoValidazione = false;
            messaggioErrore = "formato password invalido";
        }

        if (ValidationRules.formatoMailInvalido(email)) {
            esitoValidazione = false;
            messaggioErrore = "formato email invalido";
        }

        if (ValidationRules.campoVuoto(nomeUtente) ||
                ValidationRules.campoVuoto(email) ||
                ValidationRules.campoVuoto(password)) {

            esitoValidazione = false;
            messaggioErrore = "campo non compilato";
        }

        return new ValidatorResponse(esitoValidazione, messaggioErrore);
    }
}
