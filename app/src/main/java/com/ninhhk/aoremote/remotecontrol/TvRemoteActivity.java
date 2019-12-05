package com.ninhhk.aoremote.remotecontrol;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ninhhk.aoremote.ExtraButtonDialog;
import com.ninhhk.aoremote.R;

public class TvRemoteActivity extends RemoteControlActivity {

    private Button btn_numeric_buttons;
    private ImageButton btn_more;

    public TvRemoteActivity() {
        layoutResId = R.layout.activity_tv_remote;
    }

    @Override
    protected void initView() {
        viewSet.add(findViewById(R.id.btn_power));
        viewSet.add(findViewById(R.id.btn_input_source));
        viewSet.add(findViewById(R.id.btn_home));
        viewSet.add(findViewById(R.id.btn_menu));
        btn_numeric_buttons = findViewById(R.id.btn_numeric_buttons);
        viewSet.add(findViewById(R.id.btn_back));

        viewSet.add(findViewById(R.id.btn_channel_up));
        viewSet.add(findViewById(R.id.btn_channel_down));

        viewSet.add(findViewById(R.id.btn_volume_up));
        viewSet.add(findViewById(R.id.btn_volume_down));

        viewSet.add(findViewById(R.id.btn_OK));
        viewSet.add(findViewById(R.id.btn_up));
        viewSet.add(findViewById(R.id.btn_down));
        viewSet.add(findViewById(R.id.btn_left));
        viewSet.add(findViewById(R.id.btn_right));

        btn_more = findViewById(R.id.btn_more);
        viewSet.add(findViewById(R.id.btn_mute));
    }

    @Override
    protected void setButtonClickCallBack() {
        super.setButtonClickCallBack();

        btn_numeric_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExtraButtonDialog();
            }
        });
    }

    private void showExtraButtonDialog() {

        ExtraButtonDialog dialog = new ExtraButtonDialog(extraButton, this);
        dialog.show(getSupportFragmentManager(), "Extra button");
    }
}
