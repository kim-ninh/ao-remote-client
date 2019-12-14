package com.ninhhk.aoremote.learning;

import android.view.View;

import com.ninhhk.aoremote.R;

public class LearnProjectorRemoteActivity extends LearnRemoteActivity {

    public LearnProjectorRemoteActivity() {
        layoutResId = R.layout.activity_projector_remote;
    }

    @Override
    protected void initView() {
        viewSet.add(findViewById(R.id.btnOK));
        viewSet.add(findViewById(R.id.btn_up));
        viewSet.add(findViewById(R.id.btn_down));
        viewSet.add(findViewById(R.id.btn_left));
        viewSet.add(findViewById(R.id.btn_right));
        viewSet.add(findViewById(R.id.btn_power));
        viewSet.add(findViewById(R.id.btn_menu));
        viewSet.add(findViewById(R.id.btn_back));
        viewSet.add(findViewById(R.id.btn_volume_up));
        viewSet.add(findViewById(R.id.btn_volume_down));

        findViewById(R.id.btn_more).setVisibility(View.GONE);
    }
}
