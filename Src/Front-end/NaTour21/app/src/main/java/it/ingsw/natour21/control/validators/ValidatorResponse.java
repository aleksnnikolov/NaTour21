package it.ingsw.natour21.control.validators;

import java.util.Objects;

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

    public String getMessaggioErrore() {
        return messaggioErrore;
    }

    @Override
    public String toString() {
        return "ValidatorResponse{" +
                "inputValido=" + inputValido +
                ", messaggioErrore='" + messaggioErrore + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidatorResponse that = (ValidatorResponse) o;
        return inputValido == that.inputValido && Objects.equals(messaggioErrore, that.messaggioErrore);
    }
}
