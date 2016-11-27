package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.view.View;

import com.botasky.cyberblack.R;

/**
 * Created by Botasky on 27/11/2016.
 */

public class FilmFragment extends BaseFragment {

    public static FilmFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(bundle);
        return filmFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_film;
    }
}
