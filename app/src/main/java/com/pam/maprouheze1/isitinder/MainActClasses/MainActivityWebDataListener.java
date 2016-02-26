package com.pam.maprouheze1.isitinder.MainActClasses;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.pam.maprouheze1.isitinder.DataModel.Results;


/**
 * Created by Maxime on 25/02/2016.
 */
//WebDataListener pour la MainActivity
public class MainActivityWebDataListener implements WebDataListener {

    MainActivity mActivity;
    CardsDataAdapter mCardAdapter;

    public MainActivityWebDataListener(CardsDataAdapter cda, MainActivity mA){

        mCardAdapter = cda;
        mActivity = mA;

    }

    @Override
    public void onDataReceived(Results listUsers) {//si on reçoit des donnees

        mCardAdapter.addAll(listUsers.results); //on les ajoute au card adapter

    }

    @Override
    public void onError(String errorDescription) {

        ConnectivityManager manager = (ConnectivityManager) mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);

        NetworkInfo DataOn = manager.getActiveNetworkInfo();

        if (DataOn == null)
        {
            String text = "Impossible de récupérer des utilisateurs, vérifier que les données mobiles ou le wifi sont activés et appuyer sur le bouton recharger pour récuperer des profils";
            Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }

    }
}
