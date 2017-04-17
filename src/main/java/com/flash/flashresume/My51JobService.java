package com.flash.flashresume;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by palexe on 2017/4/17.
 */

public class My51JobService extends Service {
    int count = 0;
    private String url;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==101){
                httpUse();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("ulr");
        httpUse();


        return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
    }

    private void httpUse() {
        handler.removeCallbacksAndMessages(null);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url(url)
                .addHeader("User-Agent", "51job-android-client")
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Connection", "close")
                .addHeader("Host", "appapi.51job.com")
                .build();


        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SharedPreferences zhaopincount = getApplication().getSharedPreferences("51job", 0);
                SharedPreferences.Editor count = zhaopincount.edit().putLong("count", -1);
                count.commit();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                count++;
                SharedPreferences zhaopincount = getApplication().getSharedPreferences("51job", 0);
                SharedPreferences.Editor count = zhaopincount.edit().putLong("count", My51JobService.this.count);
                count.commit();
                handler.sendEmptyMessageDelayed(101, 1000 * 60 * 2);
            }
        });


    }
}
