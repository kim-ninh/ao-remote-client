package com.ninhhk.aoremote;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PickDeviceTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ADD_REQUEST_CODE = 1234;
    private Button btn_tv;
    private Button btn_fan;
    private Button btn_ac;
    private Button btn_projector;
    private Button btn_tv_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        setButtonTag();
    }

    private void initView() {
        btn_tv = findViewById(R.id.btn_tv);
        btn_fan = findViewById(R.id.btn_fan);
        btn_ac = findViewById(R.id.btn_ac);
        btn_projector = findViewById(R.id.btn_projector);
        btn_tv_box = findViewById(R.id.btn_tv_box);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        Object tag = v.getTag();
        if (tag instanceof String) {
            String strTag = (String) tag;
            Class<?> destActivityClass = ActivityClassFactory.with(getResources()).get(strTag);
            Intent intent = new Intent(this, destActivityClass);
//            startActivity(intent);
        }
    }
}
