package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.view.View;

import com.botasky.cyberblack.R;

/**
 * Created by Botasky on 27/11/2016.
 */

public class ReadingFragment extends BaseFragment {

    public static ReadingFragment newInstance(String args){
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, args);
        ReadingFragment readingFragment = new ReadingFragment();
        readingFragment.setArguments(bundle);
        return readingFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reading;
    }
}
