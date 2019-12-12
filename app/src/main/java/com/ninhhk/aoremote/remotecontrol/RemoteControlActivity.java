package com.ninhhk.aoremote.remotecontrol;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.ninhhk.aoremote.BackableActivity;
import com.ninhhk.aoremote.ByteArrayUtils;
import com.ninhhk.aoremote.Command;
import com.ninhhk.aoremote.ExtraButtonDialog;
import com.ninhhk.aoremote.IRUtils;
import com.ninhhk.aoremote.ListUtils;
import com.ninhhk.aoremote.R;
import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;
import com.ninhhk.aoremote.model.RemoteButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
        Iterator<View> iterator = viewSet.iterator();
        while (iterator.hasNext()) {
            View v = iterator.next();
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
            new LoadRemoteTask(RemoteControlActivity.this)
                    .execute(remoteId);
        }
        command.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        command.end();
    }

    private void setIRDataCode() {
//        Iterator<View> iterator = viewSet.iterator();
//        while (iterator.hasNext()) {
//            View v = iterator.next();
//            String buttonName = (String) v.getTag();
//
//            if (buttonName != null && !buttonName.isEmpty()) {
//                String hexStr = remote.getButtonCode(buttonName);
//                if (hexStr != null) {
//                    byte bytes[] = IRUtils.prontoHexToBytes(hexStr);
//                    v.setTag(R.id.ir_data, bytes);
//                }
//            }
//        }
        // if has button's tag equal with RemoteButton's name => enableButton
        // else disable button, add RemoteButton in extraFunction

        Iterator<View> iterator = viewSet.iterator();
        extraButton.addAll(remote.getButtonList());
        while (iterator.hasNext()) {
            View v = iterator.next();
            v.setEnabled(false);
        }


        iterator = viewSet.iterator();
        while (iterator.hasNext()) {
            View v = iterator.next();
            final String buttonName = (String) v.getTag();

            if (buttonName != null) {
                if (remote.hasButton(buttonName)) {

                    String hexStr = remote.getButtonCode(buttonName);
                    byte[] bytes = IRUtils.prontoHexToBytes(hexStr);
                    v.setTag(R.id.ir_data, bytes);
                    v.setEnabled(true);

                    ListUtils.removeIf(extraButton, new ListUtils.Condition<RemoteButton>() {
                        @Override
                        public boolean isMatch(RemoteButton item) {
                            return buttonName.equals(item.getName());
                        }
                    });
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
        //TODO: send byte[] to server

        Object tag = v.getTag(R.id.ir_data);
        if (tag instanceof byte[]) {
            byte[] IR_code = (byte[]) tag;
            String str_IR_code = ByteArrayUtils.toHex(IR_code);
            Log.i(TAG, "onClick: " + v.getTag() + " : " + str_IR_code);
            command.send(IR_code);
        }
    }

    protected class LoadRemoteTask extends AsyncTask<Long, Void, Remote> {

        public LoadRemoteTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected Remote doInBackground(Long... longs) {
            return remoteManager.getRemote(longs[0]);
        }

        @Override
        protected void onPostExecute(Remote remote) {
            RemoteControlActivity.this.remote = remote;
            setIRDataCode();
            getSupportActionBar()
                    .setTitle(remote.getName());
        }
    }
}
