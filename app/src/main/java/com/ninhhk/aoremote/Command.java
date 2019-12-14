package com.ninhhk.aoremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.preference.PreferenceManager;

import com.ninhhk.aoremote.Utils.ByteArrayUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Command {
    private static final String TAG = Command.class.getSimpleName();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static String server = "";
    private static int port = 1612;
    private static String url;
    protected String cmd = "";
    private static boolean isDebug = false;
    private static int connectionTimeout;
    private static Handler uiThreadHandler = new Handler(Looper.getMainLooper());

    public Command(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        server = sharedPreferences.getString("serverIP", context.getString(R.string.default_server_ip));
        port = Integer.parseInt(sharedPreferences.getString("port", context.getString(R.string.default_server_port)));
        url = String.format("http://%s:%d", server, port);
        isDebug = sharedPreferences.getBoolean("debugMode", false);
        connectionTimeout = Integer.parseInt(sharedPreferences.getString("connectionTimeout", "5"));
    }

    public void start(ResponseListener responseListener) {
        String json = String.format("{\"cmd\": \"%s\"}", cmd);
        performPOSTRequest(json, responseListener);
    }

    public void get(@NonNull ResponseListener listener) {
        String GET_URL = String.format("%s/code", url);

        Request request = new Request.Builder()
                .url(GET_URL)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5L, TimeUnit.SECONDS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error occur: " + e.getMessage());

                uiThreadHandler.post(() -> {
                    listener.onError(e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 404) {
                    uiThreadHandler.post(() -> {
                        listener.onError("Timeout");
                    });
                    return;
                }

                String jsonStr = response.body().string();

                JSONParser jsonParser = new JSONParser(jsonStr);
                String prontonStr = jsonParser.parseCode();

                if (isDebug) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                uiThreadHandler.post(() -> {
                    listener.onReceiveResponse(prontonStr);
                });

                Log.i(TAG, "onResponse: " + jsonStr);
            }
        });
    }

    public void send(byte[] bytes) {
        String hexa = ByteArrayUtils.toHex(bytes);
        send(hexa);
    }

    public void send(String hexa) {
        String json = String.format("{\"code\": \"%s\"}", hexa);
        performPOSTRequest(json, null);
    }

    public void end(ResponseListener responseListener) {
        String json = String.format("{\"cmd\": \"END_%s\"}", cmd);
        performPOSTRequest(json, responseListener);
    }

    private void performPOSTRequest(String json, @Nullable ResponseListener responseListener) {
        RequestBody body = RequestBody.create(JSON, json);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                uiThreadHandler.post(() -> {
                    if (responseListener != null) {
                        responseListener.onError(e.getMessage());
                    }
                });
                Log.e(TAG, "Error occur " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (responseListener != null) {
                    responseListener.onReceiveResponse("Success");
                }
                Log.i(TAG, "onResponse: " + call.toString());
            }
        });
    }

    public interface ResponseListener {

        @UiThread
        void onReceiveResponse(String response);

        @UiThread
        void onError(String error);
    }
}
