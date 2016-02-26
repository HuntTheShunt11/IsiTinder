package com.pam.maprouheze1.isitinder.MainActClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.pam.maprouheze1.isitinder.DataModel.Singleton;
import com.pam.maprouheze1.isitinder.DetailActClasses.DetailActivity;
import com.wenchao.cardstack.CardStack;

/**
 * Created by maprouheze1 on 17/02/2016.
 */
class CardListener implements CardStack.CardEventListener {

    MainActivity mainActivity;
    CardsDataAdapter dataCardsAdapter;
    Singleton singUsers;
    public CardListener(MainActivity activity, CardsDataAdapter cardAdapter) {
        mainActivity = activity;
        dataCardsAdapter = cardAdapter;
        singUsers = Singleton.getInstance(mainActivity.getApplicationContext());
    }

    //implement card event interface
    @Override
    public boolean swipeEnd(int direction, float distance) {
            //if "return true" the dismiss animation will be triggered
            //if false, the card will move back to stack
            //distance is finger swipe distance in dp

            //the direction indicate swipe direction
            //there are four directions
            //  0  |  1
            // ----------
            //  2  |  3

            //incremente position singleton
            return (distance>300)? true : false;
            }

    @Override
    public boolean swipeStart(int direction, float distance) {

            return true;
            }

    @Override
    public boolean swipeContinue(int direction, float distanceX, float distanceY) {

            return true;
            }

    @Override
    public void discarded(int id, int direction) {
        //this callback invoked when dismiss animation is finished.
        //quand la carte a ete enlevee (fin du mouvement) cette fonction est appelee
        Log.d("CardListener", "appel de discarded");

        if(singUsers.getListUsers()!= null) {//si le singleton n'est pas vide
            if (!singUsers.getListUsers().hasNext()) {//si le singleton n'a pas d'utilisateur suivant
                // update data
                Log.d("Cardlistener", "appel de recupData");
                singUsers.recupData(new MainActivityWebDataListener(dataCardsAdapter, mainActivity));//on fait un appel reseau pour en recuperer

            } else {//sinon s'il reste des utilisateurs dans le singleton
                singUsers.getListUsers().next();//on incremente la position de l'utilisateur courant
            }
        }else{//si le singleton est vide on indique à l'utilisateur de recharger des profils
            String text = "Aucun utilisateur en mémoire, vérifier que les données mobiles ou le wifi sont activés et appuyer sur le bouton recharger pour récuperer des profils";
            Toast.makeText(mainActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void topCardTapped() {
            //this callback invoked when a top card is tapped by user.
            //si la carte du dessus est tappee, on lance l'activite detail
            Intent intent = new Intent(mainActivity.getApplicationContext(), DetailActivity.class);
                mainActivity.startActivity(intent);
            }
}