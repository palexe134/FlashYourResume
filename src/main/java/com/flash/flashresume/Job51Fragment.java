package com.flash.flashresume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by palexe on 2017/4/17.
 */

public class Job51Fragment extends BaseFragment {
    @BindView(R.id.edittext)
    AppCompatEditText edittext;
    @BindView(R.id.textrespond)
    TextView textrespond;
    @BindView(R.id.textresule)
    TextView textresule;
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;
    private SharedPreferences sharedPreferences;

    @Override
    public int getViewID() {
        return R.layout.fragment_51job;
    }


    @Override
    public void onResume() {
        super.onResume();
        initdate();
    }

    @Override
    protected void initView(View view) {
        initdate();
    }

    private void initdate() {
        sharedPreferences = getActivity().getSharedPreferences("51job", 0);
        String url = sharedPreferences.getString("url", "");
        textrespond.setText(url);
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

    @OnClick(R.id.button)
    public void onViewClicked() {
        String str = edittext.getText().toString();
        if (!TextUtils.isEmpty(str)) {
            sharedPreferences.edit().putString("url", "").commit();
            textrespond.setText(str);
        }

        int count = (int) sharedPreferences.getLong("count", 0);

        if (count == -1) {
            textresule.setText("result:" + "刷新失败");
        } else {
            textresule.setText("result:" + count + "次刷新");
        }
        String s = textrespond.getText().toString();
        Intent intent = new Intent(getContext(), My51JobService.class);
        intent.putExtra("ulr", s);
        getActivity().startService(intent);


    }
}
