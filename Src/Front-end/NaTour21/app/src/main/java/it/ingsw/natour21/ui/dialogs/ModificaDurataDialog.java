package it.ingsw.natour21.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.Utils;

public class ModificaDurataDialog extends DialogFragment {

    private int giornoPreselezionato;
    private int oraPreselezionata;
    private int minutoPreselezionato;

    private NumberPicker numberPickerGiorni;
    private NumberPicker numberPickerOre;
    private NumberPicker numberPickerMinuti;

    private Button confermaDurataButton;

    public interface ModificaDialogListener {
        void impostaDurataItinerario(int giorni, int ore, int minuti);
    }
    private ModificaDialogListener listener;

    public ModificaDurataDialog(int durataInMinuti) {
        giornoPreselezionato = durataInMinuti / 1440;
        oraPreselezionata = (durataInMinuti % 1440) / 60;
        minutoPreselezionato = (durataInMinuti % 1440) % 60;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_modifica_durata, null);

        inizializzaViews(view);

        confermaDurataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durataGiorni = numberPickerGiorni.getValue();
                int durataOre = numberPickerOre.getValue();
                int durataMinuti = numberPickerMinuti.getValue();

                listener.impostaDurataItinerario(durataGiorni, durataOre, durataMinuti);
                dismiss();
            }
        });

        //se i bottoni smettono di funzionare per qualche motivo, appendi .setPositive button sotto
        builder.setView(view);
        return builder.create();
    }

    private void inizializzaViews(View view) {
        numberPickerGiorni = view.findViewById(R.id.number_picker_giorni);
        numberPickerOre = view.findViewById(R.id.number_picker_ore);
        numberPickerMinuti = view.findViewById(R.id.number_picker_minuti);
        confermaDurataButton = view.findViewById(R.id.button_conferma_durata);

        numberPickerGiorni.setWrapSelectorWheel(true);
        numberPickerGiorni.setDisplayedValues(Utils.giorniNumberPicker);
        numberPickerGiorni.setMinValue(0);
        numberPickerGiorni.setMaxValue(Utils.giorniNumberPicker.length - 1);
        numberPickerGiorni.setValue(giornoPreselezionato);

        numberPickerOre.setWrapSelectorWheel(true);
        numberPickerOre.setDisplayedValues(Utils.oreNumberPicker);
        numberPickerOre.setMinValue(0);
        numberPickerOre.setMaxValue(Utils.oreNumberPicker.length - 1);
        numberPickerOre.setValue(oraPreselezionata);

        numberPickerMinuti.setWrapSelectorWheel(true);
        numberPickerMinuti.setDisplayedValues(Utils.minutiNumberPicker);
        numberPickerMinuti.setMinValue(0);
        numberPickerMinuti.setMaxValue(Utils.minutiNumberPicker.length - 1);
        numberPickerMinuti.setValue(minutoPreselezionato);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Imposta il fragment chiamante della dialog come suo "parente"
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //getParentFragmentManager().setFragmentResult("VERIFICA CODICE", new Bundle());
        try {
            listener = (ModificaDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement VerificaAccountListener");
        }
    }

}