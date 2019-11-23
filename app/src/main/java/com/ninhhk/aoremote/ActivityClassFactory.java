package com.ninhhk.aoremote;

import android.content.res.Resources;

class ActivityClassFactory {

    private final Resources resource;

    private ActivityClassFactory(Resources resources) {
        this.resource = resources;
    }

    public static ActivityClassFactory with(Resources resources) {
        return new ActivityClassFactory(resources);
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
