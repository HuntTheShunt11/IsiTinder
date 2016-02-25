package com.pam.maprouheze1.isitinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wenchao.cardstack.CardStack;

/**
 * Created by maprouheze1 on 17/02/2016.
 */
class CardListener implements CardStack.CardEventListener {

    AppCompatActivity mainActivity;
    CardsDataAdapter dataCardsAdapter;
    Singleton singUsers;
    public CardListener(AppCompatActivity activity, CardsDataAdapter cardAdapter) {
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
        Log.d("CardListener Discarded", "currentIndex avant incr: "+singUsers.getListUsers().getCurrentIndex());
        if (!singUsers.getListUsers().hasNext()) {
            // update data

            Log.d("Cardlistener", "appel de recupData");
            singUsers.recupData(new CardAdapterWebDataListener(dataCardsAdapter));
        }else{
            singUsers.getListUsers().next();//on incrémente la position de l'utilisateur courant
            Log.d("CardListener Discarded", "currentIndex après incrementation: " + singUsers.getListUsers().getCurrentIndex());
            Log.d("CardListener Discarded","nb dataCardAdapter incrementation: "+dataCardsAdapter.getCount());
        }
    }

    @Override
    public void topCardTapped() {
            //this callback invoked when a top card is tapped by user.
            //si la carte du dessu est tappee, on lance l'activite detail
            Intent intent = new Intent(mainActivity.getApplicationContext(), DetailActivity.class);
                // intent.putExtra("User", currentUser);
                mainActivity.startActivity(intent);
            }
}