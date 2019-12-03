package com.ninhhk.aoremote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ninhhk.aoremote.database.RemoteManager;

public class RemoteBrandListActivity extends BackableActivity
        implements BrandAdapter.OnBrandItemClickListenter {

    private RecyclerView rcv_remote_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_brand_list);

        rcv_remote_brand = findViewById(R.id.rcv_remote_brand);
        rcv_remote_brand.setLayoutManager(new LinearLayoutManager(RemoteBrandListActivity.this));
        rcv_remote_brand.setAdapter(new BrandAdapter(new String[0], this));


        String remote_type = getIntent().getStringExtra(getString(R.string.remote_type));
        String appTitle = String.format("Select %s", remote_type);
        getSupportActionBar().setTitle(appTitle);
        new QueryRemoteBrandTask(RemoteBrandListActivity.this)
                .execute(remote_type);
    }

    @Override
    public void onBrandItemClick(String brandName) {

        Intent intent = getIntent();
        intent.setClass(RemoteBrandListActivity.this, TestRemoteActivity.class);
        intent.putExtra(getString(R.string.brand_name), brandName);
        startActivity(intent);
    }


    private class QueryRemoteBrandTask extends AsyncTask<String, Void, String[]> {

        private RemoteManager remoteManager;

        public QueryRemoteBrandTask(Context context) {
            remoteManager = RemoteManager.get(context);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            String remote_type = strings[0];
            return remoteManager.getBrands(remote_type);
        }

        @Override
        protected void onPostExecute(String[] brands) {
            super.onPostExecute(brands);
            rcv_remote_brand.setAdapter(new BrandAdapter(brands, RemoteBrandListActivity.this));
        }
    }


}
