package com.pam.maprouheze1.isitinder;

/**
 * Created by maprouheze1 on 03/02/2016.
 */
public interface WebDataListener {

    void onDataReceived(Results listUsers);

    void onError(String errorDescription);

}
