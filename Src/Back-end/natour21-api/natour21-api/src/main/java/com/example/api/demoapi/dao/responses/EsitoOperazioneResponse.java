package com.example.api.demoapi.dao.responses;

import lombok.Data;

@Data
public class EsitoOperazioneResponse {

    public final static String ESITO_OK = "OK";
    public final static String ESITO_ERRORE = "ERROR";

    private String esitoOperazione;
    private String messaggioErrore;

    public EsitoOperazioneResponse(String esito) {
        this.esitoOperazione = esito;
    }

    public EsitoOperazioneResponse(String esito, String messaggioErrore) {
        this.esitoOperazione = esito;
        this.messaggioErrore = messaggioErrore;
    }

}
