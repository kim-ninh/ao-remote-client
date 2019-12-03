package com.ninhhk.aoremote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class PickDeviceTypeActivity extends BackableActivity implements View.OnClickListener {

    private static final int ADD_REQUEST_CODE = 1234;
    private Button btn_tv;
    private Button btn_fan;
    private Button btn_ac;
    private Button btn_projector;
    private Button btn_tv_box;

    private RadioButton rd_btn_from_template;
    private RadioButton rd_btn_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device_type);

        getSupportActionBar().setTitle(getString(R.string.select_device_type));
        initView();
        setButtonTag();
    }

    private void initView() {
        btn_tv = findViewById(R.id.btn_tv);
        btn_fan = findViewById(R.id.btn_fan);
        btn_ac = findViewById(R.id.btn_ac);
        btn_projector = findViewById(R.id.btn_projector);
        btn_tv_box = findViewById(R.id.btn_tv_box);

        rd_btn_from_template = findViewById(R.id.rd_btn_from_template);
        rd_btn_manual = findViewById(R.id.rd_btn_manual);

        btn_tv.setOnClickListener(this);
        btn_fan.setOnClickListener(this);
        btn_ac.setOnClickListener(this);
        btn_projector.setOnClickListener(this);
        btn_tv_box.setOnClickListener(this);
    }

    private void setButtonTag() {
        btn_tv.setTag(getString(R.string.tv));
        btn_fan.setTag(getString(R.string.fan));
        btn_ac.setTag(getString(R.string.ac));
        btn_projector.setTag(getString(R.string.projector));
        btn_tv_box.setTag(getString(R.string.tv_box));
    }


    @Override
    public void onClick(View v) {

        Object tag = v.getTag();
        if (tag instanceof String) {
            String remote_type = (String) tag;

            if (rd_btn_from_template.isChecked()) {

                Intent intent = new Intent(this, RemoteBrandListActivity.class);
                intent.putExtra(getString(R.string.remote_type), remote_type);
                startActivity(intent);

            } else if (rd_btn_manual.isChecked()) {
                //TODO: start RemoteLearningActivity
            }
        }
    }
}
