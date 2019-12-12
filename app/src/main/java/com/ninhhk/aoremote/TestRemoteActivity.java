package com.ninhhk.aoremote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;

import java.util.List;

public class TestRemoteActivity extends BackableActivity implements View.OnClickListener {

    private TextView txt_model_count;
    private View view_popup_message;
    private Button btn_yes;
    private Button btn_no;
    private Button btn_power;
    private ImageButton btn_left;
    private ImageButton btn_right;

    private List<Remote> templateRemotes;
    private int currentIndex = 0;
    private Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_remote);

        initView();

        String brand_name = getIntent().getStringExtra(getString(R.string.brand_name));
        String remote_type = getIntent().getStringExtra(getString(R.string.remote_type));
        String appTitle = String.format("Add %s remote", remote_type);
        getSupportActionBar().setTitle(appTitle);
        command = new TestCommand(TestRemoteActivity.this);
        new LoadRemoteFromTemplateTask(TestRemoteActivity.this)
                .execute(remote_type, brand_name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        command.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        command.end();
    }

    private void initView() {
        txt_model_count = findViewById(R.id.txt_model_count);
        view_popup_message = findViewById(R.id.view_popup_message);
        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);
        btn_power = findViewById(R.id.btn_power);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);


        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        btn_power.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_power) {
            view_popup_message.setVisibility(View.VISIBLE);

            Object tag = v.getTag();
            if (tag instanceof byte[]) {
                byte[] ir_code = (byte[]) tag;

                command.send(ir_code);
            }

            if (tag instanceof String) {
                String hexa = (String) tag;
                command.send(hexa);
            }
        }

        if (v == btn_left) {
            prevModel();
        }

        if (v == btn_right) {
            nextModel();
        }

        if (v == btn_no) {
            view_popup_message.setVisibility(View.GONE);

            if (!isLastModel()) {
                nextModel();
            } else {
                //SORRY, we can't find any match
                Intent intent = new Intent(TestRemoteActivity.this, NoModelCompactActivity.class);
                startActivity(intent);
            }
        }

        if (v == btn_yes) {

            long templateRemoteId = templateRemotes.get(currentIndex - 1).getId();
            new CloneTemplateRemoteTask(TestRemoteActivity.this)
                    .execute(templateRemoteId);

            command.end();
        }
    }

    private boolean isLastModel() {
        return currentIndex == templateRemotes.size();
    }

    private void prevModel() {
        currentIndex--;
        updateCurrentData();
    }

    private void nextModel() {
        currentIndex++;
        updateCurrentData();
    }

    private void openEditRemoteActivity(long newRemoteId) {
        Intent intent = new Intent(TestRemoteActivity.this, EditRemoteActivity.class);
        intent.putExtra(getString(R.string.remoteId), newRemoteId);
        startActivity(intent);
    }

    private void updateCurrentData() {

        btn_left.setVisibility(View.VISIBLE);
        btn_right.setVisibility(View.VISIBLE);

        if (currentIndex == 1) {
            btn_left.setVisibility(View.GONE);
        }

        if (currentIndex == templateRemotes.size()) {
            btn_right.setVisibility(View.GONE);
        }

        Remote currentModel = templateRemotes.get(currentIndex - 1);
        String IR_code = currentModel.getButtonCode(getString(R.string.remote_power_test));
//        byte[] bytes = IRUtils.prontoHexToBytes(IR_code);
//        btn_power.setTag(bytes);
        btn_power.setTag(IR_code);

        String str = String.format("Test buttons (%d/%d)", currentIndex, templateRemotes.size());
        txt_model_count.setText(str);
    }

    private class LoadRemoteFromTemplateTask extends AsyncTask<String, Void, List<Remote>> {

        private RemoteManager remoteManager;

        public LoadRemoteFromTemplateTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected List<Remote> doInBackground(String... strings) {
            String remote_type = strings[0];
            String brandName = strings[1];

            List<Remote> remotes;
            remotes = remoteManager.getTemplateRemotes(remote_type, brandName);

            return remotes;
        }

        @Override
        protected void onPostExecute(List<Remote> remotes) {
            super.onPostExecute(remotes);
            templateRemotes = remotes;

            currentIndex = 1;
            updateCurrentData();
        }
    }

    private class CloneTemplateRemoteTask extends AsyncTask<Long, Void, Long> {

        RemoteManager remoteManager;

        public CloneTemplateRemoteTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected Long doInBackground(Long... longs) {
            long templateRemoteId = longs[0];
            long newRemoteId = remoteManager.cloneRemote(templateRemoteId);
            return newRemoteId;
        }

        @Override
        protected void onPostExecute(Long newRemoteId) {
            super.onPostExecute(newRemoteId);

            openEditRemoteActivity(newRemoteId);
        }
    }
}
