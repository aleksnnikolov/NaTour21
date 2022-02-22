package it.ingsw.natour21.model.entities;

import java.time.LocalDateTime;

public class Utente {

    private String userID;
    private String nomeUtente;
    private String email;
    private String immagineProfilo;
    private LocalDateTime dataCreazioneAccount;

    public Utente() {

    }

    public Utente(String userID, String nomeUtente, String email, String immagineProfilo, LocalDateTime dataCreazioneAccount) {
        this.userID = userID;
        this.nomeUtente = nomeUtente;
        this.email = email;
        this.immagineProfilo = immagineProfilo;
        this.dataCreazioneAccount = dataCreazioneAccount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public LocalDateTime getDataCreazioneAccount() {
        return dataCreazioneAccount;
    }

    public void setDataCreazioneAccount(LocalDateTime dataCreazioneAccount) {
        this.dataCreazioneAccount = dataCreazioneAccount;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "userID='" + userID + '\'' +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", email='" + email + '\'' +
                ", immagineProfilo='" + immagineProfilo + '\'' +
                ", dataCreazioneAccount=" + dataCreazioneAccount +
                '}';
    }
}
