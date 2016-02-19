package com.pam.maprouheze1.isitinder;

import android.content.Context;
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
        //Updata data
        singUsers.getListUsers().IncCurrentIndex();
        Log.d("CardListener", "appel de discarded");
        if (!singUsers.getListUsers().hasNext()) {
            // update data
            singUsers.recupData(new WebDataListener() {
                @Override
                public void onDataReceived(Results listUsers) {
                    dataCardsAdapter.addAll(listUsers.results);
                    Log.d("Cardlistener", "appel de recupData");
                }

                @Override
                public void onError(String errorDescription) {

                }
            });
        }
            }

    @Override
    public void topCardTapped() {
            //this callback invoked when a top card is tapped by user.
            Intent intent = new Intent(mainActivity.getApplicationContext(), DetailActivity.class);
                // intent.putExtra("User", currentUser);
                mainActivity.startActivity(intent);
            }
}