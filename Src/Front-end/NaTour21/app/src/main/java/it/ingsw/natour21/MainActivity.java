package it.ingsw.natour21;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import it.ingsw.natour21.control.AuthenticationManager;
import it.ingsw.natour21.control.NavigationManager;
import it.ingsw.natour21.control.Utils;
import it.ingsw.natour21.ui.fragments.NuovoItinerarioFragment;

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

    //naviga su nello stack di navigazione
    @Override
    public boolean onSupportNavigateUp() {
        return navigationManager.navigateUp() || super.onSupportNavigateUp();
    }

    public void gestisciPermessiMappa(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    Utils.REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    // gestione click tasto indietro di Android (in basso)
    @Override
    public void onBackPressed() {
        if (!navigationManager.gestisciClickIndietroInCasiSpeciali()) {
            super.onBackPressed();
        }
    }

    // gestione click tasto indietro della toolbar (in alto a sinistra)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (navigationManager.gestisciClickIndietroInCasiSpeciali()) {
                    return true;
                } else {
                    return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final int PICK_GPX_FILE = 2;

    public void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        startActivityForResult(intent, PICK_GPX_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_GPX_FILE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Fragment fragment = navigationManager.getFragmentCorrente();
                if (fragment instanceof NuovoItinerarioFragment) {
                    ((NuovoItinerarioFragment) fragment).getPresenter().parseFileGPX(uri);
                }
            }
        }

    }
}