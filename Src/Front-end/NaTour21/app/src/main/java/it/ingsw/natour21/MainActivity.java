package it.ingsw.natour21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

import it.ingsw.natour21.control.AuthenticationManager;
import it.ingsw.natour21.control.NavigationManager;

public class MainActivity extends AppCompatActivity {

    private NavigationManager navigationManager;

    //TODO: add DrawerHeader object from CM20

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();
        initializeAmplify();
    }

    private void setupNavigation() {
        navigationManager = new NavigationManager(this);
        navigationManager.setupNavigation();
    }

    private void initializeAmplify() {
        AuthenticationManager.getInstance().inizializzaAuthenticationManager(getApplicationContext());
    }


    //navigates up through the nav_graph
    @Override
    public boolean onSupportNavigateUp() {
        return navigationManager.navigateUp() || super.onSupportNavigateUp();
    }

}