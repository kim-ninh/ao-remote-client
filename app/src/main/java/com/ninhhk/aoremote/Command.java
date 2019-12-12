package com.ninhhk.aoremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Command implements Callback {
    private static final String TAG = Command.class.getSimpleName();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static String server = "";
    private static int port = 1612;
    private static String url;
    protected String cmd = "";

    public Command(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        server = sharedPreferences.getString("serverIP", context.getString(R.string.default_server_ip));
        port = Integer.parseInt(sharedPreferences.getString("port", context.getString(R.string.default_server_port)));
        url = String.format("http://%s:%d", server, port);
    }

    public void start() {
        String json = String.format("{\"cmd\": \"%s\"}", cmd);
        performPOSTRequest(json);
    }

    public void send(byte[] bytes) {
        String hexa = ByteArrayUtils.toHex(bytes);
        String json = String.format("{\"code\": \"%s\"}", hexa);
        performPOSTRequest(json);
    }

    public void end() {
        String json = String.format("{\"cmd\": \"END_%s\"}", cmd);
        performPOSTRequest(json);
    }

    private void performPOSTRequest(String json) {
        RequestBody body = RequestBody.create(JSON, json);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.i(TAG, "onResponse: " + call.toString());
    }
}
