package com.flash.flashresume;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by palexe on 2017/4/17.
 */

public class MyZhaoPinService extends Service {
    public int count = 0;
    private String bodyjson;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String format = simpleDateFormat.format(curDate);
                String text = "ffffffff-c3d2-f0ef-ffff-ffff9e62b14e_1492058823772_4450\t3\t1\t\t\t\t5\t360yingyong\t " + format + " \t\tc_mobile\t{\"action\":\"app startup\",\"page\":\"com.zhaopin.social.com.zhaopin.social.ui.ZSC_MainTabActivity\"}\t{\"DeviceName\":\"GT-P5210\",\"Resolution\":\"720x1237\",\"DeviceID\":\"ffffffff-c3d2-f0ef-ffff-ffff9e62b14e\",\"AppVersion\":\"6.5.6\",\"Coordinate\":\"39.916213,116.410238\",\"OS\":\"4.2.2\",\"Netware\":\"WIFI\",\"Platform\":\"Android\"}\tffffffff-c3d2-f0ef-ffff-ffff9e62b14e_4968\t";
                SharedPreferences zhaopin = getSharedPreferences("zhaopin", 0);
                SharedPreferences.Editor edit = zhaopin.edit();
                edit.putString("respond",text);
                edit.commit();
                bodyjson=text;

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
        bodyjson = intent.getStringExtra("url");
        httpUse();
        return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private void httpUse() {
        handler.removeCallbacksAndMessages(null);
        //Okhttp的使用
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, bodyjson);
        Request request = new Request.Builder().url("http://st.zhaopin.com").post(body)
                .addHeader("Content-Type", "application/html")
                .addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.2.2; GT-P5210 Build/JDQ39E)")
                .addHeader("Host", "st.zhaopin.com")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Content-Length", "453")
                .build();
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SharedPreferences zhaopincount = getApplication().getSharedPreferences("zhaopin", 0);
                SharedPreferences.Editor count = zhaopincount.edit().putLong("count", -1);
                count.commit();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendEmptyMessageDelayed(100, 1000 * 60 * 2);
                count++;
                Log.e("onResponse: ", count + "");
                SharedPreferences zhaopincount = getApplication().getSharedPreferences("zhaopin", 0);
                SharedPreferences.Editor count = zhaopincount.edit().putLong("count", MyZhaoPinService.this.count);
                count.commit();
            }
        });
    }
}
