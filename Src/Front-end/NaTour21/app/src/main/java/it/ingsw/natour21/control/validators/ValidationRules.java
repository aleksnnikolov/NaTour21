package it.ingsw.natour21.control.validators;

import androidx.core.util.PatternsCompat;

public class ValidationRules {

    private static final int LUNGHEZZA_MINIMA_PASSWORD = 8;
    private static final int LUNGHEZZA_MASSIMA_PASSWORD = 256;

    private static final int LUNGHEZZA_MINIMA_NOME_UTENTE = 4;
    private static final int LUNGHEZZA_MASSIMA_NOME_UTENTE = 25;

    public static boolean campoVuoto(String testo) {
        return testo == null || testo.trim().isEmpty();
    }

    public static boolean formatoMailInvalido(String mail) {
        if (mail != null)
            return !PatternsCompat.EMAIL_ADDRESS.matcher(mail).matches();
        else
            return true;
    }

    public static boolean formatoPasswordInvalido(String password) {
        return  password == null ||
                password.trim().length() < LUNGHEZZA_MINIMA_PASSWORD ||
                password.trim().length() > LUNGHEZZA_MASSIMA_PASSWORD;
    }

    public static boolean formatoNomeUtenteInvalido(String nomeUtente) {
        return  nomeUtente == null ||
                nomeUtente.trim().length() < LUNGHEZZA_MINIMA_NOME_UTENTE ||
                nomeUtente.trim().length() > LUNGHEZZA_MASSIMA_NOME_UTENTE ||
                nomeUtente.contains(" ");
    }

}
