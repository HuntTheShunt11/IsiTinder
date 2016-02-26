package com.pam.maprouheze1.isitinder.DetailActClasses;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pam.maprouheze1.isitinder.DataModel.Results;
import com.pam.maprouheze1.isitinder.DataModel.Singleton;
import com.pam.maprouheze1.isitinder.DataModel.User;
import com.pam.maprouheze1.isitinder.MainActClasses.WebDataListener;
import com.pam.maprouheze1.isitinder.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Singleton singUsers;
    TextView userName ;
    TextView userAge ;
    TextView location ;
    TextView email;
    Button likeButton;
    Button nopeButton;


    ImageView imageView;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //on recupere chaque element de l'interface graphique
        userName = (TextView) findViewById(R.id.name);
        userAge = (TextView) findViewById(R.id.age);
        likeButton = (Button) findViewById(R.id.like);
        nopeButton = (Button) findViewById(R.id.nope);
        imageView = (ImageView) findViewById(R.id.imageView);
        location = (TextView) findViewById(R.id.location);
        email = (TextView) findViewById(R.id.email);

        //on recupere le singleton
        singUsers = Singleton.getInstance(getApplicationContext());

        //on definit l'utlisateur courant
        currentUser = singUsers.getListUsers().results.get(singUsers.getListUsers().getCurrentIndex()).user;
        showUser();//et on l'affiche

        //pour les deux boutons on definit un listener
        likeButton.setOnClickListener(new DetailButtonListener(this));
        nopeButton.setOnClickListener(new DetailButtonListener(this));

    }

    protected void showUser(){
        String prenom = currentUser.name.first; //on recupere le prenom
        setTitle(prenom);//et on l'affiche

        String nom = currentUser.name.last; //on recupere le nom
        userName.setText(nom);//et on l'affiche

        long age = currentUser.getAge();//recuperation de l'age
        userAge.setText("" + age);//et affichage

        String locationstr = currentUser.getLocation().toString(); //recuperation de l'adresse postale
        location.setText(locationstr);//et affichage

        String emailAddress = currentUser.getEmail().toString();//recuperation de l'adresse mail
        email.setText(emailAddress);//et affichage

        Picasso.with(getApplicationContext()).load(currentUser.picture.large).into(imageView); //affichage de l'image
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appmenu, menu); //on defini le menu de l'application avec le fichier xml correspondant (appmenu)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: //si l'action de refresh a ete selectionnee
                singUsers.recupData(new WebDataListener() { //on lance l'appel reseau pour recuperer les donnees
                    @Override
                    public void onDataReceived(Results listUsers) { //quand on reçoit les resultats
                        currentUser = singUsers.getListUsers().results.get(0).user; //l'utilisateur courant est celui avec l'id 0
                        showUser();//et on l'affiche
                    }

                    @Override
                    public void onError(String errorDescription) {
                        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                        NetworkInfo DataOn = manager.getActiveNetworkInfo();

                        if (DataOn == null)
                        {
                            String text = "Impossible de récupérer des utilisateurs, vérifier que les données mobiles ou le wifi sont activés et appuyer sur le bouton recharger pour récuperer des profils";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                        }

                    }
                });
                break;
            default:
                break;
        }

        return true;
    }
}
