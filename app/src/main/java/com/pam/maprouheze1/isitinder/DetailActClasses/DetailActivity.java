package com.pam.maprouheze1.isitinder.DetailActClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pam.maprouheze1.isitinder.DataModel.Singleton;
import com.pam.maprouheze1.isitinder.DataModel.User;
import com.pam.maprouheze1.isitinder.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Singleton singUsers;
    TextView userName ;
    TextView userAge ;
    TextView location ;
    TextView email;
    Button likeButton;
    Button nopeButton;


    ImageView imageView;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        userName = (TextView) findViewById(R.id.name);
        userAge = (TextView) findViewById(R.id.age);
        likeButton = (Button) findViewById(R.id.like);
        nopeButton = (Button) findViewById(R.id.nope);
        imageView = (ImageView) findViewById(R.id.imageView);
        location = (TextView) findViewById(R.id.location);
        email = (TextView) findViewById(R.id.email);


        singUsers = Singleton.getInstance(getApplicationContext());

        currentUser = singUsers.getListUsers().results.get(singUsers.getListUsers().getCurrentIndex()).user;
        Log.d("onCreate DetailActivity", "currentIndex au demarrage: " + singUsers.getListUsers().getCurrentIndex());
        showUser();

       /* singUsers.recupData(new WebDataListener() {
            @Override
            public void onDataReceived(Results listUsers) {
                currentUser = singUsers.getListUsers().results.get(singUsers.getListUsers().getCurrentIndex()).user;
                showUser();

            }

            @Override
            public void onError(String errorDescription) {

            }
        });*/

       /* View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singUsers.getListUsers().hasNext()) {
                    currentUser = singUsers.getListUsers().next().user;
                    singUsers.getListUsers().IncCurrentIndex();
                    showUser();

                    // updateView
                } else {
                    // update data
                    singUsers.recupData(new WebDataListener() {
                        @Override
                        public void onDataReceived(Results listUsers) {
                            currentUser = singUsers.getListUsers().results.get(0).user;

                            //singUsers.setPosition(0);//à quoi ça sert?

                           showUser();

                        }

                        @Override
                        public void onError(String errorDescription) {

                        }
                    });
                }
            }
        };*/

        likeButton.setOnClickListener(new DetailButtonListener(this));
        nopeButton.setOnClickListener(new DetailButtonListener(this));

    }

    protected void showUser(){
        String prenom = currentUser.name.first;
        setTitle(prenom);

        String nom = currentUser.name.last;
        userName.setText(nom);
        long age =   currentUser.getAge();
        userAge.setText("" + age);

        String locationstr = currentUser.getLocation().toString();
        location.setText(locationstr);

        String emailAddress = currentUser.getEmail().toString();
        email.setText(emailAddress);

        Picasso.with(getApplicationContext()).load(currentUser.picture.large).into(imageView); //chargement premiere image
    }
}
