package it.ingsw.natour21.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import it.ingsw.natour21.R;

public class UscitaNuovoItinerarioDialog extends DialogFragment {

    private Button confermaUscitaButton;
    private Button annullaButton;

    public interface UscitaNuovoItinerarioListener {
        void confermaUscita();
    }
    private UscitaNuovoItinerarioListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_uscita_nuovo_itinerario, null);

        confermaUscitaButton = view.findViewById(R.id.button_conferma_uscita);
        annullaButton = view.findViewById(R.id.button_annulla);

        confermaUscitaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.confermaUscita();
                dismiss();
            }
        });

        annullaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //se i bottoni smettono di funzionare per qualche motivo, appendi .setPositive button sotto
        builder.setView(view);
        return builder.create();
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
            listener = (UscitaNuovoItinerarioListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement VerificaAccountListener");
        }
    }

}