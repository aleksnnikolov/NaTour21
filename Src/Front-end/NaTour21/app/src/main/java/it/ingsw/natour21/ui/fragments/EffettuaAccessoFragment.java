package it.ingsw.natour21.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.ingsw.natour21.R;
import it.ingsw.natour21.control.presenters.EffettuaAccessoPresenter;

public class EffettuaAccessoFragment extends Fragment {

    private EffettuaAccessoPresenter effettuaAccessoPresenter;
    private View view;

    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView inputInvalidoTextView;
    private Button accediButton;
    private TextView accountMancanteTextView;
    private TextView passwordDimenticataTextView;
    private View snackBarPosition;

    public EffettuaAccessoFragment() {super(R.layout.fragment_effettua_accesso);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        creaPresenter();

        inizializzaViews();
        aggiungiListener();
    }

    private void creaPresenter() {
        effettuaAccessoPresenter = new EffettuaAccessoPresenter(this);
    }

    private void inizializzaViews() {
        emailEditText = view.findViewById(R.id.edit_text_email);
        passwordEditText = view.findViewById(R.id.edit_text_password);
        inputInvalidoTextView = view.findViewById(R.id.text_view_input_invalido);
        accediButton = view.findViewById(R.id.button_accedi);
        accountMancanteTextView = view.findViewById(R.id.text_view_account_mancante);
        passwordDimenticataTextView = view.findViewById(R.id.text_view_password_dimenticata);
        snackBarPosition = view.findViewById(R.id.snackbar_position);
    }

    private void aggiungiListener() {
        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                effettuaAccessoPresenter.effettuaAccesso(email, password);
            }
        });

        accountMancanteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effettuaAccessoPresenter.mostraSchermataCreaAccount();
            }
        });
    }

    public void mostraLabelErrore(String messaggio) {
        getActivity().runOnUiThread(() -> {
            inputInvalidoTextView.setVisibility(View.VISIBLE);
            inputInvalidoTextView.setText(messaggio);
        });
    }
}
