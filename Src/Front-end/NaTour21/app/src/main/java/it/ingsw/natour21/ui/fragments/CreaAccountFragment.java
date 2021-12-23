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
import it.ingsw.natour21.control.presenters.CreaAccountPresenter;

public class CreaAccountFragment extends Fragment {

    private CreaAccountPresenter creaAccountPresenter;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nomeUtenteEditText;

    private TextView inputInvalidoTextView;
    private Button creaAccountButton;
    private TextView accountEsistenteTextView;

    private View indicatoreDiAttesa;
    private View snackBarPosition;

    public CreaAccountFragment() {super(R.layout.fragment_crea_account);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        creaPresenter();

        inizializzaViews(view);
        aggiungiListener(view);
    }

    private void creaPresenter() {
        creaAccountPresenter = new CreaAccountPresenter(this);
    }

    private void inizializzaViews(View view) {
        emailEditText = view.findViewById(R.id.edit_text_email);
        passwordEditText = view.findViewById(R.id.edit_text_password);
        nomeUtenteEditText = view.findViewById(R.id.edit_text_nome_utente);
        inputInvalidoTextView = view.findViewById(R.id.text_view_input_invalido);
        creaAccountButton = view.findViewById(R.id.button_crea_account);
        accountEsistenteTextView = view.findViewById(R.id.text_view_account_esistente);
        snackBarPosition = view.findViewById(R.id.snackbar_position);
        indicatoreDiAttesa = view.findViewById(R.id.loading_anim);
    }

    private void aggiungiListener(View view) {
        creaAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaAccountPresenter.creaAccount();
            }
        });

        accountEsistenteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaAccountPresenter.mostraSchermataEffettuaAccesso();
            }
        });
    }

}
