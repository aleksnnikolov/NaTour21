package it.ingsw.natour21.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import it.ingsw.natour21.R;

public class DrawerHeader {

    private View drawerHeader;

    private ImageView profilePictureImageView;
    private TextView usernameTextView;

    public DrawerHeader(View drawerHeader) {
        this.drawerHeader = drawerHeader;
        inizializzaViews();
    }

    private void inizializzaViews() {
        profilePictureImageView = drawerHeader.findViewById(R.id.profile_picture);
        usernameTextView = drawerHeader.findViewById(R.id.text_view_username);
    }

    public void setDati(String urlImmagine, String nomeUtente) {
        Glide.with(drawerHeader)
                .load("https://i.imgur.com/vqYAxpX.jpeg")
                .into(profilePictureImageView);

        usernameTextView.setText(nomeUtente);
    }

}
