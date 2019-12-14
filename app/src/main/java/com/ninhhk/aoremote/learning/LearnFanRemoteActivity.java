package com.ninhhk.aoremote.learning;

import com.ninhhk.aoremote.R;

public class LearnFanRemoteActivity extends LearnRemoteActivity {
    public LearnFanRemoteActivity() {
        layoutResId = R.layout.activity_fan_remote;
    }

    @Override
    protected void initView() {
        viewSet.add(findViewById(R.id.btn_power));
        viewSet.add(findViewById(R.id.btn_timer));
        viewSet.add(findViewById(R.id.btn_fanspeed));
        viewSet.add(findViewById(R.id.btn_wind_mode));
    }
}
