package com.pam.maprouheze1.isitinder.MainActClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pam.maprouheze1.isitinder.DataModel.Result;
import com.pam.maprouheze1.isitinder.DataModel.Singleton;
import com.pam.maprouheze1.isitinder.DataModel.User;
import com.pam.maprouheze1.isitinder.R;
import com.wenchao.cardstack.CardStack;

import android.view.Menu;
import android.view.MenuItem;
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

    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "demarrage de l'appli");

        //on recupere chaque element de l'interface graphique
        userName = (TextView) findViewById(R.id.name);
        userAge = (TextView) findViewById(R.id.age);
        likeButton = (Button) findViewById(R.id.like);
        nopeButton = (Button) findViewById(R.id.nope);
        imageView = (ImageView) findViewById(R.id.imageView);

        //on definit l'array adapter specifique aux cards (CardsDatadapter)
        mCardAdapter = new CardsDataAdapter(this, 0, new ArrayList<Result>());

        //on definit la card stack
        mCardStack = (CardStack)findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_layout);
        mCardStack.setStackMargin(20);
        //et on definit son adapter qui est mCardAapter
        mCardStack.setAdapter(mCardAdapter);

        //on cree un listener pour la card stack et on lui attache
        CardListener cardListener = new CardListener(this, mCardAdapter);
        mCardStack.setListener(cardListener);

        //on definit les listeners pour les boutons like et nope
        likeButton.setOnClickListener(new MainButtonListener(mCardStack));
        nopeButton.setOnClickListener(new MainButtonListener(mCardStack));

        //on recupere l'instance du singleton
        singUsers = Singleton.getInstance(getApplicationContext());

        //et on lance un appel reseau pour recuperer des utilisateurs
        singUsers.recupData(new MainActivityWebDataListener(mCardAdapter, this));

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "redemarrage de l'appli!");

        if(singUsers.getListUsers()!= null) {

            mCardStack.reset(true);//au retour a la main activity on reset la card stack
            mCardAdapter.clear();//on vide le card adapter

            List<Result> list = singUsers.getListUsers().results;//on recupere les results du singleton

            for (int i = singUsers.getListUsers().getCurrentIndex(); i < list.size(); ++i) {//et on remplit le card adapter avec l'utlisateur courant et les suivants du singleton
                mCardAdapter.add(list.get(i));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appmenu, menu);//on defini le menu de l'application avec le fichier xml correspondant (appmenu)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //si l'action de refresh a ete selectionnee
            case R.id.action_refresh:
                mCardAdapter.clear(); //on vide le Card Adapter
                singUsers.recupData(new MainActivityWebDataListener(mCardAdapter, this)); //on fait un appel reseau pour recuperer les donnees
                mCardStack.reset(true);//et on reset la card stack
                break;
            default:
                break;
        }

        return true;
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
