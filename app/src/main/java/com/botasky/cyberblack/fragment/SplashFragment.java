package com.botasky.cyberblack.fragment;

import android.os.Bundle;
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
    @BindView(R.id.hello_tv_fenlan)
    TextView helloTvFenlan;
    @BindView(R.id.hello_tv_poland)
    TextView helloTvPoland;
    @BindView(R.id.hello_tv_alb)
    TextView helloTvAlb;
    @BindView(R.id.hello_tv_ael)
    TextView helloTvAel;
    @BindView(R.id.hello_tv_tailan)
    TextView helloTvTailan;
    @BindView(R.id.hello_tv_hsk)
    TextView helloTvHsk;

    private Animation alpha500;
    private Animation alpha1000;
    private Animation alpha1500;
    private Animation alpha2000;
    private Animation alpha2500;
    private Animation alpha3000;
    private Animation alpha3500;
    private Animation alpha4000;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        alpha500 = new AlphaAnimation(0.0f, 1.0f);
        alpha1000 = new AlphaAnimation(0.0f, 1.0f);
        alpha1500 = new AlphaAnimation(0.0f, 1.0f);
        alpha2000 = new AlphaAnimation(0.0f, 1.0f);
        alpha2500 = new AlphaAnimation(0.0f, 1.0f);
        alpha3000 = new AlphaAnimation(0.0f, 1.0f);
        alpha3500 = new AlphaAnimation(0.0f, 1.0f);
        alpha4000 = new AlphaAnimation(0.0f, 1.0f);


        alpha500.setDuration(500);
        alpha1000.setDuration(1000);
        alpha1500.setDuration(1500);
        alpha2000.setDuration(2000);
        alpha2500.setDuration(2500);
        alpha3000.setDuration(3000);
        alpha3500.setDuration(3500);
        alpha4000.setDuration(4000);


        helloTvSp.startAnimation(alpha500);
        helloTvGermany.startAnimation(alpha1000);
        helloTvRussuia.startAnimation(alpha2000);
        helloTvFrance.startAnimation(alpha4000);
        helloTvCn.startAnimation(alpha4000);
        helloTvKorea.startAnimation(alpha3000);
        helloTvWorld.startAnimation(alpha1500);
        helloTvJpan.startAnimation(alpha2500);
        helloTvFenlan.startAnimation(alpha1500);
        helloTvTailan.startAnimation(alpha3500);
        helloTvHsk.startAnimation(alpha2500);
        helloTvAlb.startAnimation(alpha3500);
        helloTvPoland.startAnimation(alpha500);
        helloTvAel.startAnimation(alpha1000);
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
