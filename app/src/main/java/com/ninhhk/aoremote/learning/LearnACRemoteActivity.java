package com.ninhhk.aoremote.learning;

import android.view.View;

import com.ninhhk.aoremote.R;

public class LearnACRemoteActivity extends LearnRemoteActivity {

    public LearnACRemoteActivity() {
        layoutResId = R.layout.activity_ac_remote;
    }

    @Override
    protected void initView() {
        viewSet.add(findViewById(R.id.btn_power));
        viewSet.add(findViewById(R.id.btn_mode));
        viewSet.add(findViewById(R.id.btn_speed));
        viewSet.add(findViewById(R.id.btn_direction));
        viewSet.add(findViewById(R.id.btn_swing));
        viewSet.add(findViewById(R.id.btn_temp_down));
        viewSet.add(findViewById(R.id.btn_temp_up));
        viewSet.add(findViewById(R.id.btn_timer));
        viewSet.add(findViewById(R.id.btn_sleep));

        findViewById(R.id.btn_more).setVisibility(View.GONE);
    }


}
