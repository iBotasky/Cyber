package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.view.View;

import com.botasky.cyberblack.R;

/**
 * Created by Botasky on 27/11/2016.
 */

public class GirlsFragment extends BaseFragment {

    public static GirlsFragment newInstance(String args){
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, args);
        GirlsFragment girlsFragment = new GirlsFragment();
        girlsFragment.setArguments(bundle);
        return girlsFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girls;
    }
}
