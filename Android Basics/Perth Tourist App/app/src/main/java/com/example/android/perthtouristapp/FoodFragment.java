package com.example.android.perthtouristapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FoodFragment extends Fragment {

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        final ArrayList<Item> items = new ArrayList<Item>();

        items.add(new Item(getString(R.string.matilda_bay_restaurant),
                getString(R.string.matilda_bay_restaurant_loc),
                R.drawable.matilda_bay_restaurant,
                getString(R.string.matilda_bay_restaurant_summary),
                getString(R.string.matilda_bay_restaurant_number)));

        items.add(new Item(getString(R.string.silks),
                getString(R.string.silks_loc),
                R.drawable.silk,
                getString(R.string.silks_summary),
                getString(R.string.silks_number)));

        items.add(new Item(getString(R.string.the_heritage_brasserie_bar_boardroom),
                getString(R.string.the_heritage_brasserie_bar_boardroom_loc),
                R.drawable.the_heritage_brasserie_bar,
                getString(R.string.the_heritage_brasserie_bar_boardroom_summary),
                getString(R.string.the_heritage_brasserie_bar_boardroom_number)));

        items.add(new Item(getString(R.string.wildflower),
                getString(R.string.wildflower_loc),
                R.drawable.wildflower,
                getString(R.string.wildflower_summary),
                getString(R.string.wildflower_number)));


        ItemAdapter adapter = new ItemAdapter(getActivity(), items);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long I) {
                Item item = items.get(position);
                Uri gmmIntentUri = Uri.parse(item.getCoordinate());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }

        });

        return rootView;
    }
}
