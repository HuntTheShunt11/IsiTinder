package com.pam.maprouheze1.isitinder.MainActClasses;

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
        // updateView
        if (v.getId() == R.id.like){  //si l'utilisateur clique like
            cardStack.discardTop(1); //on enleve la carte au sommet de la pile de cartes vers la droite
        } else{
            cardStack.discardTop(0);//ou vers la gauche si il clique le bouton nope
        }

    }
}
