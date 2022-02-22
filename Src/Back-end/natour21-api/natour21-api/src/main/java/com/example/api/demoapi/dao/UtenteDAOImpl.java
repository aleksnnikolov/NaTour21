package com.example.api.demoapi.dao;

import com.example.api.demoapi.dao.interfaces.UtenteDAO;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("utente-mysql")
public class UtenteDAOImpl implements UtenteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*@Override
    public String getIdUtente(String email) {
        String sql = "SELECT user_id FROM utente WHERE email = ?;";

        String idUtente = jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("user_id");
            }
        }, email);

        return idUtente;
    }*/

    @Override
    public Utente getDettagliUtenteByEmail(String email) {
        String sql = "SELECT * FROM utente WHERE email = ?;";

        Utente utente = jdbcTemplate.queryForObject(sql, new RowMapper<Utente>() {
            @Override
            public Utente mapRow(ResultSet resultSet, int i) throws SQLException {
                Utente utenteEstratto = new Utente();
                utenteEstratto.setUserID(resultSet.getString("user_id"));
                utenteEstratto.setNomeUtente(resultSet.getString("nome_utente"));
                utenteEstratto.setEmail(resultSet.getString("email"));
                utenteEstratto.setDataCreazioneAccount(resultSet.getTimestamp("data_creazione_account").toLocalDateTime());
                utenteEstratto.setImmagineProfilo(resultSet.getString("immagine_profilo"));
                return utenteEstratto;
            }
        }, email);

        return utente;
    }

    @Override
    public Utente getDettagliUtenteByID(String id) {
        String sql = "SELECT * FROM utente WHERE user_id = ?;";

        Utente utente = jdbcTemplate.queryForObject(sql, new RowMapper<Utente>() {
            @Override
            public Utente mapRow(ResultSet resultSet, int i) throws SQLException {
                Utente utenteEstratto = new Utente();
                utenteEstratto.setUserID(resultSet.getString("user_id"));
                utenteEstratto.setNomeUtente(resultSet.getString("nome_utente"));
                utenteEstratto.setEmail(resultSet.getString("email"));
                utenteEstratto.setDataCreazioneAccount(resultSet.getTimestamp("data_creazione_account").toLocalDateTime());
                utenteEstratto.setImmagineProfilo(resultSet.getString("immagine_profilo"));
                return utenteEstratto;
            }
        }, id);

        return utente;
    }

    @Override
    public EsitoOperazioneResponse nuovoUtente(Utente utente) {
        String sql = "INSERT INTO utente() VALUES(?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, utente.getUserID(),
                                 utente.getNomeUtente(),
                                 utente.getEmail(),
                                 utente.getDataCreazioneAccount(),
                                 utente.getImmagineProfilo());

        return new EsitoOperazioneResponse("OK");
    }

    @Override
    public Boolean userIdDisponibile(String codiceGenerato) {
        String sql = "SELECT user_id FROM utente WHERE user_id = ?";

        return jdbcTemplate.query(sql, new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                return !rs.isBeforeFirst();
            }
        }, codiceGenerato);
    }
}
