package com.example.android.perthtouristapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Activity context, ArrayList<Item> items) {

        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            ViewCompat.setElevation(listItemView, 5);
        }

        Item item = getItem(position);

        ImageView Image = (ImageView) listItemView.findViewById(R.id.Image);
        Image.setImageResource(item.getImage());

        TextView Name = (TextView) listItemView.findViewById(R.id.Name);
        Name.setText(item.getName());

        TextView Summary = (TextView) listItemView.findViewById(R.id.Summary);
        Summary.setText(item.getSummary());

        TextView Location = (TextView) listItemView.findViewById(R.id.Location);
        Location.setText(item.getLocation());

        if (item.hasNumber()) {
            TextView Phone = (TextView) listItemView.findViewById(R.id.Phone);
            Phone.setText(item.getNumber());
            View phoneVis = listItemView.findViewById(R.id.PhoneVis);
            phoneVis.setVisibility(View.VISIBLE);
        }

        return listItemView;
    }
}
