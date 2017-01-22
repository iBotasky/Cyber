package com.botasky.cyberblack.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.botasky.cyberblack.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
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
    private int[] lastVisibleItem;
    private int lastVisibleItemPosition;
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
//        linearLayoutManager = new LinearLayoutManager(mActivity);
//        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        girlsRecyle.setLayoutManager(staggeredGridLayoutManager);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
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
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                staggeredGridLayoutManager.findLastVisibleItemPositions()
                if (lastVisibleItem == null) {
                    lastVisibleItem = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastVisibleItem);
                lastVisibleItemPosition = findMax(lastVisibleItem);
            }
        });

    }

    private int findMax(int[] A) {
        int size = A.length;
        int max = 0;
        for (int i = 0; i < size; i++) {
            max = max >= A[i] ? max : A[i];
        }
        return max;
    }


    private void getData() {
        //Rxjava map是一对一的转换， flatmap是一对多的转换，这里z还需要得到一个list，就可以，所以用map
        girlsSwipeRefresh.setRefreshing(true);
        final List<GirlsResponse.ResultsBean> data = new ArrayList<>();
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.GANK_IO_HOST);
        httpHelper.getService(GirlsApi.class)
                .getGirls(page)
                .subscribeOn(Schedulers.newThread())//指定在新线程创建爱你Observable
                .observeOn(Schedulers.immediate())//指定在当前线程做变换操作
                .flatMap(girlsResponse -> Observable.from(girlsResponse.getResults()))
                .flatMap(resultsBean -> {
                    int[] bounds = ImageUtil.returnBitMapBounds(resultsBean.getUrl());
                    resultsBean.setWith(bounds[0]);
                    resultsBean.setHeight(bounds[1]);
                    return Observable.just(resultsBean);
                })
                .observeOn(AndroidSchedulers.mainThread())//指定在main线程做订阅者操作
                .subscribe(resultsBean -> {
                    data.add(resultsBean);
                }, throwable -> {
                    Log.e("Girls ", " onFailure " + throwable);
                }, () -> {
                    girlsSwipeRefresh.setRefreshing(false);
                    adapter.addMoreItem(data);
                });
        page += 1;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
