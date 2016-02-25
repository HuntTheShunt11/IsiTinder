package com.pam.maprouheze1.isitinder.MainActClasses;

import android.util.Log;
import android.view.View;

import com.pam.maprouheze1.isitinder.R;
import com.wenchao.cardstack.CardStack;

/**
 * Created by Maxime on 25/02/2016.
 */
public class MainButtonListener implements View.OnClickListener{

    private CardStack cardStack;

    public MainButtonListener(CardStack cardStack) {
        this.cardStack = cardStack;
    }


    @Override
    public void onClick(View v) {
                /*if (!singUsers.getListUsers().hasNext()) {
                    // update data
                    singUsers.recupData(new WebDataListener() {
                        @Override
                        public void onDataReceived(Results listUsers) {
                            mCardAdapter.addAll(listUsers.results);
                            Log.d(TAG, "appel de recupData par les boutons");
                        }

                        @Override
                        public void onError(String errorDescription) {

                        }
                    });
                }*/

        // updateView
        if (v.getId() == R.id.like){  //si l'utilisateur clique like
            cardStack.discardTop(1); //on enleve la carte au sommet de la pile vers la droite
        } else{
            cardStack.discardTop(0);//ou vers la gauche si il clique le bouton nope
        }

        /*Log.d("listener bouton", "taille de CardAdapter: " + mCardAdapter.getCount());

        Log.d("listener bouton", "taille de listUsers: " + singUsers.getListUsers().results.size());*/
        // singUsers.incrPosition();
        //singUsers.getListUsers().IncCurrentIndex(); //plus besoin de l'appeler on le fait dans le card listener

    }
}
