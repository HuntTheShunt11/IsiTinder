package com.pam.maprouheze1.isitinder.DetailActClasses;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.pam.maprouheze1.isitinder.DataModel.Results;
import com.pam.maprouheze1.isitinder.DataModel.Singleton;
import com.pam.maprouheze1.isitinder.MainActClasses.WebDataListener;

/**
 * Created by Maxime on 25/02/2016.
 */
public class DetailButtonListener implements View.OnClickListener{

    DetailActivity activity;
    Singleton singUsers;

    public DetailButtonListener(DetailActivity act) {
        this.activity = act;
        this.singUsers = Singleton.getInstance(act);//on recupere le singleton
    }

    @Override
    public void onClick(View v) {
        if (singUsers.getListUsers().hasNext()) {//si le singleton contient encore des utilisateurs
            activity.currentUser = singUsers.getListUsers().next().user; //on met a jour l'utilisateur courant de l'activity DetailActivity
            activity.showUser();//et on l'affiche
        } else {
            // update data
            singUsers.recupData(new WebDataListener() { //s'il n'y a plus d'utilisateur suivant, on fait un appel reseau pour en recuperer
                @Override
                public void onDataReceived(Results listUsers) { //si on a reçu des donnees
                    activity.currentUser = singUsers.getListUsers().results.get(0).user; //on met a jour l'utilisateur courant
                    activity.showUser();//et on l'affiche
                }

                @Override
                public void onError(String errorDescription) {
                    ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);

                    NetworkInfo DataOn = manager.getActiveNetworkInfo();

                    if (DataOn == null)
                    {
                        String text = "Impossible de récupérer des utilisateurs, vérifier que les données mobiles ou le wifi sont activés et appuyer sur le bouton recharger pour récuperer des profils";
                        Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
}
