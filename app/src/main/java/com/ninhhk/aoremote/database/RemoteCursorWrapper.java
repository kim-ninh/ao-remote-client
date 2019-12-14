package com.ninhhk.aoremote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ninhhk.aoremote.model.Remote;

import static com.ninhhk.aoremote.database.IRRemoteDbSchema.RemoteTable;


public class RemoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public RemoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public String getBrand() {
        return getString(getColumnIndex(RemoteTable.Cols.BRAND));
    }

    public Remote getRemote() {

        long id = getInt(getColumnIndex(RemoteTable.Cols._ID));
        String name = getString(getColumnIndex(RemoteTable.Cols.NAME));
        int isTemplate = getInt(getColumnIndex(RemoteTable.Cols.IS_TEMPLATE));
        String brand = getString(getColumnIndex(RemoteTable.Cols.BRAND));
        String deviceType = getString(getColumnIndex(RemoteTable.Cols.TYPE));

        return new Remote(id, name, isTemplate, brand, deviceType);
    }

    public int getId() {
        return getInt(getColumnIndex(RemoteTable.Cols._ID));
    }


    public String getName() {
        return getString(getColumnIndex(RemoteTable.Cols.NAME));
    }
}
