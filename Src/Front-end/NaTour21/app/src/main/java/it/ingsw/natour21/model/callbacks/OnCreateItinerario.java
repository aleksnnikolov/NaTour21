package it.ingsw.natour21.model.callbacks;

public interface OnCreateItinerario {
    void onSuccess();
    void onFailure(String messaggioErrore);
    void onFailure();
}
