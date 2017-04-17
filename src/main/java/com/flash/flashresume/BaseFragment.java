package com.flash.flashresume;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by palexe on 2017/4/17.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getViewID(),null);
    }

    public abstract int getViewID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);

    }

    protected abstract void initView(View view);
}
