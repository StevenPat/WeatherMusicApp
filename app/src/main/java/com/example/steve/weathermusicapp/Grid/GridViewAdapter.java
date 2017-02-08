package com.example.steve.weathermusicapp.Grid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steve.weathermusicapp.R;

import java.util.List;

/**
 * Created by Seungbum Han on 2/4/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Mood> {
    public GridViewAdapter(Context context, int resource, List<Mood> objects){
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(null == view) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, null);

        }
        Mood mood = getItem(position);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) view.findViewById(R.id.Mood);

        img.setImageResource(mood.getImageId());
        txtTitle.setText(mood.getTitle());

        return view;


    }
}
