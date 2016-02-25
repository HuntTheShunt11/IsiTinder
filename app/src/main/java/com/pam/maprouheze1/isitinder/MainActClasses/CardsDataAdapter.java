package com.pam.maprouheze1.isitinder.MainActClasses;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pam.maprouheze1.isitinder.DataModel.Result;
import com.pam.maprouheze1.isitinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maprouheze1 on 17/02/2016.
 */
public class CardsDataAdapter extends ArrayAdapter<Result> {

    public CardsDataAdapter(Context context, int ressource, List<Result> objects) {
        super(context, ressource, objects);
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        //supply the layout for your card
        Result result = getItem(position);

        TextView name = (TextView)(contentView.findViewById(R.id.name));
        name.setText(result.user.name.first);
        TextView age = (TextView)(contentView.findViewById(R.id.age));
        age.setText(", "+result.user.getAge());
        ImageView imageView = (ImageView) (contentView.findViewById(R.id.imageView));
        Picasso.with(getContext()).load(result.user.picture.large).into(imageView);

        return contentView;
    }

}