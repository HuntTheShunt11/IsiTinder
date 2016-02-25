package com.pam.maprouheze1.isitinder;

import android.util.Log;

/**
 * Created by Maxime on 25/02/2016.
 */
public class CardAdapterWebDataListener implements WebDataListener {

    CardsDataAdapter dataCardsAdapter;

    public CardAdapterWebDataListener(CardsDataAdapter dataAdapter){
        dataCardsAdapter = dataAdapter;
    }

    @Override
    public void onDataReceived(Results listUsers) {
        dataCardsAdapter.addAll(listUsers.results);
    }

    @Override
    public void onError(String errorDescription) {

    }
}
