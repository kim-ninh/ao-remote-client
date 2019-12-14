package com.ninhhk.aoremote.learning;

import android.content.res.Resources;

import com.ninhhk.aoremote.NotImplementRemoteControlActivity;
import com.ninhhk.aoremote.R;

public class LearnRemoteActivityClassFactory {
    private final Resources resource;

    private LearnRemoteActivityClassFactory(Resources resources) {
        this.resource = resources;
    }

    public static LearnRemoteActivityClassFactory with(Resources resources) {
        return new LearnRemoteActivityClassFactory(resources);
    }

    public Class<?> get(String remote_type) {
        if (remote_type.equals(resource.getString(R.string.tv)))
            return LearnTVRemoteActivity.class;

        if (remote_type.equals(resource.getString(R.string.projector)))
            return LearnProjectorRemoteActivity.class;

        if (remote_type.equals(resource.getString(R.string.ac)))
            return LearnACRemoteActivity.class;

        if (remote_type.equals(resource.getString(R.string.fan)))
            return LearnFanRemoteActivity.class;

        return NotImplementRemoteControlActivity.class;
    }
}
