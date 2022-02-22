package it.ingsw.natour21.control;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.ingsw.natour21.control.validators.SignUpInputValidator;
import it.ingsw.natour21.control.validators.ValidatorResponse;

public class ValidaCompilazioneCampiBlackBoxTest {

    private final static String messaggioErroreCompilazioneCampi = "campo non compilato";
    private final static String messaggioErroreFormatoMailInvalido = "formato email invalido";
    private final static String messaggioErroreFormatoPasswordInvalido = "formato password invalido";
    private final static String messaggioErroreFormatoNomeUtenteInvalido = "formato nome utente invalido";
    private final static String messaggioErroreSuccesso = ""; // Quando l'input è validato correttamente

    private SignUpInputValidator validator;

    @BeforeEach
    public void setupValidator() {
        validator = new SignUpInputValidator();
    }

    @Test
    public void validaEmailNull() {
        String email = null;
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreCompilazioneCampi);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaEmailVuota() {
        String email = "";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreCompilazioneCampi);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaEmailSoloChiocciola() {
        String email = "email@email";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoMailInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaEmailSoloPunto() {
        String email = "email.email";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoMailInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaEmailChiocciolaPrecedutaDaPunto() {
        String email = "email.email@com";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoMailInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaEmailChiocciolaImmediatamentePrimaDiPunto() {
        String email = "email@.com";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoMailInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaEmailChiocciolaSeguitaDaPiuCaratteriEPunto() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(true, messaggioErroreSuccesso);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaPasswordNull() {
        String email = "email@email.com";
        String password = null;
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreCompilazioneCampi);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaPasswordVuota() {
        String email = "email@email.com";
        String password = "";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreCompilazioneCampi);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaPasswordMinoreDiLunghezzaConsentita() {
        String email = "email@email.com";
        String password = "Pass";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoPasswordInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaPasswordMaggioreDiLunghezzaConsentita() {
        String email = "email@email.com";
        String password = new String(new char[300]).replace('\0', 'A'); // crea una stringa più lunga del limite di caratteri per la password
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoPasswordInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaPasswordDiLunghezzaConsentita() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(true, messaggioErroreSuccesso);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteNull() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = null;

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreCompilazioneCampi);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteVuoto() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreCompilazioneCampi);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteMinoreDiLunghezzaConsentita() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "Nom";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoNomeUtenteInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteMaggioreDiLunghezzaConsentita() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "NomeUtenteNomeUtenteNomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoNomeUtenteInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteContenteSpazio() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "Nome Utente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoNomeUtenteInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteIniziaConSpazio() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = " NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoNomeUtenteInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteFinisceConSpazio() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "NomeUtente ";

        ValidatorResponse valoreAtteso = new ValidatorResponse(false, messaggioErroreFormatoNomeUtenteInvalido);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

    @Test
    public void validaNomeUtenteDiLunghezzaConsentita() {
        String email = "email@email.com";
        String password = "Password";
        String nomeUtente = "NomeUtente";

        ValidatorResponse valoreAtteso = new ValidatorResponse(true, messaggioErroreSuccesso);
        ValidatorResponse valoreAttuale = validator.validaCompilazioneCampi(email, password, nomeUtente);

        assertEquals(valoreAtteso, valoreAttuale);
    }

}
