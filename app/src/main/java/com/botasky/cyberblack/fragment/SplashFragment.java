package com.botasky.cyberblack.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.botasky.cyberblack.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Botasky on 27/11/2016.
 */

public class SplashFragment extends BaseFragment {

    @BindView(R.id.hello_tv_sp)
    TextView helloTvSp;
    @BindView(R.id.hello_tv_cn)
    TextView helloTvCn;
    @BindView(R.id.hello_tv_france)
    TextView helloTvFrance;
    @BindView(R.id.hello_tv_korea)
    TextView helloTvKorea;
    @BindView(R.id.hello_tv_jpan)
    TextView helloTvJpan;
    @BindView(R.id.hello_tv_germany)
    TextView helloTvGermany;
    @BindView(R.id.hello_tv_en)
    TextView helloTvEn;
    @BindView(R.id.hello_tv_world)
    TextView helloTvWorld;
    @BindView(R.id.hello_tv_russuia)
    TextView helloTvRussuia;
    @BindView(R.id.imageView)
    ImageView imageView;

    private Animation tvSpAnim;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvSpAnim = new AlphaAnimation(0.0f,1.0f);
        tvSpAnim.setDuration(4000);
        helloTvSp.startAnimation(tvSpAnim);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
    // create ContextThemeWrapper from the original Activity Context with the custom theme
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.splash_theme);
//
//        // clone the inflater using the ContextThemeWrapper
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
//
//        // inflate the layout using the cloned inflater, not default inflater
//        return localInflater.inflate(getLayoutId(), container, false);

//        // create ContextThemeWrapper from the original Activity Context with the custom theme
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.splash_theme);
//
//        // clone the inflater using the ContextThemeWrapper
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
//
//        // inflate the layout using the cloned inflater, not default inflater
//        return localInflater.inflate(getLayoutId(), null, false);
//    }

}
