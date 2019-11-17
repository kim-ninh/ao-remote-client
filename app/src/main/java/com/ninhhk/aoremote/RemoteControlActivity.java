package com.ninhhk.aoremote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RemoteControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new TvRemoteFragment())
                .commit();
    }
}
