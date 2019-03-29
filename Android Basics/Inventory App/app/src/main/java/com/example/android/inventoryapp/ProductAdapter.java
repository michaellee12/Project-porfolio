package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.inventoryapp.Data.Contract.Entry;

/**
 * Created by Mike Lee on 9/08/2017.
 */

public class ProductAdapter extends CursorAdapter {
    public ProductAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    private int Quantity;
    private TextView quantity;
    private int ID;
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Context context1 = context;
        String Name = cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAME));
        String ImagePath = cursor.getString(cursor.getColumnIndex(Entry.COLUMN_IMAGE_PATH));
        Quantity = cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_QUANtITY));
        float Price = cursor.getFloat(cursor.getColumnIndex(Entry.COLUMN_PRICE));
        ID = cursor.getInt(cursor.getColumnIndex(Entry._ID));

        TextView name = (TextView)view.findViewById(R.id.name);
        quantity = (TextView)view.findViewById(R.id.quantity);
        TextView price = (TextView)view.findViewById(R.id.price);
        ImageView image = (ImageView) view.findViewById(R.id.Image);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Quantity>0) {
                    Quantity -= 1;
                    ContentValues values = new ContentValues();
                    values.put(Entry.COLUMN_QUANtITY, Quantity);
                    Uri uri = ContentUris.withAppendedId(Entry.CONTENT_URI,ID);
                    context1.getContentResolver().update(uri, values,null,null);
                    quantity.setText("Quantity: "+String.valueOf(Quantity));
                }

            }
        });

        name.setText(Name);
        quantity.setText("Quantity: "+String.valueOf(Quantity));
        price.setText("Price: $"+String.valueOf(Price));
        try{
            image.setImageURI(Uri.parse(ImagePath));
        }catch (Exception e){
            Log.e(ProductAdapter.class.getSimpleName(),e+ImagePath);
        }


    }
}
