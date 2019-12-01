package com.ninhhk.aoremote;

import android.util.Log;

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
    private static String server = "192.168.84.11";
    private static int port = 1612;
    private static String url = String.format("http://%s:%d", server, port);
    protected String cmd = "";


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
