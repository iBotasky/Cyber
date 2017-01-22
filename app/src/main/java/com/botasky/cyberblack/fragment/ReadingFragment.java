package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.ZhiHuDailyApi;
import com.botasky.cyberblack.network.response.DailyResponse;
import com.botasky.cyberblack.network.response.DailyStories;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Botasky on 27/11/2016.
 */

public class ReadingFragment extends BaseFragment {


    @BindView(R.id.read_rv)
    RecyclerView readRv;
    @BindView(R.id.read_srl)
    SwipeRefreshLayout readSrl;

    public static ReadingFragment newInstance(String args) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, args);
        ReadingFragment readingFragment = new ReadingFragment();
        readingFragment.setArguments(bundle);
        return readingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        readRv.setHasFixedSize(true);
        //设置刷新图标背景色
        readSrl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置刷新转圈的颜色
        readSrl.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        readSrl.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        readSrl.setRefreshing(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL,false);
        readRv.setLayoutManager(linearLayoutManager);
        getReadDatas();

    }

    private void getReadDatas(){
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.ZHI_HU_HOST);
        httpHelper.getService(ZhiHuDailyApi.class)
                .getLastNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .map(dailyResponse -> dailyResponse.getStories())
                .observeOn(AndroidSchedulers.mainThread())
                //onNExt onThrowable onComplete
                .subscribe(list->{

                }, throwable->{

                }, ()->{

                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reading;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
