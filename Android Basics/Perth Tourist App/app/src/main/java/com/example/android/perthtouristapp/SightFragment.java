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

public class SightFragment extends Fragment {

    public SightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        final ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(getString(R.string.kings_park),
                getString(R.string.kings_park_loc),
                R.drawable.kings_park,
                getString(R.string.kings_park_summary),
                getString(R.string.kings_park_number)
        ));
        items.add(new Item(getString(R.string.perth_mint),
                getString(R.string.perth_mint_loc),
                R.drawable.perth_mint,
                getString(R.string.perth_mint_summary),
                getString(R.string.perth_mint_number)
        ));
        items.add(new Item(getString(R.string.perth_zoo),
                getString(R.string.perth_zoo_loc),
                R.drawable.perth_zoo,
                getString(R.string.perth_zoo_summary),
                getString(R.string.perth_zoo_number)
        ));
        items.add(new Item(getString(R.string.crown_perth),
                getString(R.string.crown_perth_loc),
                R.drawable.crown_perth,
                getString(R.string.crown_perth_summary),
                getString(R.string.crown_perth_number)
        ));


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
