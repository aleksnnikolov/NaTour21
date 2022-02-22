package it.ingsw.natour21.control;

import static it.ingsw.natour21.model.utils.Constants.MAX_DURATA_ITINERARIO;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.ingsw.natour21.model.enums.DifficoltaItinerario;

public class Utils {

    public static final DateTimeFormatter FORMATO_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String[] giorniNumberPicker;
    public static final String[] oreNumberPicker;
    public static final String[] minutiNumberPicker;

    //Utilizzato nella mappa
    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    static {
        giorniNumberPicker = generaArrayDiStringheNumerateOdinate(0, 10);
        oreNumberPicker = generaArrayDiStringheNumerateOdinate(0, 23);
        minutiNumberPicker = generaArrayDiStringheNumerateOdinate(0, 59);
    }

    public static String[] generaArrayDiStringheNumerateOdinate(int min, int max) {
        String[] risultato = new String[max - min + 1];

        int contatore = 0;
        for (int i = min; i <= max; i++, contatore++) {
            String stringa = String.format("%02d", i);
            risultato[contatore] = stringa;
        }

        return risultato;
    }

    public static List<String> getDifficoltaItinerario() {
        return Stream.of(DifficoltaItinerario.values())
                .map(DifficoltaItinerario::name)
                .collect(Collectors.toList());
    }

    public static boolean connessioneInternetDisponibile(FragmentActivity fragmentActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) fragmentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String generaDurataItinerario(int durataInMinuti) {
        if (durataInMinuti > 0 && durataInMinuti <= MAX_DURATA_ITINERARIO) {
            int giorni = durataInMinuti / 1440;
            int ore = (durataInMinuti % 1440) / 60;
            int minuti = (durataInMinuti % 1440) % 60;

            String giorniString = (giorni != 0) ? giorni + "g " : "";
            String oreString = ((ore != 0) || (giorni == 0 && minuti == 0)) ? ore + "h " : "";
            String minutiString = (minuti != 0) ? minuti + "m" : "";

            return giorniString + oreString + minutiString;
        } else {
            return " - ";
        }
    }

    public static String generaIntervalloTemporale(LocalDateTime dateTimeInserimento) {
        LocalDateTime now = LocalDateTime.now();
        long giorni = Duration.between(dateTimeInserimento, now).toDays();

        if (giorni == 0) {
            return "oggi";
        } else if (giorni > 0 && giorni <= 7) {
            if (giorni == 1)
                return giorni + "giorno fa";
            return giorni + " giorni fa";
        } else if (giorni > 7 && giorni <= 30) {

            long settimane = giorni % 7;
            if (settimane == 1)
                return settimane + "settimana fa";

            return settimane + " settimane fa";

        } else if (giorni > 30) {

            long mesi = giorni % 30;
            if (mesi == 1)
                return mesi + " mese fa";

            return mesi + " mesi fa";

        } else
            return "";
    }
}
