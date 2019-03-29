package com.example.android.perthtouristapp;

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

import static android.media.CamcorderProfile.get;


public class ShopFragment extends Fragment {
    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        final ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(getString(R.string.e_shed_markets),
                getString(R.string.e_shed_markets_loc),
                R.drawable.e_shed_markets,
                getString(R.string.e_shed_markets_summary),
                getString(R.string.e_shed_markets_number)

        ));

        items.add(new Item("Cloisters Shopping Arcade Perth",
                getString(R.string.cloisters_shopping_arcade_perth_loc),
                R.drawable.cloisters,
                getString(R.string.cloisters_shopping_arcade_perth_summary),
                getString(R.string.cloisters_shopping_arcade_perth_number)
        ));

        items.add(new Item("Hay Street Mall",
                getString(R.string.hay_street_mall_loc),
                R.drawable.hay_street,
                getString(R.string.hay_street_mall_summary)
        ));

        items.add(new Item("Twilight Hawkers Market",
                getString(R.string.twilight_hawkers_market_loc),
                R.drawable.twilight_hawkers,
                getString(R.string.twilight_hawkers_market_summary),
                getString(R.string.twilight_hawkers_market_number)
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
