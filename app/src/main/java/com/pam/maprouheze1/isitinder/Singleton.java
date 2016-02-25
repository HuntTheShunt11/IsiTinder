package com.pam.maprouheze1.isitinder;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maprouheze1 on 03/02/2016.
 */
public final class Singleton {
    private static Singleton mInstance = null;

    private Results listUsers;
    private int position;//à retirer
    private int nbUsersToRequest;
    private int nbUsersList;
    private Context appContext;

    private Singleton(Context context) {
        super();
        appContext = context;
        nbUsersToRequest = 5;
        nbUsersList = 5;
    }

    public final static Singleton getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Singleton.class) {
                if (mInstance == null) {
                    mInstance = new Singleton(context);
                }
            }
        }
        return mInstance;

    }

    public void recupData(final WebDataListener listener) {
        Log.d("singleton", "entreeRecupdata");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(appContext);
        String url = "https://randomuser.me/api/?format=json&results="+nbUsersToRequest+"&nat=fr";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("singleton", "onResponse");
                        final Gson gson = new Gson();
                        listUsers = gson.fromJson(response, Results.class);
                        if (listUsers == null) {
                            Log.e("Deserialisation", "Json non parsé! ");
                        }
                        nbUsersList = listUsers.results.size();
                        listener.onDataReceived(listUsers);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Appel reseau", "That didn't work!: ");
                listener.onError("erreur");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    

    public Results getListUsers() {
        return listUsers;
    }

    public void setListUsers(Results listUsers) {
        this.listUsers = listUsers;
    }

}