package com.ninhhk.aoremote.learning;


import android.view.View;
import android.widget.Button;

import com.ninhhk.aoremote.R;
import com.ninhhk.aoremote.TVNumericButtonsDialog;

public class LearnTVRemoteActivity extends LearnRemoteActivity {

    private Button btn_numeric_buttons;

    public LearnTVRemoteActivity() {
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

        findViewById(R.id.more_linear_layout).setVisibility(View.GONE);
        viewSet.add(findViewById(R.id.btn_mute));

        btn_numeric_buttons.setOnClickListener(l -> {
//            showNumericButtonDialog();
        });
    }

    private void showNumericButtonDialog() {
        TVNumericButtonsDialog dialog = new TVNumericButtonsDialog(viewSet);
        dialog.show(getSupportFragmentManager(), "TV numeric button");
    }
}
