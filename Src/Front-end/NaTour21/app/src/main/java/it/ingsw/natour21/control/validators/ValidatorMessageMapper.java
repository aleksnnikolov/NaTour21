package it.ingsw.natour21.control.validators;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import it.ingsw.natour21.R;

public class ValidatorMessageMapper {

    private static Map<String, String> mappaMessaggiErrore = null;

    public static String getMessaggioErroreAssociatoSeEsiste(Context context, String messaggio) {
        if (mappaMessaggiErrore == null)
            inizializzaMappa(context);

        return mappaMessaggiErrore.get(messaggio);
    }


    public static void inizializzaMappa(Context context) {
        mappaMessaggiErrore = new HashMap<>();
        // autenticazione
        mappaMessaggiErrore.put("Username already exists in the system.", context.getResources().getString(R.string.avviso_mail_esistente));
        mappaMessaggiErrore.put("The password given is invalid.", context.getResources().getString(R.string.avviso_formato_password_invalido));
        mappaMessaggiErrore.put("formato nome utente invalido", context.getResources().getString(R.string.avviso_formato_nome_utente_invalido));
        mappaMessaggiErrore.put("formato formato password invalido", context.getResources().getString(R.string.avviso_formato_password_invalido));
        mappaMessaggiErrore.put("formato email invalido", context.getResources().getString(R.string.avviso_formato_mail_invalido));
        mappaMessaggiErrore.put("campo non compilato", context.getResources().getString(R.string.avviso_campi_vuoti));

        // inserimento itinerario
        mappaMessaggiErrore.put("campo titolo obbligatorio", context.getResources().getString(R.string.titolo_itinerario_obbligatorio));
        mappaMessaggiErrore.put("esiste già un itinerario con questo nome", context.getResources().getString(R.string.titolo_itinerario_esistente));
        mappaMessaggiErrore.put("percorso non può contenere un solo indirizzo", context.getResources().getString(R.string.percorso_invalido));
    }
}
