package it.ingsw.natour21.control.validators;

public class ValidatorResponseItinerario extends ValidatorResponse{

    private String labelErrore;

    public ValidatorResponseItinerario(boolean inputValido, String messaggioErrore, String labelErrore) {
        super(inputValido, messaggioErrore);
        this.labelErrore = labelErrore;
    }

    public String getLabelErrore() {
        return labelErrore;
    }

}
