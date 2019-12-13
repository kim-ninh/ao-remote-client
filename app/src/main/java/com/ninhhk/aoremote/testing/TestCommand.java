package com.ninhhk.aoremote.testing;

import android.content.Context;

import com.ninhhk.aoremote.Command;

public class TestCommand extends Command {

    public TestCommand(Context context) {
        super(context);
        cmd = "TEST";
    }
}
