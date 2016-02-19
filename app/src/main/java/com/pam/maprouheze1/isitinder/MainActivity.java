package com.pam.maprouheze1.isitinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wenchao.cardstack.CardStack;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Singleton singUsers;
    TextView userName ;
    TextView userAge ;
    Button likeButton;
    Button nopeButton;
    CardStack mCardStack;
    CardsDataAdapter mCardAdapter;
    ImageView imageView;
    User currentUser;

    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "demarrage de l'appli");

        userName = (TextView) findViewById(R.id.name);
        userAge = (TextView) findViewById(R.id.age);
        likeButton = (Button) findViewById(R.id.like);
        nopeButton = (Button) findViewById(R.id.nope);
        imageView = (ImageView) findViewById(R.id.imageView);


        mCardAdapter = new CardsDataAdapter(this, 0, new ArrayList<Result>());

        mCardStack = (CardStack)findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_layout);
        mCardStack.setStackMargin(20);
        CardListener cardListener = new CardListener(this, mCardAdapter);
        mCardStack.setListener(cardListener);


        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!singUsers.getListUsers().hasNext()) {
                    // update data
                    singUsers.recupData(new WebDataListener() {
                        @Override
                        public void onDataReceived(Results listUsers) {
                            mCardAdapter.addAll(listUsers.results);
                            Log.d(TAG, "appel de recupData");
                        }

                        @Override
                        public void onError(String errorDescription) {

                        }
                    });
                }

                // updateView
                if (v.getId() == R.id.like){
                    mCardStack.discardTop(1);
                } else{

                    mCardStack.discardTop(0);
                }
                // singUsers.incrPosition();
                //singUsers.getListUsers().IncCurrentIndex(); //plus besoin de l'appeler on le fait dans le card listener

            }
        };
        View.OnClickListener pictureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
               // intent.putExtra("User", currentUser);
                startActivity(intent);
            }
        };

       // imageView.setOnClickListener(pictureListener);
        likeButton.setOnClickListener(buttonListener);
        nopeButton.setOnClickListener(buttonListener);

        singUsers = Singleton.getInstance(getApplicationContext());

        singUsers.recupData(new WebDataListener() {
            @Override
            public void onDataReceived(Results listUsers) {

                //mCardAdapter = new CardsDataAdapter(getApplicationContext(),0, listUsers.results);
                mCardAdapter.addAll(listUsers.results);
                mCardStack.setAdapter(mCardAdapter);

            }

            @Override
            public void onError(String errorDescription) {

            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "redemarrage de l'appli!");
        currentUser = singUsers.getListUsers().results.get(singUsers.getPosition()).user;
        //à modifier pour récupérer l'image

        /*String nom = currentUser.name.first;
        userName.setText(nom);
        long age =   currentUser.getAge();
        userAge.setText(", " + age);
        Picasso.with(getApplicationContext()).load(currentUser.picture.medium).into(imageView); //chargement premiere image*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "pause de l'appli!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "l'appli passe en arriere plan");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "killage de l'appli");
    }
}
