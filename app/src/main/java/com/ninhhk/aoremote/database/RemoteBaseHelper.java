package com.ninhhk.aoremote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import static com.ninhhk.aoremote.database.IRRemoteDbSchema.BrandTable;
import static com.ninhhk.aoremote.database.IRRemoteDbSchema.ButtonTable;
import static com.ninhhk.aoremote.database.IRRemoteDbSchema.RemoteTable;
import static com.ninhhk.aoremote.database.IRRemoteDbSchema.TypeTable;

public class RemoteBaseHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_TABLE_BRAND =
            "CREATE TABLE " + BrandTable.NAME + " (" +
                    BrandTable.Cols.NAME + " TEXT PRIMARY KEY)";
    public static final String SQL_CREATE_TABLE_TYPE =
            "CREATE TABLE " + TypeTable.NAME + " (" +
                    TypeTable.Cols.NAME + " TEXT PRIMARY KEY)";
    public static final String SQL_CREATE_TABLE_REMOTE =
            "CREATE TABLE " + RemoteTable.NAME + " (" +
                    RemoteTable.Cols._ID + " INTEGER PRIMARY KEY, " +

                    RemoteTable.Cols.NAME + " TEXT, " +

                    RemoteTable.Cols.IS_TEMPLATE + " INTEGER DEFAULT 0, " +

                    RemoteTable.Cols.BRAND + " TEXT, " +

                    RemoteTable.Cols.TYPE + " TEXT, " +

                    "FOREIGN KEY (" + RemoteTable.Cols.BRAND + ") " +
                    "REFERENCES " + BrandTable.NAME + " (" + BrandTable.Cols.NAME + "), " +

                    "FOREIGN KEY ( " + RemoteTable.Cols.TYPE + ") " +
                    "REFERENCES " + TypeTable.NAME + " (" + TypeTable.Cols.NAME + " )" +
                    ")";
    public static final String SQL_CREATE_TABLE_BUTTON =
            "CREATE TABLE " + ButtonTable.NAME + " (" +
                    ButtonTable.Cols._ID + " INTEGER PRIMARY KEY, " +

                    ButtonTable.Cols.NAME + " TEXT, " +

                    ButtonTable.Cols.CODE + " BLOB, " +

                    ButtonTable.Cols.REMOTE + " INTEGER, " +

                    "FOREIGN KEY (" + ButtonTable.Cols.REMOTE + ") " +
                    "REFERENCES " + RemoteTable.NAME + " (" + RemoteTable.Cols._ID + " ))";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "RemoteBase.db";
    private static List<String> DEFAULT_DATA;


    public RemoteBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public RemoteBaseHelper(Context context, List<String> defaultDataSql) {
        super(context, DATABASE_NAME, null, VERSION);
        DEFAULT_DATA = defaultDataSql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BRAND);
        db.execSQL(SQL_CREATE_TABLE_TYPE);
        db.execSQL(SQL_CREATE_TABLE_REMOTE);
        db.execSQL(SQL_CREATE_TABLE_BUTTON);
//        insertDefaultData(db);

        for (String sql : DEFAULT_DATA) {
            db.execSQL(sql);
        }

    }

    private void insertDefaultData(SQLiteDatabase db) {
        ContentValues brandTableRow = new ContentValues();
        brandTableRow.put(BrandTable.Cols.NAME, "Samsung");
        db.insert(BrandTable.NAME, null, brandTableRow);

        ContentValues typeTableRow = new ContentValues();
        typeTableRow.put(TypeTable.Cols.NAME, "TV");
        db.insert(TypeTable.NAME, null, typeTableRow);

        ContentValues remoteTableRow = new ContentValues();
        remoteTableRow.put(RemoteTable.Cols.NAME, "Bedroom TV");
        remoteTableRow.put(RemoteTable.Cols.BRAND, "Samsung");
        remoteTableRow.put(RemoteTable.Cols.TYPE, "TV");
        long remoteId = db.insert(RemoteTable.NAME, null, remoteTableRow);

        ContentValues buttonTableRow = new ContentValues();
        buttonTableRow.put(ButtonTable.Cols.NAME, "Menu");
        buttonTableRow.put(ButtonTable.Cols.CODE, new byte[]{-128, -127, -2, -1, 0, 1, 2, 3});
        buttonTableRow.put(ButtonTable.Cols.REMOTE, remoteId);
        db.insert(ButtonTable.NAME, null, buttonTableRow);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
