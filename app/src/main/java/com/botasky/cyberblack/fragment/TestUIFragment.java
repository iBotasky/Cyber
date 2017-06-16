package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.view.ScanView;

import butterknife.ButterKnife;

/**
 * Created by botasky on 27/05/2017.
 */

public class TestUIFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initView(rootView, savedInstanceState);
        return rootView;
    }

    private void test(){
        String b = "Hello, Again";
    }

    

    public static TestUIFragment newInstance(String args) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, args);
        TestUIFragment girlsFragment = new TestUIFragment();
        girlsFragment.setArguments(bundle);
        return girlsFragment;
    }


    protected void initView(View view, Bundle savedInstanceState){
        ScanView scanView = (ScanView) view.findViewById(R.id.scanveiw);
        scanView.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_ui;
    }
}
