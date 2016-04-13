package com.interest.myapplication.view.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Android on 2016/3/5.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
