package it.ingsw.natour21.model.dao.interfaces;

import it.ingsw.natour21.model.callbacks.OnCreateUser;
import it.ingsw.natour21.model.callbacks.OnLoginUser;
import it.ingsw.natour21.model.entities.Utente;

public interface UtenteDAO {

    //public String getIdUtente(String email);
    void getDettagliUtente(String email, OnLoginUser callback);
    void nuovoUtente(Utente utente, OnCreateUser callback);

}
