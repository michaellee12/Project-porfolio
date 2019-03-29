package com.example.android.inventoryapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.inventoryapp.Data.Contract.Entry;

/**
 * Created by Mike Lee on 9/08/2017.
 */

public class InventoryProvider extends ContentProvider {
    private static final String LOG_TAG = InventoryProvider.class.getSimpleName();
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.INVENTORY_PATH, INVENTORY);
        mUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.INVENTORY_PATH + "/#", INVENTORY_ID);
    }

    private dbHelper mdbHelper;

    @Override
    public boolean onCreate() {
        mdbHelper = new dbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mdbHelper.getReadableDatabase();
        Cursor cursor;
        int match = mUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                cursor = db.query(Entry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INVENTORY_ID:
                selection = Entry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(Entry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (mUriMatcher.match(uri) != INVENTORY) {
            throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

        SQLiteDatabase db = mdbHelper.getWritableDatabase();
        long id = db.insert(Entry.TABLE_NAME, null, contentValues);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues.size() == 0) {
            return 0;
        }
        if (mUriMatcher.match(uri) != INVENTORY_ID) {
            throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

        SQLiteDatabase db = mdbHelper.getWritableDatabase();
        selection = Entry._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        int rowupdate = db.update(Entry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (rowupdate != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowupdate;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mdbHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        int rowsDeleted = 0;
        switch (match) {
            case INVENTORY:
                rowsDeleted = db.delete(Entry.TABLE_NAME, selection, selectionArgs);
                break;
            case INVENTORY_ID:
                selection = Entry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(Entry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }


    @Override
    public String getType(Uri uri) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case (INVENTORY):
                return Entry.CONTENT_ITME_TYPE;
            case (INVENTORY_ID):
                return Entry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalStateException("Unknown URI" + uri + " with match" + match);

        }
    }
}
