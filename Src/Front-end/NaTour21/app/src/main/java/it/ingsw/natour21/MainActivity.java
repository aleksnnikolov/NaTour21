package it.ingsw.natour21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView drawerNav;

    //TODO: add DrawerHeader object from CM20

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationComponent();
    }

    private void setupNavigationComponent() {
        initNavController();
        setupToolbar();
        setupDrawer();
        setNavigationViewsVisibility();
    }

    private void initNavController() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

    }

    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawer_layout);

        //sets the top level destinations (those without a back button on the top)
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.effettuaAccessoFragment);
        topLevelDestinations.add(R.id.homeFragment);
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).setOpenableLayout(drawerLayout).build();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setupDrawer() {
        drawerNav = findViewById(R.id.nav_drawer_view);
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

    //navigates up through the nav_graph
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

}