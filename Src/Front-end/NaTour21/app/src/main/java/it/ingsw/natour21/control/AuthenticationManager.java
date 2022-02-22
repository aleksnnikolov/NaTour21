package it.ingsw.natour21.control;

import android.content.Context;
import android.util.Log;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import it.ingsw.natour21.control.presenters.CreaAccountPresenter;
import it.ingsw.natour21.control.presenters.EffettuaAccessoPresenter;


public class AuthenticationManager {

    private static AuthenticationManager instance;

    private CreaAccountPresenter creaAccountPresenter;
    private EffettuaAccessoPresenter effettuaAccessoPresenter;

    public static AuthenticationManager getInstance() {
        if (instance == null)
            instance = new AuthenticationManager();

        return instance;
    }

    public void inizializzaAuthenticationManager(Context context) {
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(context.getApplicationContext());
            Log.i("AMPLIFY", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("AMPLIFY", "Could not initialize Amplify", error);
        }
    }

    public void registraUtente(String email, String password, String nomeUtente) {

        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> { Log.i("AMPLIFY", "Result: " + result.toString());
                               creaAccountPresenter.terminaCreazioneAccount(email, nomeUtente); },
                error -> { Log.e("AMPLIFY", "Sign up failed", error);
                               creaAccountPresenter.mostraMessaggioErrore(error.getMessage());}
        );

    }

    public void signInUtente(String email, String password) {
        Amplify.Auth.signIn(
                email,
                password,
                result -> {Log.i("AMPLIFY", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                            if (result.isSignInComplete())
                                effettuaAccessoPresenter.getDatiUtente(email);},
                error -> {Log.e("AMPLIFY", error.toString());
                            effettuaAccessoPresenter.mostraMessaggioErrore(error.getMessage());}
        );
    }

    public boolean verificaCodiceConfermaCorretto(String email, String codiceConferma) {
        final boolean[] esitoConfermaCodice = new boolean[1];

        Amplify.Auth.confirmSignUp(
                email,
                codiceConferma,
                result -> {Log.i("AMPLIFY", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                            esitoConfermaCodice[0] = result.isSignUpComplete();},
                error ->  Log.e("AMPLIFY", error.toString())
        );

        return esitoConfermaCodice[0];
    }

    public void signOutUtente() {

    }

    public void setCreaAccountPresenter(CreaAccountPresenter creaAccountPresenter) {
        this.creaAccountPresenter = creaAccountPresenter;
    }

    public void setEffettuaAccessoPresenter(EffettuaAccessoPresenter effettuaAccessoPresenter) {
        this.effettuaAccessoPresenter = effettuaAccessoPresenter;
    }
}
