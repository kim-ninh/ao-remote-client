package com.ninhhk.aoremote.controling;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.ninhhk.aoremote.AsyncTaskCallback;
import com.ninhhk.aoremote.BackableActivity;
import com.ninhhk.aoremote.Command;
import com.ninhhk.aoremote.ExtraButtonDialog;
import com.ninhhk.aoremote.NoConnectionAlertDialog;
import com.ninhhk.aoremote.R;
import com.ninhhk.aoremote.Utils.ByteArrayUtils;
import com.ninhhk.aoremote.Utils.ListUtils;
import com.ninhhk.aoremote.Utils.ViewsUtil;
import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;
import com.ninhhk.aoremote.model.RemoteButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RemoteControlActivity extends BackableActivity
        implements View.OnClickListener {

    protected static final String TAG = RemoteControlActivity.class.getSimpleName();

    protected RemoteManager remoteManager;
    protected Remote remote;
    protected Set<View> viewSet = new HashSet<>();
    protected List<RemoteButton> extraButton = new ArrayList<>(100);
    protected @LayoutRes
    int layoutResId;
    private Command command;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        initView();
        setButtonClickCallBack();
        command = new ControlCommand(RemoteControlActivity.this);
    }

    protected abstract void initView();

    protected void setButtonClickCallBack() {
        for (View v : viewSet) {
            v.setOnClickListener(this);
        }
    }

    protected void showExtraButtonDialog() {
        ExtraButtonDialog dialog = new ExtraButtonDialog(extraButton, this);
        dialog.show(getSupportFragmentManager(), "Extra button");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        long remoteId = intent.getLongExtra(getString(R.string.remoteId), -1);

        if (remoteId != -1) {
            new LoadRemoteTask(RemoteControlActivity.this, remote -> {
                this.remote = remote;
                setIRDataCode();
                getSupportActionBar()
                        .setTitle(remote.getName());
            })
                    .execute(remoteId);
        }
        command.start(new Command.ResponseListener() {
            @Override
            public void onReceiveResponse(String response) {

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

    private void setIRDataCode() {

        extraButton.addAll(remote.getButtonList());

        ViewsUtil.toggleEnabled(viewSet, false);

        for (View v : viewSet) {
            final String buttonName = (String) v.getTag();

            if (buttonName != null) {
                if (remote.hasButton(buttonName)) {

                    String hexStr = remote.getButtonCode(buttonName);

                    v.setTag(R.id.ir_data, hexStr);
                    v.setEnabled(true);

                    ListUtils.removeIf(extraButton, item -> buttonName.equals(item.getName()));
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {

        Object tag = v.getTag(R.id.ir_data);
        if (tag instanceof byte[]) {
            byte[] IR_code = (byte[]) tag;
            String str_IR_code = ByteArrayUtils.toHex(IR_code);
            Log.i(TAG, "onClick: " + v.getTag() + " : " + str_IR_code);
            command.send(IR_code);
        }

        if (tag instanceof String) {
            String hexa = (String) tag;
            String debugInfo = String.format("onClick: %s : %s", v.getTag(), hexa);
            Log.i(TAG, debugInfo);

            showDebugDialogIfNeed(debugInfo);
            command.send(hexa);
        }
    }

    protected static class LoadRemoteTask extends AsyncTask<Long, Void, Remote> {

        private final RemoteManager remoteManager;
        private final AsyncTaskCallback<Remote> callback;

        public LoadRemoteTask(Context context, AsyncTaskCallback<Remote> callback) {
            remoteManager = RemoteManager.get(context);
            this.callback = callback;
        }

        @Override
        protected Remote doInBackground(Long... longs) {
            return remoteManager.getRemote(longs[0]);
        }

        @Override
        protected void onPostExecute(Remote remote) {
            callback.onComplete(remote);
        }
    }
}
