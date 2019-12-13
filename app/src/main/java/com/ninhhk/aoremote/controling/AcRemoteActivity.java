package com.ninhhk.aoremote.controling;

import android.view.View;
import android.widget.Button;

import com.ninhhk.aoremote.R;

public class AcRemoteActivity extends RemoteControlActivity {

    private Button btn_more;

    public AcRemoteActivity() {
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
        btn_more = findViewById(R.id.btn_more);
    }

    @Override
    protected void setButtonClickCallBack() {
        super.setButtonClickCallBack();

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExtraButtonDialog();
            }
        });
    }
}
