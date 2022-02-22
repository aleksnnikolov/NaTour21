package com.example.api.demoapi.dao;

import com.example.api.demoapi.dao.interfaces.ItinerarioDAO;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.Itinerario;
import com.example.api.demoapi.entity.enums.AmbienteItinerario;
import com.example.api.demoapi.entity.enums.DifficoltaItinerario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository("itinerario-mysql")
public class ItinerarioDAOImpl implements ItinerarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Itinerario> getItinerariPiuRecenti() {
        String sql = " SELECT * FROM itinerario\n" +
                     " ORDER BY data_inserimento DESC\n" +
                     " LIMIT 5;";

        List<Itinerario> itinerari = jdbcTemplate.query(sql, new RowMapper<Itinerario>() {
            @Override
            public Itinerario mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Itinerario itinerarioEstratto = new Itinerario();

                itinerarioEstratto.setItinerarioID(resultSet.getString("itinerario_id"));
                itinerarioEstratto.setTitolo(resultSet.getString("titolo"));
                itinerarioEstratto.setDescrizione(resultSet.getString("descrizione"));
                itinerarioEstratto.setDurata(resultSet.getInt("durata"));
                itinerarioEstratto.setDifficolta(DifficoltaItinerario.valueOf(resultSet.getString("difficolta")));
                itinerarioEstratto.setDataInserimento(resultSet.getTimestamp("data_inserimento").toLocalDateTime());
                itinerarioEstratto.setHasPercorso(decodificaBooleanHasPercorso(resultSet.getInt("has_percorso")));
                itinerarioEstratto.setPercorso(null);
                itinerarioEstratto.setUserID(resultSet.getString("fk_utente"));

                return itinerarioEstratto;
            }
        });

        return itinerari;
    }

    public List<Itinerario> getItinerariUtente(String userID) {
        String sql = " SELECT * FROM itinerario\n" +
                     " INNER JOIN utente ON itinerario.fk_utente = utente.user_id" +
                     " WHERE itinerario.fk_utente = ?" +
                     " ORDER BY data_inserimento DESC;";

        List<Itinerario> itinerari = jdbcTemplate.query(sql, new RowMapper<Itinerario>() {
            @Override
            public Itinerario mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Itinerario itinerarioEstratto = new Itinerario();

                itinerarioEstratto.setItinerarioID(resultSet.getString("itinerario_id"));
                itinerarioEstratto.setTitolo(resultSet.getString("titolo"));
                itinerarioEstratto.setDescrizione(resultSet.getString("descrizione"));
                itinerarioEstratto.setDurata(resultSet.getInt("durata"));
                itinerarioEstratto.setDifficolta(DifficoltaItinerario.valueOf(resultSet.getString("difficolta")));
                itinerarioEstratto.setDataInserimento(resultSet.getTimestamp("data_inserimento").toLocalDateTime());
                itinerarioEstratto.setHasPercorso(decodificaBooleanHasPercorso(resultSet.getInt("has_percorso")));
                itinerarioEstratto.setPercorso(null);
                itinerarioEstratto.setUserID(resultSet.getString("fk_utente"));

                return itinerarioEstratto;
            }
        }, userID);

        return itinerari;
    }

    @Override
    public Itinerario getDettagliItinerario(String id) {
        String sql = " SELECT * FROM itinerario\n" +
                     " WHERE itinerario_id = ?;";

        Itinerario itinerario = jdbcTemplate.queryForObject(sql, new RowMapper<Itinerario>() {
            @Override
            public Itinerario mapRow(ResultSet resultSet, int i) throws SQLException {
                Itinerario itinerarioEstratto = new Itinerario();

                itinerarioEstratto.setItinerarioID(resultSet.getString("itinerario_id"));
                itinerarioEstratto.setTitolo(resultSet.getString("titolo"));
                itinerarioEstratto.setDescrizione(resultSet.getString("descrizione"));
                itinerarioEstratto.setDurata(resultSet.getInt("durata"));
                itinerarioEstratto.setDifficolta(DifficoltaItinerario.valueOf(resultSet.getString("difficolta")));
                itinerarioEstratto.setDataInserimento(resultSet.getTimestamp("data_inserimento").toLocalDateTime());
                itinerarioEstratto.setHasPercorso(decodificaBooleanHasPercorso(resultSet.getInt("has_percorso")));
                itinerarioEstratto.setPercorso(null);
                itinerarioEstratto.setUserID(resultSet.getString("fk_utente"));

                return itinerarioEstratto;
            }
        }, id);

        return itinerario;
    }

    @Override
    public EsitoOperazioneResponse nuovoItinerario(Itinerario itinerario) {
        String sql = " INSERT INTO itinerario(itinerario_id, titolo, descrizione, durata, difficolta, data_inserimento, has_percorso, fk_utente)\n" +
                     " VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            jdbcTemplate.update(sql, itinerario.getItinerarioID(),
                    itinerario.getTitolo(),
                    itinerario.getDescrizione(),
                    itinerario.getDurata(),
                    itinerario.getDifficolta().toString(),
                    itinerario.getDataInserimento(),
                    (itinerario.isHasPercorso()) ? 1 : 0,
                    itinerario.getUserID());
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new EsitoOperazioneResponse("ERROR", "esiste già un itinerario con questo nome");
            }
        }

        return new EsitoOperazioneResponse("OK");
    }

    @Override
    public Boolean itinerarioIdDisponibile(String codiceGenerato) {
        String sql = "SELECT itinerario_id FROM itinerario WHERE itinerario_id = ?";

        return jdbcTemplate.query(sql, new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                return !rs.isBeforeFirst();
            }
        }, codiceGenerato);
    }

    private boolean decodificaBooleanHasPercorso(int intero) {
        return intero == 1;
    }

}
