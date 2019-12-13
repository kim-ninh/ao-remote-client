package com.ninhhk.aoremote;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ninhhk.aoremote.controling.ActivityClassFactory;
import com.ninhhk.aoremote.database.RemoteManager;
import com.ninhhk.aoremote.model.Remote;
import com.ninhhk.aoremote.selecting.PickDeviceTypeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RemoteAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView rcv_user_remote;
    TextView txt_empty_list;
    private RemoteManager remoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_empty_list = findViewById(R.id.txt_empty_list);
        rcv_user_remote = findViewById(R.id.rcv_user_remote);
        RemoteAdapter adapter = new RemoteAdapter(new ArrayList<Remote>());
        rcv_user_remote.setLayoutManager(new LinearLayoutManager(this));
        rcv_user_remote.setAdapter(adapter);

        new QueryRemotesTask(MainActivity.this).execute();
        new GetWifiInfoTask(MainActivity.this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_device:
                openAddDeviceActivity();
                break;

            case R.id.more:
                openSettingsActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openAddDeviceActivity() {
        Intent intent = new Intent(this, PickDeviceTypeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Remote remote) {
        Class<?> targetActivityClass = ActivityClassFactory.with(getResources())
                .get(remote.getDeviceType());

        Intent intent = new Intent(this, targetActivityClass);
        intent.putExtra(getString(R.string.remoteId), remote.getId());
        startActivity(intent);
    }


    private class QueryRemotesTask extends AsyncTask<Void, Void, List<Remote>> {

        public QueryRemotesTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected List<Remote> doInBackground(Void... voids) {
            return remoteManager.getUserRemotes();
        }

        @Override
        protected void onPostExecute(List<Remote> remotes) {
            if (remotes.isEmpty()) {
                txt_empty_list.setVisibility(View.VISIBLE);
                rcv_user_remote.setVisibility(View.GONE);
            } else {
                txt_empty_list.setVisibility(View.GONE);
                rcv_user_remote.setVisibility(View.VISIBLE);
                rcv_user_remote.setAdapter(new RemoteAdapter(remotes, MainActivity.this));
            }
        }
    }

    private static class GetWifiInfoTask extends AsyncTask<Void, Void, Void> {
        private Context context;

        public GetWifiInfoTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            String server = String.valueOf(dhcpInfo.serverAddress);
            return null;
        }
    }

}
