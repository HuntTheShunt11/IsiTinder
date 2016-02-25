package com.pam.maprouheze1.isitinder.MainActClasses;

import com.pam.maprouheze1.isitinder.DataModel.Results;

/**
 * Created by Maxime on 25/02/2016.
 */
public class MainActivityWebDataListener implements WebDataListener {

    CardsDataAdapter mCardAdapter;

    public MainActivityWebDataListener(CardsDataAdapter cda){

        mCardAdapter = cda;

    }

    @Override
    public void onDataReceived(Results listUsers) {

        //mCardAdapter = new CardsDataAdapter(getApplicationContext(),0, listUsers.results);
        mCardAdapter.addAll(listUsers.results);

    }

    @Override
    public void onError(String errorDescription) {

    }
}
