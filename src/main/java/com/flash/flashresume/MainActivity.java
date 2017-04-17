package com.flash.flashresume;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.bottombar)
    BottomBar bottombar;
    private SparseArray<BaseFragment> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sparseArray = new SparseArray<>();

        sparseArray.put(R.id.tab_zhaopin, new ZhaoPinFragment());
        sparseArray.put(R.id.tab_51job, new Job51Fragment());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(curDate);
        String text = "ffffffff-c3d2-f0ef-ffff-ffff9e62b14e_1492058823772_4450\t3\t1\t\t\t\t5\t360yingyong\t " + format + " \t\tc_mobile\t{\"action\":\"app startup\",\"page\":\"com.zhaopin.social.com.zhaopin.social.ui.ZSC_MainTabActivity\"}\t{\"DeviceName\":\"GT-P5210\",\"Resolution\":\"720x1237\",\"DeviceID\":\"ffffffff-c3d2-f0ef-ffff-ffff9e62b14e\",\"AppVersion\":\"6.5.6\",\"Coordinate\":\"39.916213,116.410238\",\"OS\":\"4.2.2\",\"Netware\":\"WIFI\",\"Platform\":\"Android\"}\tffffffff-c3d2-f0ef-ffff-ffff9e62b14e_4968\t";
        SharedPreferences zhaopin = getSharedPreferences("zhaopin", 0);
        SharedPreferences.Editor edit = zhaopin.edit();
        edit.putString("respond",text);
        edit.commit();

        SharedPreferences job51 = getSharedPreferences("51job", 0);
        SharedPreferences.Editor url = job51.edit().putString("url", "http://appapi.51job.com/api/user/refresh_resume.php?accountid=82972818&userid=359125107&key=8c97e040a4c2351bfd740e06eb5ebe6758ef0a4a&productname=51job&partner=23823a9f323530e7789b751aefb62a03&uuid=dd4952a5239347d6a684ca12305458f3&version=710&guid=ba472e7d9ba5b6134cad6f415be24df5");
        url.commit();


        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, sparseArray.get(tabId));

                fragmentTransaction.commit();

            }


        });

    }


}
