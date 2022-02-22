package it.ingsw.natour21.control;

import android.content.Context;
import android.net.Uri;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.WayPoint;
import it.ingsw.natour21.model.entities.PuntoItinerario;

public class GPXParserService {

    public List<PuntoItinerario> parseFileGPX(Context context, Uri fileUri) {
        List<PuntoItinerario> percorso = new ArrayList<>();
        InputStream inputStream = getInputStreamByUri(context, fileUri);
        Gpx parsedGpx = getParsedGPX(inputStream);

        if (parsedGpx == null) {
            // error parsing track
            return null;
        } else {
            // do something with the parsed track
            // see included example app and tests
            List<WayPoint> wayPoints = parsedGpx.getWayPoints();
            int contatoreOrdine = 1;

            for (WayPoint p : wayPoints) {
                PuntoItinerario punto = new PuntoItinerario();
                punto.setIndirizzo(p.getName());
                punto.setPosizione(contatoreOrdine);
                punto.setLatitudine(p.getLatitude());
                punto.setLongitudine(p.getLongitude());
                percorso.add(punto);

                contatoreOrdine++;
            }
        }

        return percorso;
    }

    private Gpx getParsedGPX(InputStream in) {
        GPXParser parser = new GPXParser();
        Gpx parsedGpx = null;
        try {
            parsedGpx = parser.parse(in); //TODO: consider using a background thread
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return parsedGpx;
    }

    private InputStream getInputStreamByUri(Context context, Uri uri) {
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
