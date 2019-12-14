package com.ninhhk.aoremote.learning;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.ninhhk.aoremote.BaseActivity;
import com.ninhhk.aoremote.Command;
import com.ninhhk.aoremote.EditRemoteNameActivity;
import com.ninhhk.aoremote.NoConnectionAlertDialog;
import com.ninhhk.aoremote.R;
import com.ninhhk.aoremote.Utils.ActionBarStateUtils;
import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;
import com.ninhhk.aoremote.model.RemoteButton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class LearnRemoteActivity extends BaseActivity
        implements View.OnClickListener, Command.ResponseListener {

    protected @LayoutRes
    int layoutResId;
    protected Set<View> viewSet = new HashSet<>();
    private String TAG = LearnRemoteActivity.class.getSimpleName();
    private TextView txtMsg;
    private Map<String, String> buttonMap = new HashMap<>();

    private Command command;

    private String receivedHexStr = "0000 ABCD";

    private ActionBarStateUtils actionBarStateUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        initView();
        setButtonClickCallBack();

        ActionBar actionBar = initActionBar();

        View v = actionBar.getCustomView();
        txtMsg = v.findViewById(R.id.txt_msg);

        actionBarStateUtils = new ActionBarStateUtils(this, v, viewSet);
        command = new LearnCommand(this);
    }

    private ActionBar initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setCustomView(R.layout.action_bar_custom_view);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        return actionBar;
    }

    @Override
    protected void onResume() {
        super.onResume();

        command.start(new Command.ResponseListener() {
            @Override
            public void onReceiveResponse(String response) {
                command.get(LearnRemoteActivity.this);
            }

            @Override
            public void onError(String error) {
                NoConnectionAlertDialog dialog = new NoConnectionAlertDialog(error);
                dialog.show(getSupportFragmentManager(), "No connection");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        command.end(null);
    }

    @Override
    protected void onDestroy() {
        actionBarStateUtils.releaseResource();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        viewSet.add(v);

        v.setTag(R.id.ir_data, receivedHexStr);
        command.get(this);
        actionBarStateUtils.setState(ActionBarStateUtils.WAITING_IR_SIGNAL_STATE);
    }

    @Override
    public void onReceiveResponse(String response) {
        String debugInfo = String.format("onReceiveResponse: %s", response);
        Log.i(TAG, debugInfo);
        showDebugDialogIfNeed(debugInfo);

        receivedHexStr = response;
        actionBarStateUtils.setState(ActionBarStateUtils.RECEIVED_SIGNAL_STATE);
    }

    @Override
    public void onError(String error) {
        showDebugDialogIfNeed(error);

        actionBarStateUtils.setState(ActionBarStateUtils.TIMEOUT_WAITING_IR_STATE);
        txtMsg.setOnClickListener(l -> {
            actionBarStateUtils.setState(ActionBarStateUtils.WAITING_IR_SIGNAL_STATE);
            command.get(this);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.learn_remote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_item_complete:
                saveNewRemote();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected abstract void initView();

    private void saveNewRemote() {
        extractTagNonNullButton();
        String deviceType = getIntent().getStringExtra(getString(R.string.remote_type));
        new AddRemoteTask(LearnRemoteActivity.this).execute(deviceType);
    }

    private void setButtonClickCallBack() {
        for (View v : viewSet) {
            v.setOnClickListener(this);
        }
    }

    private void extractTagNonNullButton() {
        for (View v : viewSet) {
            Object tag = v.getTag(R.id.ir_data);
            if (tag instanceof String) {
                String hexStr = (String) tag;
                buttonMap.put((String) v.getTag(), hexStr);
            }
        }
    }

    private void openEditRemoteActivity(long remoteID) {

        Intent intent = new Intent(this, EditRemoteNameActivity.class);
        intent.putExtra(getString(R.string.remoteId), remoteID);
        startActivity(intent);
    }

    private class AddRemoteTask extends AsyncTask<String, Void, Long> {

        private RemoteManager remoteManager;

        public AddRemoteTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected Long doInBackground(String... strings) {
            String remoteType = strings[0];
            long remoteId = remoteManager.addRemote(new Remote(remoteType));

            Set<Map.Entry<String, String>> entrySet = buttonMap.entrySet();

            for (Map.Entry<String, String> entry : entrySet) {
                RemoteButton button = new RemoteButton(entry.getKey(), entry.getValue(), remoteId);
                remoteManager.addButton(button);
            }

            return remoteId;
        }

        @Override
        protected void onPostExecute(Long remoteId) {
            super.onPostExecute(remoteId);
            openEditRemoteActivity(remoteId);
        }
    }
}
