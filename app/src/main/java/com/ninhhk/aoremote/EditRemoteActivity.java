package com.ninhhk.aoremote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;

public class EditRemoteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_device_name;
    private Button btn_save;
    private Remote remote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remote);

        edt_device_name = findViewById(R.id.edt_device_name);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);

        long remoteId = getIntent().getLongExtra(getString(R.string.remoteId), -1);
        new LoadRemoteTask(EditRemoteActivity.this)
                .execute(remoteId);
    }

    @Override
    public void onClick(View v) {
        String deviceName = edt_device_name.getText().toString();
        remote.setName(deviceName);

        new UpdateRemoteTask(EditRemoteActivity.this)
                .execute(remote);
    }

    private void openMainActivity() {
        Intent intent = new Intent(EditRemoteActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class LoadRemoteTask extends AsyncTask<Long, Void, Remote> {

        private RemoteManager remoteManager;

        public LoadRemoteTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected Remote doInBackground(Long... longs) {
            long remoteId = longs[0];
            return remoteManager.getRemote(remoteId);
        }

        @Override
        protected void onPostExecute(Remote remote) {
            super.onPostExecute(remote);

            String defaultDeviceName = remote.getBrand() + remote.getDeviceType();
            edt_device_name.setText(defaultDeviceName);
            EditRemoteActivity.this.remote = remote;
        }
    }

    private class UpdateRemoteTask extends AsyncTask<Remote, Void, Void> {

        private RemoteManager remoteManager;

        public UpdateRemoteTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected Void doInBackground(Remote... remotes) {
            remoteManager.updateRemote(remotes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            openMainActivity();
        }
    }
}
