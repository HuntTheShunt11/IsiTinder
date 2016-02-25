package com.pam.maprouheze1.isitinder;

import android.util.Log;
import android.view.View;

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
        if (v.getId() == R.id.like){
            cardStack.discardTop(1);
        } else{

            cardStack.discardTop(0);
        }

        /*Log.d("listener bouton", "taille de CardAdapter: " + mCardAdapter.getCount());

        Log.d("listener bouton", "taille de listUsers: " + singUsers.getListUsers().results.size());*/
        // singUsers.incrPosition();
        //singUsers.getListUsers().IncCurrentIndex(); //plus besoin de l'appeler on le fait dans le card listener

    }
}
