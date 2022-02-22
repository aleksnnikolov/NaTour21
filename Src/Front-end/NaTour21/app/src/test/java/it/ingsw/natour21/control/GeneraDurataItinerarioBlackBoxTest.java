package it.ingsw.natour21.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GeneraDurataItinerarioBlackBoxTest {

    @Test
    public void generaDurataNegativa() {
        int durataInMinuti = -100;
        String valoreAtteso = " - ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataNulla() {
        int durataInMinuti = 0;
        String valoreAtteso = " - ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnMinutoMenoDiUnOra() {
        int durataInMinuti = 59;
        String valoreAtteso = "59m";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnOraEsatta() {
        int durataInMinuti = 60;
        String valoreAtteso = "1h ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnOraEMezza() {
        int durataInMinuti = 90;
        String valoreAtteso = "1h 30m";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataDueOre() {
        int durataInMinuti = 120;
        String valoreAtteso = "2h ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnMinutoMenoDiUnGiorno() {
        int durataInMinuti = 1439;
        String valoreAtteso = "23h 59m";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnGiorno() {
        int durataInMinuti = 1440;
        String valoreAtteso = "1g ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnGiornoEMezzo() {
        int durataInMinuti = 2160;
        String valoreAtteso = "1g 12h ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataDueGiorni() {
        int durataInMinuti = 2880;
        String valoreAtteso = "2g ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataGiorniEMinutiDiversiDaZero() {
        int durataInMinuti = 2900;
        String valoreAtteso = "2g 20m";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataUnMinutoMenoDiDurataMassima() {
        int durataInMinuti = 14399;
        String valoreAtteso = "9g 23h 59m";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataMassima() {
        int durataInMinuti = 14400;
        String valoreAtteso = "10g ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void generaDurataMaggioreDiDurataMassima() {
        int durataInMinuti = 14401;
        String valoreAtteso = " - ";
        String valoreAttuale = Utils.generaDurataItinerario(durataInMinuti);

        assertEquals(valoreAtteso, valoreAttuale);
    }

}