package com.ninhhk.aoremote.controling;

import android.content.Context;

import com.ninhhk.aoremote.Command;

public class ControlCommand extends Command {

    public ControlCommand(Context context) {
        super(context);
        cmd = "CTRL";
    }
}
