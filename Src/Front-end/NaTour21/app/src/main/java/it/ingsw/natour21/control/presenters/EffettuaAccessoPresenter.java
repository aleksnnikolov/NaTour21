package it.ingsw.natour21.control.presenters;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import it.ingsw.natour21.ui.fragments.EffettuaAccessoFragment;
import it.ingsw.natour21.ui.fragments.EffettuaAccessoFragmentDirections;

public class EffettuaAccessoPresenter {

    private EffettuaAccessoFragment effettuaAccessoFragment;

    public EffettuaAccessoPresenter(EffettuaAccessoFragment effettuaAccessoFragment) {
        this.effettuaAccessoFragment = effettuaAccessoFragment;
    }

    public void mostraSchermataCreaAccount() {
        NavDirections action = EffettuaAccessoFragmentDirections.actionEffettuaAccessoFragmentToCreaAccountFragment();
        Navigation.findNavController(effettuaAccessoFragment.getView()).navigate(action);
    }

    public void effettuaAccesso() {
        NavDirections action = EffettuaAccessoFragmentDirections.actionEffettuaAccessoFragmentToHomeFragment();
        Navigation.findNavController(effettuaAccessoFragment.getView()).navigate(action);
    }

}
