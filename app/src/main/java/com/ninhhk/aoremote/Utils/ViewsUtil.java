package com.ninhhk.aoremote.Utils;

import android.view.View;

import java.util.Set;

public class ViewsUtil {

    public static void toggleEnabled(Set<View> viewSet, boolean enabled) {
        for (View v : viewSet) {
            v.setEnabled(enabled);
        }
    }
}
