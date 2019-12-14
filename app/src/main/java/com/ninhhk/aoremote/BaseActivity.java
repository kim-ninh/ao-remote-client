package com.ninhhk.aoremote;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class BaseActivity extends AppCompatActivity {
    protected boolean isDebug;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isDebug = sharedPreferences.getBoolean("debugMode", false);
    }


    protected void showDebugDialogIfNeed(String msg) {
        if (isDebug) {
            DebugInfoDialog dialog = new DebugInfoDialog(msg);
            dialog.show(getSupportFragmentManager(), "Debug Info");
        }
    }
}
