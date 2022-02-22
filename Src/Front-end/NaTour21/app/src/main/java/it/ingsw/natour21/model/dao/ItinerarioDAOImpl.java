package it.ingsw.natour21.model.dao;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.model.callbacks.OnCreateItinerario;
import it.ingsw.natour21.model.callbacks.OnGetItinerari;
import it.ingsw.natour21.model.callbacks.OnGetItinerario;
import it.ingsw.natour21.model.dao.interfaces.ItinerarioDAO;
import it.ingsw.natour21.model.entities.DettagliItinerario;
import it.ingsw.natour21.model.entities.Itinerario;
import it.ingsw.natour21.model.entities.PuntoItinerario;
import it.ingsw.natour21.model.entities.Utente;
import it.ingsw.natour21.model.enums.DifficoltaItinerario;
import it.ingsw.natour21.model.utils.Constants;
import it.ingsw.natour21.model.utils.RequestQueueSingleton;

public class ItinerarioDAOImpl implements ItinerarioDAO {

    private Context context;

    public ItinerarioDAOImpl(Context context) {
        this.context = context;
    }

    @Override
    public void nuovoItinerario(Itinerario itinerario, OnCreateItinerario callback) {
        String url = Constants.URL_BASE_BACKEND + "/natour/route/";

        JSONObject postData = new JSONObject();
        try {
            postData.put("userID", itinerario.getIdUtenteCreatore());
            postData.put("itinerarioID", itinerario.getId());
            postData.put("titolo", itinerario.getTitolo());
            postData.put("descrizione", itinerario.getDescrizione());
            postData.put("durata", itinerario.getDurataInMinuti());
            postData.put("difficolta", itinerario.getDifficoltaItinerario().toString());
            postData.put("dataInserimento", itinerario.getDataInserimento().format(Utils.FORMATO_DATETIME));
            postData.put("hasPercorso", itinerario.getHasPercorso());

            JSONArray percorso = getElementoPercorso(itinerario);
            postData.put("percorso", percorso);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url,
                postData,
                response -> {
                    try {
                        if (response.getString("esitoOperazione").equals("ERROR")) {
                            String errore = response.getString("messaggioErrore");
                            callback.onFailure(errore);
                        } else
                            callback.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    callback.onFailure();
                    error.printStackTrace();
                });

        //add the request above to the request queue singleton
        RequestQueueSingleton.getInstance(context).getRequestQueue().add(request);
    }

    private JSONArray getElementoPercorso(Itinerario itinerario) throws JSONException {
        JSONArray percorso = new JSONArray();

        if (itinerario.getHasPercorso()) {
            for (PuntoItinerario puntoItinerario : itinerario.getPercorso()) {
                JSONObject punto = new JSONObject();
                punto.put("indirizzo", puntoItinerario.getIndirizzo());
                punto.put("posizione", puntoItinerario.getPosizione());
                punto.put("latitudine", puntoItinerario.getLatitudine());
                punto.put("longitudine", puntoItinerario.getLongitudine());
                percorso.put(punto);
            }
        }

        return percorso;
    }

    @Override
    public void getDettagliItinerario(String itinerarioID, OnGetItinerario callback) {
        String url = Constants.URL_BASE_BACKEND + "/natour/route/" + itinerarioID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {
                    Itinerario itinerario = new Itinerario();
                    Utente utente = new Utente();

                    try {
                        JSONObject itinerarioJSON = response.getJSONObject("itinerario");
                        itinerario.setIdUtenteCreatore(itinerarioJSON.getString("userID"));
                        itinerario.setId(itinerarioJSON.getString("itinerarioID"));
                        itinerario.setTitolo(itinerarioJSON.getString("titolo"));
                        itinerario.setDescrizione(itinerarioJSON.getString("descrizione"));
                        itinerario.setDurataInMinuti(itinerarioJSON.getInt("durata"));
                        itinerario.setDifficoltaItinerario(DifficoltaItinerario.valueOf(itinerarioJSON.getString("difficolta")));
                        itinerario.setDataInserimento(LocalDateTime.parse(itinerarioJSON.getString("dataInserimento"), Utils.FORMATO_DATETIME));
                        itinerario.setHasPercorso(itinerarioJSON.getBoolean("hasPercorso"));

                        JSONArray percorsoJSON = itinerarioJSON.getJSONArray("percorso");
                        List<PuntoItinerario> percorso = new ArrayList<>();
                        for (int i = 0; i < percorsoJSON.length(); i++) {
                            JSONObject puntoJSON = percorsoJSON.getJSONObject(i);
                            PuntoItinerario punto = new PuntoItinerario();
                            punto.setIndirizzo(puntoJSON.getString("indirizzo"));
                            punto.setPosizione(puntoJSON.getInt("posizione"));
                            punto.setLatitudine(puntoJSON.getDouble("latitudine"));
                            punto.setLongitudine(puntoJSON.getDouble("longitudine"));
                            percorso.add(punto);
                        }
                        itinerario.setPercorso(percorso);

                        JSONObject utenteJSON = response.getJSONObject("utente");
                        utente.setUserID(utenteJSON.getString("userID"));
                        utente.setNomeUtente(utenteJSON.getString("nomeUtente"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    DettagliItinerario dettagliItinerario = new DettagliItinerario();
                    dettagliItinerario.setItinerario(itinerario);
                    dettagliItinerario.setUtente(utente);
                    callback.onSuccess(dettagliItinerario);
                },
                error -> {
                    callback.onFailure();
                    error.printStackTrace();
                });

        RequestQueueSingleton.getInstance(context).getRequestQueue().add(request);

    }

    @Override
    public void getItinerariRecenti(OnGetItinerari callback) {
        String url = Constants.URL_BASE_BACKEND + "/natour/route/recent/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {
                    List<Itinerario> itinerariRecenti = new ArrayList<>();

                    try {
                        JSONArray itinerariJSON = response.getJSONArray("itinerari");

                        for (int i = 0; i < itinerariJSON.length(); i++) {
                            JSONObject itinerarioJSON = itinerariJSON.getJSONObject(i);
                            Itinerario itinerario = new Itinerario();

                            itinerario.setIdUtenteCreatore(itinerarioJSON.getString("userID"));
                            itinerario.setId(itinerarioJSON.getString("itinerarioID"));
                            itinerario.setTitolo(itinerarioJSON.getString("titolo"));
                            itinerario.setDescrizione(itinerarioJSON.getString("descrizione"));
                            itinerario.setDurataInMinuti(itinerarioJSON.getInt("durata"));
                            itinerario.setDifficoltaItinerario(DifficoltaItinerario.valueOf(itinerarioJSON.getString("difficolta")));
                            itinerario.setDataInserimento(LocalDateTime.parse(itinerarioJSON.getString("dataInserimento"), Utils.FORMATO_DATETIME));
                            itinerario.setHasPercorso(itinerarioJSON.getBoolean("hasPercorso"));

                            itinerariRecenti.add(itinerario);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.onSuccess(itinerariRecenti);
                },
                error -> {
                    callback.onFailure();
                    error.printStackTrace();
                });

        RequestQueueSingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void getItinerariUtente(String utenteID, OnGetItinerari callback) {
        String url = Constants.URL_BASE_BACKEND + "/natour/route/user/" + utenteID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {
                    List<Itinerario> itinerariRecenti = new ArrayList<>();

                    try {
                        JSONArray itinerariJSON = response.getJSONArray("itinerari");

                        for (int i = 0; i < itinerariJSON.length(); i++) {
                            JSONObject itinerarioJSON = itinerariJSON.getJSONObject(i);
                            Itinerario itinerario = new Itinerario();

                            itinerario.setIdUtenteCreatore(itinerarioJSON.getString("userID"));
                            itinerario.setId(itinerarioJSON.getString("itinerarioID"));
                            itinerario.setTitolo(itinerarioJSON.getString("titolo"));
                            itinerario.setDescrizione(itinerarioJSON.getString("descrizione"));
                            itinerario.setDurataInMinuti(itinerarioJSON.getInt("durata"));
                            itinerario.setDifficoltaItinerario(DifficoltaItinerario.valueOf(itinerarioJSON.getString("difficolta")));
                            itinerario.setDataInserimento(LocalDateTime.parse(itinerarioJSON.getString("dataInserimento"), Utils.FORMATO_DATETIME));
                            itinerario.setHasPercorso(itinerarioJSON.getBoolean("hasPercorso"));

                            itinerariRecenti.add(itinerario);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.onSuccess(itinerariRecenti);
                },
                error -> {
                    callback.onFailure();
                    error.printStackTrace();
                });

        RequestQueueSingleton.getInstance(context).getRequestQueue().add(request);
    }
}
