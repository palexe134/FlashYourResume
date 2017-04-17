package com.flash.flashresume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;

/**
 * Created by palexe on 2017/4/17.
 */

public class ZhaoPinFragment extends BaseFragment {
    @BindView(R.id.edittext)
    AppCompatEditText edittext;
    @BindView(R.id.textrespond)
    TextView textrespond;
    @BindView(R.id.textresule)
    TextView textresule;
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;
    private SharedPreferences zhaopin;


    @Override
    public int getViewID() {
        return R.layout.fragment_zhaopin;
    }

    @Override
    protected void initView(View view) {
        //将view绑定到this上
        ButterKnife.bind(this, view);
        initDate();
    }

    private void initDate() {
        zhaopin = getActivity().getSharedPreferences("zhaopin", 0);
        String requsetBody = zhaopin.getString("respond","");

        textrespond.setText(requsetBody);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initDate();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {


        String trim = edittext.getText().toString().trim();
        if(!TextUtils.isEmpty(trim)){
            textrespond.setText(trim);
            SharedPreferences.Editor respond1 = zhaopin.edit().putString("respond",trim);

        }else{
            initDate();
        }
        long count = zhaopin.getLong("count",0);
        textresule.setText("result:"+count+"次刷新");


        Intent service = new Intent(getContext(), MyZhaoPinService.class);
        String data = textrespond.getText().toString();
//        Log.e("onViewClicked: ",data+"" );
        service.putExtra("url", data);
        getActivity().startService(service);


    }
}
