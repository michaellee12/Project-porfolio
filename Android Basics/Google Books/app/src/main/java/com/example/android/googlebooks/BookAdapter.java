package com.example.android.googlebooks;

import android.app.Activity;
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
 * Created by Mike Lee on 9/07/2017.
 */

public class BookAdapter extends ArrayAdapter<Books> {
    public BookAdapter(Context context, ArrayList<Books> list) {
        super(context, 0, list);
    }

    public void setBooks(ArrayList<Books> list) {
        clear();
        addAll(list);
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
        Books book = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.Title);
        title.setText(book.getTitle());

        TextView author = (TextView) listItemView.findViewById(R.id.Author);
        if (book.getAuthor().equals("")) {
            author.setVisibility(View.GONE);
        } else {
            author.setVisibility(View.VISIBLE);
            author.setText(book.getAuthor());
        }

        TextView Info = (TextView) listItemView.findViewById(R.id.Info);
        Info.setText(book.getInfo());

        return listItemView;
    }
}
