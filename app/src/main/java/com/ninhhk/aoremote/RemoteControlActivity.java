package com.ninhhk.aoremote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class RemoteControlActivity extends AppCompatActivity
        implements View.OnClickListener {

    protected static final String TAG = RemoteControlActivity.class.getSimpleName();

    protected RemoteManager remoteManager;
    protected Remote remote;
    protected Set<View> viewSet = new HashSet<>();
    protected @LayoutRes
    int layoutResId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        initView();
        setButtonClickCallBack();
    }

    protected abstract void initView();

    protected void setButtonClickCallBack() {
        Iterator<View> iterator = viewSet.iterator();
        while (iterator.hasNext()) {
            View v = iterator.next();
            v.setOnClickListener(this);
        }
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
    }


    private void setIRDataCode() {
        Iterator<View> iterator = viewSet.iterator();
        while (iterator.hasNext()) {
            View v = iterator.next();
            String buttonName = (String) v.getTag();

            if (buttonName != null && !buttonName.isEmpty()) {
                v.setTag(R.id.ir_data, remote.getButtonCode(buttonName));
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
            String str_IR_code = new String(IR_code);
            Log.i(TAG, "onClick: " + v.getTag() + " : " + str_IR_code);
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
        }
    }
}
