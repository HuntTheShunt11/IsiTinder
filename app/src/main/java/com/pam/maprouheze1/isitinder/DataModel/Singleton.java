package com.pam.maprouheze1.isitinder.DataModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pam.maprouheze1.isitinder.MainActClasses.WebDataListener;

/**
 * Created by maprouheze1 on 03/02/2016.
 */
public final class Singleton {
    private static Singleton mInstance = null;

    private Results listUsers;
    private int nbUsersToRequest;
    private Context appContext;

    private Singleton(Context context) {
        super();
        appContext = context;
        nbUsersToRequest = 5;
    }

    public final static Singleton getInstance(Context context) {//methode pour recuperer le singleton
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

        String text = "Récupération d'utilisateurs en cours...";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(appContext, text, duration);//affichage d'un message pour indiquer l'appel reseau
        toast.show();

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
                        if (listUsers == null) {//si la liste d'utlisateurs est vide
                            Log.e("Deserialisation", "Json non parsé! ");//on log que le json a ete mal parse
                        }
                        listener.onDataReceived(listUsers);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Appel reseau", "erreur lors de l'appel reseau");
                listener.onError("erreur lors de l'appel reseau: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public Results getListUsers() {
        return listUsers;
    }

}