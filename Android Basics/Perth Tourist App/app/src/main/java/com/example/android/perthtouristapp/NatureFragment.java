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

public class NatureFragment extends Fragment {
    public NatureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        final ArrayList<Item> items = new ArrayList<Item>();

        items.add(new Item(getString(R.string.pinnacles),
                getString(R.string.pinnacles_loc),
                R.drawable.pinnacles,
                getString(R.string.pinnacles_summary),
                getString(R.string.pinnacles_number)
        ));
        items.add(new Item(getString(R.string.cottesloe_beach),
                getString(R.string.cottesloe_beach_loc),
                R.drawable.cottesloe,
                getString(R.string.cottesloe_beach_summary),
                getString(R.string.cottesloe_beach_number)
        ));
        items.add(new Item(getString(R.string.lucky_bay),
                getString(R.string.lucky_bay_loc),
                R.drawable.lucky_bay,
                getString(R.string.lucky_bay_summary),
                getString(R.string.lucky_bay_number)
        ));
        items.add(new Item(getString(R.string.rottnest_island),
                getString(R.string.rottnest_island_loc),
                R.drawable.rottnest,
                getString(R.string.rottnest_island_summary),
                getString(R.string.rottnest_island_number)
        ));


        ItemAdapter adapter = new ItemAdapter(getActivity(), items);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long I){
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
