package com.ninhhk.aoremote.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class RemoteDB extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "RemoteBase.db";
    public static final int DATABASE_VERSION = 1;

    public RemoteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
