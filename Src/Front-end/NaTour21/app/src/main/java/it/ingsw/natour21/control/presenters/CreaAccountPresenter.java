package it.ingsw.natour21.control.presenters;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import it.ingsw.natour21.ui.fragments.CreaAccountFragment;
import it.ingsw.natour21.ui.fragments.CreaAccountFragmentDirections;

public class CreaAccountPresenter {

    private CreaAccountFragment creaAccountFragment;

    public CreaAccountPresenter(CreaAccountFragment creaAccountFragment) {
        this.creaAccountFragment = creaAccountFragment;
    }

    public void mostraSchermataEffettuaAccesso() {
        NavDirections action = CreaAccountFragmentDirections.actionCreaAccountFragmentToEffettuaAccessoFragment();
        Navigation.findNavController(creaAccountFragment.getView()).navigate(action);
    }

    public void creaAccount() {

    }

}
