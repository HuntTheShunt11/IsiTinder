package com.pam.maprouheze1.isitinder.MainActClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pam.maprouheze1.isitinder.DataModel.Result;
import com.pam.maprouheze1.isitinder.DataModel.Singleton;
import com.pam.maprouheze1.isitinder.DataModel.User;
import com.pam.maprouheze1.isitinder.R;
import com.wenchao.cardstack.CardStack;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        mCardStack.setAdapter(mCardAdapter);

        CardListener cardListener = new CardListener(this, mCardAdapter);
        mCardStack.setListener(cardListener);


        likeButton.setOnClickListener(new MainButtonListener(mCardStack));
        nopeButton.setOnClickListener(new MainButtonListener(mCardStack));

        singUsers = Singleton.getInstance(getApplicationContext());

        singUsers.recupData(new MainActivityWebDataListener(mCardAdapter));

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "redemarrage de l'appli!");
        Log.d("OnRestart","currentIndex au redemarrage: "+singUsers.getListUsers().getCurrentIndex());
        //currentUser = singUsers.getListUsers().results.get(singUsers.getListUsers().getCurrentIndex()).user; //à enlever

        mCardStack.reset(true);
        mCardAdapter.clear();

        List<Result> list = singUsers.getListUsers().results;

        for(int i = singUsers.getListUsers().getCurrentIndex(); i < list.size() ; ++i) {
            mCardAdapter.add(list.get(i));
        }



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
