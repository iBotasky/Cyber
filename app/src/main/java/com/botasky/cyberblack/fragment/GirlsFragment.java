package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.adapter.RefreshRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        //设置下拉刷新
        girlsSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newDatas = new ArrayList<String>();
                        for (int i = 0; i < 5; i++) {
                            int index = i + 1;
                            newDatas.add("new item" + index);
                        }
                        adapter.addItem(newDatas);
                        girlsSwipeRefresh.setRefreshing(false);
                        Toast.makeText(mActivity, "更新了五条数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<String> newDatas = new ArrayList<String>();
                            for (int i = 0; i < 5; i++) {
                                int index = i + 1;
                                newDatas.add("more item" + index);
                            }
                            adapter.addMoreItem(newDatas);
                            adapter.changeLoadStatus(RefreshRecyclerAdapter.PULLUP_LOAD_MORE);
                        }
                    }, 1000);
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
//                lastVisibleItem = staggeredGridLayoutManager.findLastVisibleItemPositions()
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
