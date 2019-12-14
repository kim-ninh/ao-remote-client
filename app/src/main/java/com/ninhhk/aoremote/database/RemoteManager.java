package com.ninhhk.aoremote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ninhhk.aoremote.model.Remote;
import com.ninhhk.aoremote.model.RemoteButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.ninhhk.aoremote.database.IRRemoteDbSchema.ButtonTable;
import static com.ninhhk.aoremote.database.IRRemoteDbSchema.RemoteTable;

public class RemoteManager {
    private static RemoteManager sRemoteManager;

    private Context context;
    private SQLiteDatabase mDatabase;

    private RemoteManager(Context context) {
        this.context = context;

        this.mDatabase = new RemoteDB(context).getWritableDatabase();
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

        String whereClause = RemoteTable.Cols.TYPE + " = ? AND " +
                RemoteTable.Cols.IS_TEMPLATE + " = ?";

        RemoteCursorWrapper cursor = queryBrands(whereClause, new String[]{deviceType, "1"});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                brands.add(cursor.getBrand());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return brands.toArray(new String[0]);
    }

    public List<Remote> getUserRemotes() {
        String whereClause = RemoteTable.Cols.IS_TEMPLATE + " = ?";
        String[] whereArgs = {"0"};

        return getRemotes(whereClause, whereArgs);
    }

    public List<Remote> getTemplateRemotes(String remote_type, String brandName) {
        String whereClause = RemoteTable.Cols.TYPE + " = ? AND " +
                RemoteTable.Cols.BRAND + " = ? AND " +
                RemoteTable.Cols.IS_TEMPLATE + " = ? ";

        String[] whereArgs = {remote_type, brandName, "1"};

        return getRemotes(whereClause, whereArgs);
    }

    public List<Remote> getRemotes(String whereClause, String[] whereArgs) {
        List<Remote> remotes = new ArrayList<>();
        RemoteCursorWrapper cursor = queryRemotes(whereClause, whereArgs);

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

        String whereClause = ButtonTable.Cols.REMOTE + " = ?";
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
        Remote remote = getRemote(remoteId);
        remote.setIsTemplate(0);
        remote.setName(String.format("%s %s", remote.getBrand(), remote.getDeviceType()));
        long newRemoteId = addRemote(remote);

        Iterator<Map.Entry<String, String>> buttonIterator = remote.getButtonsIterator();
        while (buttonIterator.hasNext()) {

            Map.Entry<String, String> entry = buttonIterator.next();
            RemoteButton remoteButton = new RemoteButton(entry.getKey(), entry.getValue(), newRemoteId);
            addButton(remoteButton);
        }

        return newRemoteId;
    }

    public long addButton(RemoteButton button) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ButtonTable.Cols.NAME, button.getName());
        contentValues.put(ButtonTable.Cols.CODE, button.getCode());
        contentValues.put(ButtonTable.Cols.REMOTE, button.getRemote());

        long id = mDatabase.insert(ButtonTable.NAME, null, contentValues);
        button.setId(id);
        return id;
    }

    public long addRemote(Remote remote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RemoteTable.Cols.NAME, remote.getName());
        contentValues.put(RemoteTable.Cols.IS_TEMPLATE, remote.isTemplate());
        contentValues.put(RemoteTable.Cols.BRAND, remote.getBrand());
        contentValues.put(RemoteTable.Cols.TYPE, remote.getDeviceType());

        long id = mDatabase.insert(RemoteTable.NAME, null, contentValues);
        remote.setId(id);
        return id;
    }

    public Remote getRemote(long remoteId) {

        List<Remote> remotes = new ArrayList<>();
        String whereClause = RemoteTable.Cols._ID + " = ?";
        String[] whereArgs = {String.valueOf(remoteId)};
        RemoteCursorWrapper cursor = queryRemotes(whereClause, whereArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Remote remote = cursor.getRemote();
                int currentRemoteId = cursor.getId();
                remote.initButtons(getRemoteButtons(currentRemoteId));

                remotes.add(remote);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return remotes.get(0);
    }

    public void updateRemote(Remote remote) {
        long id = remote.getId();

        ContentValues contentValues = new ContentValues();
        contentValues.put(RemoteTable.Cols.NAME, remote.getName());

        String selection = RemoteTable.Cols._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        mDatabase.update(RemoteTable.NAME, contentValues, selection, selectionArgs);

        for (RemoteButton button : remote.getButtonList()) {
            updateButton(button);
        }
    }

    public String getRemoteName(long remoteId) {
        String remoteName = "";
        String whereClause = RemoteTable.Cols._ID + " = ?";
        String[] whereArgs = {String.valueOf(remoteId)};

        RemoteCursorWrapper cursor = queryRemotes(whereClause, whereArgs);
        try {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                remoteName = cursor.getName();
            }

        } finally {
            cursor.close();
        }

        return remoteName;
    }

    public void updateRemoteName(long remoteId, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RemoteTable.Cols.NAME, name);

        String selection = RemoteTable.Cols._ID + " = ?";
        String[] selectionArgs = {String.valueOf(remoteId)};

        mDatabase.update(RemoteTable.NAME, contentValues, selection, selectionArgs);
    }

    private void updateButton(RemoteButton button) {
        long id = button.getId();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ButtonTable.Cols.NAME, button.getName());
        contentValues.put(ButtonTable.Cols.CODE, button.getCode());
        contentValues.put(ButtonTable.Cols.REMOTE, button.getRemote());

        String selection = RemoteTable.Cols._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        mDatabase.update(ButtonTable.NAME, contentValues, selection, selectionArgs);
    }

    public void deleteRemote(Remote remote) {

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

    private RemoteCursorWrapper queryBrands(@Nullable String whereClause, @Nullable String[] whereArgs) {
        String[] columns = {RemoteTable.Cols.BRAND};

        Cursor cursor = mDatabase.query(
                true,
                RemoteTable.NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new RemoteCursorWrapper(cursor);
    }
}