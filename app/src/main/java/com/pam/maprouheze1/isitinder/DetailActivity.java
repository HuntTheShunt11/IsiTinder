package com.pam.maprouheze1.isitinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        singUsers.recupData(new WebDataListener() {
            @Override
            public void onDataReceived(Results listUsers) {
                currentUser = singUsers.getListUsers().results.get(singUsers.getListUsers().getCurrentIndex()).user;
                String prenom = currentUser.name.first;
                setTitle(prenom);

                String nom = currentUser.name.last;
                userName.setText(nom);
                long age = currentUser.getAge();
                userAge.setText("" + age);

                String locationstr = currentUser.getLocation().toString();
                location.setText(locationstr);

                String emailAddress = currentUser.getEmail().toString();
                email.setText(emailAddress);

                Picasso.with(getApplicationContext()).load(currentUser.picture.medium).into(imageView); //chargement premiere image

            }

            @Override
            public void onError(String errorDescription) {

            }
        });
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singUsers.getListUsers().hasNext()) {
                    currentUser = singUsers.getListUsers().next().user;
                    singUsers.getListUsers().IncCurrentIndex();
                    String prenom = currentUser.name.first;
                    setTitle(prenom);

                    String nom = currentUser.name.last;
                    userName.setText(nom);


                    long age = currentUser.getAge();
                    userAge.setText("" + age);

                    String locationstr = currentUser.getLocation().toString();
                    location.setText(locationstr);
                    Picasso.with(getApplicationContext()).load(currentUser.picture.medium).into(imageView);
                    
                    String emailAddress = currentUser.getEmail().toString();
                    email.setText(emailAddress);

                    // updateView
                } else {
                    // update data
                    singUsers.recupData(new WebDataListener() {
                        @Override
                        public void onDataReceived(Results listUsers) {
                            currentUser = singUsers.getListUsers().results.get(0).user;

                            singUsers.setPosition(0);//à quoi ça sert?

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

                            Picasso.with(getApplicationContext()).load(currentUser.picture.medium).into(imageView); //chargement premiere image

                        }

                        @Override
                        public void onError(String errorDescription) {

                        }
                    });
                }
            }
        };

        likeButton.setOnClickListener(buttonListener);
        nopeButton.setOnClickListener(buttonListener);

    }
}
