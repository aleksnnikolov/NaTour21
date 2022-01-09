package it.ingsw.natour21.control.validators;

public class ValidatorResponse {

    private boolean inputValido;
    private String messaggioErrore;

    public ValidatorResponse(boolean inputValido, String messaggioErrore) {
        this.inputValido = inputValido;
        this.messaggioErrore = messaggioErrore;
    }

    public boolean isInputValido() {
        return inputValido;
    }

    public void setInputValido(boolean inputValido) {
        this.inputValido = inputValido;
    }

    public String getMessaggioErrore() {
        return messaggioErrore;
    }

    public void setMessaggioErrore(String messaggioErrore) {
        this.messaggioErrore = messaggioErrore;
    }
}
