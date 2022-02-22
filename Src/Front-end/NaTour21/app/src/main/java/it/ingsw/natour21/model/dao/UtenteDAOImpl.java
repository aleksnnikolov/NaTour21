package it.ingsw.natour21.model.dao;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.model.callbacks.OnCreateUser;
import it.ingsw.natour21.model.callbacks.OnLoginUser;
import it.ingsw.natour21.model.dao.interfaces.UtenteDAO;
import it.ingsw.natour21.model.entities.Utente;
import it.ingsw.natour21.model.utils.Constants;
import it.ingsw.natour21.model.utils.RequestQueueSingleton;

public class UtenteDAOImpl implements UtenteDAO {

    private Context context;

    public UtenteDAOImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getDettagliUtente(String email, final OnLoginUser callback) {
        String url = Constants.URL_BASE_BACKEND + "/natour/user/data?email=" + email;

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                postData,
                response -> {
                    Utente utente = new Utente();

                    try {
                        utente.setUserID(response.getString("userID"));
                        utente.setNomeUtente(response.getString("nomeUtente"));
                        utente.setEmail(response.getString("email"));
                        utente.setImmagineProfilo(response.getString("immagineProfilo"));
                        utente.setDataCreazioneAccount(LocalDateTime.parse(response.getString("dataCreazioneAccount"), Utils.FORMATO_DATETIME));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.onSuccess(utente);
                },
                error -> {
                    callback.onFailure();
                    error.printStackTrace();
                });

        RequestQueueSingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void nuovoUtente(Utente utente, final OnCreateUser callback) {
        String url = "http://192.168.1.14:8080/natour/user/";

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", utente.getEmail());
            postData.put("nomeUtente", utente.getNomeUtente());
            postData.put("dataCreazioneAccount", utente.getDataCreazioneAccount().format(Utils.FORMATO_DATETIME));
            postData.put("immagineProfilo", ""); //TODO: inserire link per immagine mancante
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                                                            url,
                                                            postData,
                                                            response -> callback.onSuccess(),
                                                            error -> {
                                                                callback.onFailure();
                                                                error.printStackTrace();
                                                            });

        //add the request above to the request queue singleton
        RequestQueueSingleton.getInstance(context).getRequestQueue().add(request);
    }

}
