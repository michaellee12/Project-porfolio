package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mike Lee on 10/07/2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsArticle> {
    public NewsAdapter(Context context, ArrayList<NewsArticle> List) {
        super(context, 0, List);
    }

    private String CapFirst(String input) {
        String[] Array = input.split(" ");
        String output = "";
        for (int i = 0; i < Array.length; i++) {
            if (Array[i].length() > 1) {
                output += Array[i].substring(0, 1).toUpperCase() + Array[i].substring(1) + " ";
            } else {
                output += Array[i] + " ";

            }
        }
        return output;
    }

    public void setList(ArrayList<NewsArticle> List) {
        clear();
        addAll(List);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            ViewCompat.setElevation(listItemView, 5);
        }

        NewsArticle newsArticle = getItem(position);

        TextView sectionName = (TextView) listItemView.findViewById(R.id.SectionName);
        sectionName.setText(newsArticle.getSectionName());

        TextView type = (TextView) listItemView.findViewById(R.id.Type);
        type.setText(newsArticle.getType());

        TextView title = (TextView) listItemView.findViewById(R.id.Title);
        title.setText(CapFirst(newsArticle.getTitle()));

        TextView date = (TextView) listItemView.findViewById(R.id.Date);
        date.setText(newsArticle.getDate());

        return listItemView;
    }
}
