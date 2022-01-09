package it.ingsw.natour21.control;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

import it.ingsw.natour21.R;

public class NavigationManager {

    private AppCompatActivity mainActivity;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView drawerNav;

    public NavigationManager(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setupNavigation() {
        initNavController();
        setupToolbar();
        setupDrawer();
        setNavigationViewsVisibility();
    }

    private void initNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

    }

    private void setupToolbar() {
        drawerLayout = mainActivity.findViewById(R.id.drawer_layout);

        //sets the top level destinations (those without a back button on the top)
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.effettuaAccessoFragment);
        topLevelDestinations.add(R.id.homeFragment);
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).setOpenableLayout(drawerLayout).build();

        toolbar = mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);

        NavigationUI.setupActionBarWithNavController(mainActivity, navController, appBarConfiguration);
    }

    private void setupDrawer() {
        drawerNav = mainActivity.findViewById(R.id.nav_drawer_view);
        NavigationUI.setupWithNavController(drawerNav, navController);
    }

    //nasconde la toolbar nei fragment precedenti al HomeFragment
    private void setNavigationViewsVisibility() {

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.effettuaAccessoFragment ||
                    destination.getId() == R.id.creaAccountFragment ||
                    destination.getId() == R.id.effettuaAccessoGoogleFragment) {
                hideDrawerNav();
                hideToolbar();
            } else {
                showDrawerNav();
                showToolbar();
            }
        });
    }

    private void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    private void hideToolbar() {
        toolbar.setVisibility(View.INVISIBLE);
    }

    private void showDrawerNav() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void hideDrawerNav() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public boolean navigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

}
