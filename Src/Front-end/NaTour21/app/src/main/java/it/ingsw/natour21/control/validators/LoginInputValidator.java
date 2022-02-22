package it.ingsw.natour21.control.validators;

import androidx.fragment.app.Fragment;

import it.ingsw.natour21.R;

public class LoginInputValidator {
    private Fragment fragment;

    public LoginInputValidator(Fragment fragment) {
        this.fragment = fragment;
    }

    public ValidatorResponse validaCompilazioneCampi(String email, String password) {
        boolean esitoValidazione = true;
        String messaggioErrore = "";

        if (ValidationRules.campoVuoto(email) ||
                ValidationRules.campoVuoto(password)) {

            esitoValidazione = false;
            messaggioErrore = fragment.getString(R.string.avviso_campi_vuoti);
        }

        return new ValidatorResponse(esitoValidazione, messaggioErrore);
    }
}
