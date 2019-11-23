package com.ninhhk.aoremote.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ninhhk.aoremote.model.Remote;
import com.ninhhk.aoremote.model.RemoteButton;

import java.util.ArrayList;
import java.util.List;

import static com.ninhhk.aoremote.database.IRRemoteDbSchema.ButtonTable;
import static com.ninhhk.aoremote.database.IRRemoteDbSchema.RemoteTable;

public class RemoteManager {
    private static RemoteManager sRemoteManager;

    private Context context;
    private SQLiteDatabase mDatabase;

    private RemoteManager(Context context) {
        this.context = context;
        this.mDatabase = new RemoteBaseHelper(context).getWritableDatabase();
    }

    public static RemoteManager get(Context context) {
        if (sRemoteManager == null) {
            sRemoteManager = new RemoteManager(context);
        }
        return sRemoteManager;
    }

    public void close() {
        mDatabase.close();
    }

    public String[] getBrands(String deviceType) {
        List<String> brands = new ArrayList<>();

        String whereClause = RemoteTable.Cols.TYPE + "=?";

        RemoteCursorWrapper cursor = queryRemotes(whereClause, new String[]{deviceType});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                brands.add(cursor.getBrand());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return (String[]) brands.toArray();
    }

    public List<Remote> getUserRemotes() {
        List<Remote> remotes = new ArrayList<>();

        String whereClause = RemoteTable.Cols.IS_TEMPLATE + "=?";
        RemoteCursorWrapper cursor = queryRemotes(whereClause, new String[]{"0"});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Remote remote = cursor.getRemote();
                int remoteId = cursor.getId();
                remote.initButtons(getRemoteButtons(remoteId));

                remotes.add(remote);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return remotes;
    }

    public List<RemoteButton> getRemoteButtons(long remoteId) {
        List<RemoteButton> buttons = new ArrayList<>();

        String whereClause = ButtonTable.Cols.REMOTE + "=?";
        String[] whereArgs = {String.valueOf(remoteId)};

        ButtonCursorWrapper cursor = queryButtons(whereClause, whereArgs);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                buttons.add(cursor.getButton());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return buttons;
    }

    public long cloneRemote(long remoteId) {

        return -1;
    }

    private RemoteCursorWrapper queryRemotes(@Nullable String whereClause, @Nullable String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                RemoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RemoteCursorWrapper(cursor);
    }

    private ButtonCursorWrapper queryButtons(@Nullable String whereClause, @Nullable String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ButtonTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ButtonCursorWrapper(cursor);
    }

}