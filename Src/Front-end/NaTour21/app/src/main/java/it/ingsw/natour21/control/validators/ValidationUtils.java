package it.ingsw.natour21.control.validators;

public class ValidationUtils {

    public static final int LUNGHEZZA_MINIMA_PASSWORD = 8;
    public static final int LUNGHEZZA_MINIMA_NOME_UTENTE = 4;



    public static boolean campoVuoto(String testo) {
        return testo == null || testo.trim().isEmpty();
    }

    public static boolean formatoMailInvalido(String mail) {
        if (mail != null)
            return !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        else
            return true;
    }

    public static boolean formatoPasswordInvalido(String password) {
        return password == null || password.trim().length() < LUNGHEZZA_MINIMA_PASSWORD;
    }

    public static boolean formatoNomeUtenteInvalido(String nomeUtente) {
        return nomeUtente == null || nomeUtente.trim().length() < LUNGHEZZA_MINIMA_NOME_UTENTE;
    }

}
