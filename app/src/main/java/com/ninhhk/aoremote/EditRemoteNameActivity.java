package com.ninhhk.aoremote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ninhhk.aoremote.database.RemoteManager;

public class EditRemoteNameActivity extends BackableActivity implements View.OnClickListener {

    private EditText edt_device_name;
    private Button btn_save;
    private long remoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remote);

        edt_device_name = findViewById(R.id.edt_device_name);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);

        remoteId = getIntent().getLongExtra(getString(R.string.remoteId), -1);

        new LoadRemoteName(EditRemoteNameActivity.this, (remoteName) -> {
            edt_device_name.setText(remoteName);
        })
                .execute(remoteId);
    }

    @Override
    public void onClick(View v) {
        String deviceName = edt_device_name.getText().toString();

        new UpdateRemoteName(EditRemoteNameActivity.this, remoteId, (r) -> openMainActivity())
                .execute(deviceName);
    }

    private void openMainActivity() {
        Intent intent = new Intent(EditRemoteNameActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private static class LoadRemoteName extends AsyncTask<Long, Void, String> {

        private RemoteManager remoteManager;
        private AsyncTaskCallback<String> callback;

        public LoadRemoteName(Context context, AsyncTaskCallback<String> callback) {
            remoteManager = RemoteManager.get(context);
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Long... longs) {
            long remoteId = longs[0];
            return remoteManager.getRemoteName(remoteId);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onComplete(s);
        }
    }

    private static class UpdateRemoteName extends AsyncTask<String, Void, Void> {

        private final long remoteId;
        private RemoteManager remoteManager;
        private AsyncTaskCallback<Void> callback;

        public UpdateRemoteName(Context context, long remoteId, AsyncTaskCallback<Void> callback) {
            remoteManager = RemoteManager.get(context);
            this.remoteId = remoteId;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String remoteName = strings[0];
            remoteManager.updateRemoteName(remoteId, remoteName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.onComplete(aVoid);
        }
    }
}
