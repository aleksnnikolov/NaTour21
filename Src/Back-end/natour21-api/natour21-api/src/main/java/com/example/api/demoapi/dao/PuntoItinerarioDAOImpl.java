package com.example.api.demoapi.dao;

import com.example.api.demoapi.dao.interfaces.PuntoItinerarioDAO;
import com.example.api.demoapi.dao.responses.EsitoOperazioneResponse;
import com.example.api.demoapi.entity.PuntoItinerario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository("punto-itinerario-mysql")
public class PuntoItinerarioDAOImpl implements PuntoItinerarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<PuntoItinerario> getPercorsoItinerario(String itinerarioID) {
        String sql = " SELECT * FROM punto_itinerario\n" +
                     " WHERE fk_itinerario = ?" +
                     " ORDER BY posizione ASC";

        List<PuntoItinerario> percorso = jdbcTemplate.query(sql, new RowMapper<PuntoItinerario>() {
            @Override
            public PuntoItinerario mapRow(ResultSet rs, int rowNum) throws SQLException {
                PuntoItinerario puntoItinerario = new PuntoItinerario();

                puntoItinerario.setIndirizzo(rs.getString("indirizzo"));
                puntoItinerario.setPosizione(rs.getInt("posizione"));
                puntoItinerario.setLatitudine(rs.getDouble("latitudine"));
                puntoItinerario.setLongitudine(rs.getDouble("longitudine"));
                puntoItinerario.setItinerarioID(rs.getString("fk_itinerario"));

                return puntoItinerario;
            }
        }, itinerarioID);

        return percorso;
    }

    @Override
    public void inserisciPuntoItinerario(PuntoItinerario puntoItinerario) {
        String sql = " INSERT INTO punto_itinerario(indirizzo, posizione, latitudine, longitudine, fk_itinerario)\n" +
                " VALUES(?, ?, ?, ?, ?);";

            jdbcTemplate.update(sql,
                    puntoItinerario.getIndirizzo(),
                    puntoItinerario.getPosizione(),
                    puntoItinerario.getLatitudine(),
                    puntoItinerario.getLongitudine(),
                    puntoItinerario.getItinerarioID()
            );

    }
}
