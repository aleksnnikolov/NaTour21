package it.ingsw.natour21.model.callbacks;

import it.ingsw.natour21.model.entities.Utente;

public interface OnLoginUser {
    void onSuccess(Utente utente);
    void onFailure();
}
