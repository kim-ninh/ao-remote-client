package com.ninhhk.aoremote.learning;

import android.content.Context;

import com.ninhhk.aoremote.Command;

public class LearnCommand extends Command {

    public LearnCommand(Context context) {
        super(context);
        cmd = "LEARN";
    }
}
