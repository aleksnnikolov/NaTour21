package com.example.api.demoapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Utente {

    private String userID;
    private String nomeUtente;
    private String email;
    private String immagineProfilo;
    private String providerEsterno;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dataCreazioneAccount;

}
