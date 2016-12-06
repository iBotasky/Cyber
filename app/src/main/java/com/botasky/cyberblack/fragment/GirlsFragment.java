package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.adapter.RefreshRecyclerAdapter;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.GirlsApi;
import com.botasky.cyberblack.network.response.GirlsResponse;

import java.util.ArrayList;
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

public class GirlsFragment extends BaseFragment {


    @BindView(R.id.girls_recyle)
    RecyclerView girlsRecyle;
    @BindView(R.id.girls_swipe_refresh)
    SwipeRefreshLayout girlsSwipeRefresh;

    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    RefreshRecyclerAdapter adapter;
    private int lastVisibleItem;
    private int page = 1;


    public static GirlsFragment newInstance(String args) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, args);
        GirlsFragment girlsFragment = new GirlsFragment();
        girlsFragment.setArguments(bundle);
        return girlsFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girls;
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
    protected void initView(View view, Bundle savedInstanceState) {
        girlsRecyle.setHasFixedSize(true);
        //设置刷新图标背景色
        girlsSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置刷新转圈的颜色
        girlsSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        girlsSwipeRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //设置LinearLayoutManager
        linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        girlsRecyle.setLayoutManager(linearLayoutManager);
        //设置Adapter
        girlsRecyle.setAdapter(adapter = new RefreshRecyclerAdapter(mActivity));
        //第一次去访问初始化数据
        getData();
        //设置下拉刷新
        girlsSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 30/11/2016 后面整改逻辑
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<String> newDatas = new ArrayList<String>();
//                        for (int i = 0; i < 5; i++) {
//                            int index = i + 1;
//                            newDatas.add("new item" + index);
//                        }
//                        adapter.addItem(newDatas);
//                        girlsSwipeRefresh.setRefreshing(false);
//                        Toast.makeText(mActivity, "更新了五条数据...", Toast.LENGTH_SHORT).show();
//                    }
//                }, 5000);
                girlsSwipeRefresh.setRefreshing(false);
            }
        });

        //设置RecyleView滑动监听
        girlsRecyle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 当RecyclerView的滑动状态改变时触发
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    adapter.changeLoadStatus(RefreshRecyclerAdapter.LOADING_MORE);
                    getData();
                }
            }

            /**
             * 当RecyclerView滑动时触发
             * 类似点击事件的MotionEvent.ACTION_MOVE
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private void getData() {
        //Rxjava map是一对一的转换， flatmap是一对多的转换，这里z还需要得到一个list，就可以，所以用map
        final List<GirlsResponse.ResultsBean> data = new ArrayList<>();
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.GANK_IO_HOST);
        httpHelper.getService(GirlsApi.class)
                .getGirls(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GirlsResponse, List<GirlsResponse.ResultsBean>>() {
                    @Override
                    public List<GirlsResponse.ResultsBean> call(GirlsResponse girlsResponse) {
                        return girlsResponse.getResults();
                    }
                })
                .subscribe(new Subscriber<List<GirlsResponse.ResultsBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("GetGirls ", "onComplete");
                        adapter.addMoreItem(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("GetGirls ", "onError " + e);

                    }

                    @Override
                    public void onNext(List<GirlsResponse.ResultsBean> resultsBean) {
                        Log.e("GetGirls ", "onNext "  + resultsBean);
                        data.addAll(resultsBean);
                    }
                });
        page += 1;
    }

    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
