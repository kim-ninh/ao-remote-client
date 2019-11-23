package com.ninhhk.aoremote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ninhhk.aoremote.database.IRRemoteDbSchema.ButtonTable;
import com.ninhhk.aoremote.model.RemoteButton;

public class ButtonCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ButtonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public RemoteButton getButton() {

        long id = getLong(getColumnIndex(ButtonTable.Cols._ID));
        String name = getString(getColumnIndex(ButtonTable.Cols.NAME));
        byte[] code = getBlob(getColumnIndex(ButtonTable.Cols.CODE));
        return new RemoteButton(id, name, code);
    }
}
