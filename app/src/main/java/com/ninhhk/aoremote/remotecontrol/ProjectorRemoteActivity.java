package com.ninhhk.aoremote.remotecontrol;

import android.view.View;
import android.widget.Button;

import com.ninhhk.aoremote.R;

public class ProjectorRemoteActivity extends RemoteControlActivity {

    private Button btnMore;

    public ProjectorRemoteActivity() {
        layoutResId = R.layout.activity_projector_remote;
    }

    @Override
    protected void initView() {
        viewSet.add(findViewById(R.id.btnOK));
        viewSet.add(findViewById(R.id.btn_up));
        viewSet.add(findViewById(R.id.btn_down));
        viewSet.add(findViewById(R.id.btn_left));
        viewSet.add(findViewById(R.id.btn_right));
        viewSet.add(findViewById(R.id.btn_menu));
        viewSet.add(findViewById(R.id.btn_back));
        viewSet.add(findViewById(R.id.btn_volume_up));
        viewSet.add(findViewById(R.id.btn_volume_down));
        btnMore = findViewById(R.id.btn_more);
    }

    @Override
    protected void setButtonClickCallBack() {
        super.setButtonClickCallBack();

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExtraButtonDialog();
            }
        });
    }
}
