package com.pam.maprouheze1.isitinder;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;

/**
 * Created by Maxime on 25/02/2016.
 */
public class DetailButtonListener implements View.OnClickListener{

    DetailActivity activity;
    Singleton singUsers;

    public DetailButtonListener(DetailActivity act) {
        this.activity = act;
        this.singUsers = Singleton.getInstance(act);
    }

    @Override
    public void onClick(View v) {
        if (singUsers.getListUsers().hasNext()) {
            activity.currentUser = singUsers.getListUsers().next().user;
            //singUsers.getListUsers().IncCurrentIndex();
            activity.showUser();

            // updateView
        } else {
            // update data
            singUsers.recupData(new WebDataListener() {
                @Override
                public void onDataReceived(Results listUsers) {
                    activity.currentUser = singUsers.getListUsers().results.get(0).user;
                    activity.showUser();
                    //singUsers.setPosition(0);//à quoi ça sert?



                }

                @Override
                public void onError(String errorDescription) {

                }
            });
        }
    }
}
