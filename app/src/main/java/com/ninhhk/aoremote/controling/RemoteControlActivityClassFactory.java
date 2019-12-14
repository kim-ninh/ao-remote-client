package com.ninhhk.aoremote.controling;

import android.content.res.Resources;

import com.ninhhk.aoremote.NotImplementRemoteControlActivity;
import com.ninhhk.aoremote.R;

public class RemoteControlActivityClassFactory {

    private final Resources resource;

    private RemoteControlActivityClassFactory(Resources resources) {
        this.resource = resources;
    }

    public static RemoteControlActivityClassFactory with(Resources resources) {
        return new RemoteControlActivityClassFactory(resources);
    }

    public Class<?> get(String s) {
        if (s.equals(resource.getString(R.string.tv))) {
            return TvRemoteActivity.class;
        }

        if (s.equals(resource.getString(R.string.fan))) {
            return FanRemoteActivity.class;
        }

        if (s.equals(resource.getString(R.string.ac))) {
            return AcRemoteActivity.class;
        }

        if (s.equals(resource.getString(R.string.projector))) {
            return ProjectorRemoteActivity.class;
        }

        if (s.equals(resource.getString(R.string.tv_box))) {

        }

        return NotImplementRemoteControlActivity.class;
    }
}
