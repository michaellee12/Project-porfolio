package com.example.android.inventoryapp.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mike Lee on 9/08/2017.
 */

public class Contract {
    private Contract(){}
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String INVENTORY_PATH = "inventory";

    public static final class Entry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,INVENTORY_PATH);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"+ CONTENT_AUTHORITY+"/"+INVENTORY_PATH;
        public static final String CONTENT_ITME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"+CONTENT_AUTHORITY+"/"+INVENTORY_PATH;
        public static final String TABLE_NAME = "inventory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANtITY = "quantity";
        public static final String COLUMN_SUPPLIER = "supplier";
        public static final String COLUMN_IMAGE_PATH = "image";

    }


}
