package com.botasky.cyberblack.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botasky.cyberblack.activity.BaseActivity;

import butterknife.Unbinder;

/**
 * Created by Botasky on 27/11/2016.
 */

public abstract class BaseFragment extends Fragment {
    protected static final String TITLE = "title";
    protected BaseActivity mActivity;
    protected Unbinder unbinder;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    //获取当前的标题
    public String getTitle() {
        return getArguments().getString(TITLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

}
