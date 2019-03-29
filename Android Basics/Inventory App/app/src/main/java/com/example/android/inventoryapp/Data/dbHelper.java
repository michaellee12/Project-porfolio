package com.example.android.inventoryapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.Data.Contract.Entry;

/**
 * Created by Mike Lee on 9/08/2017.
 */

public class dbHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "inventory";
    private final static int DB_VERSION = 1;

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PETS_TABLE = "Create TABLE " + Entry.TABLE_NAME + " ("
                + Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Entry.COLUMN_NAME + " TEXT NOT NULL, "
                + Entry.COLUMN_SUPPLIER + " TEXT NOT NULL, "
                + Entry.COLUMN_PRICE + " FLOAT NOT NULL, "
                + Entry.COLUMN_QUANtITY + " INTEGER NOT NULL, "
                + Entry.COLUMN_IMAGE_PATH + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
