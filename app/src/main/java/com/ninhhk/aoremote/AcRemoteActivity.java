package com.ninhhk.aoremote;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class AcRemoteActivity extends RemoteControlActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_remote);
    }
}
