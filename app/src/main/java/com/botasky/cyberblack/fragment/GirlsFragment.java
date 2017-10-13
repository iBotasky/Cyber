package com.botasky.cyberblack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.activity.PhotoViewerActivity;
import com.botasky.cyberblack.adapter.RecyclerItemClickListener;
import com.botasky.cyberblack.adapter.RefreshRecyclerAdapter;
import com.botasky.cyberblack.constant.Constant;
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
import rx.android.schedulers.AndroidSchedulers;
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
    private ArrayList<String> urls;


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
        urls = new ArrayList<String>();
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
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        girlsRecyle.setLayoutManager(staggeredGridLayoutManager);
        //设置Adapter
        girlsRecyle.setAdapter(adapter = new RefreshRecyclerAdapter(mActivity));
        //设置点击事件
        girlsRecyle.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), girlsRecyle,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), PhotoViewerActivity.class);
                        intent.putExtra(Constant.INTENT_KEY_PHOTO_VIEWER_CURRENT, position);
                        intent.putStringArrayListExtra(Constant.INTENT_KEY_PHOTO_VIEWER_IMG_RULS, urls);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
                        getContext().startActivity(intent, options.toBundle());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        //第一次去访问初始化数据
        getData();
        //设置下拉刷新
        girlsSwipeRefresh.setOnRefreshListener(() -> {
            getData();
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
        if (girlsSwipeRefresh.isRefreshing())
            return;
        Log.e("Girls", "Start Download");
        girlsSwipeRefresh.setRefreshing(true);
        //Rxjava map是一对一的转换， flatmap是一对多的转换，这里z还需要得到一个list，就可以，所以用map
        final List<GirlsResponse.ResultsBean> data = new ArrayList<>();
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.GANK_IO_HOST);
        httpHelper.getService(GirlsApi.class)
                .getGirls(page)
                .subscribeOn(Schedulers.newThread())//指定在新线程创建爱你Observable
                .observeOn(Schedulers.immediate())//指定在当前线程做变换操作
                .flatMap(girlsResponse -> Observable.from(girlsResponse.getResults()))
                .flatMap(resultsBean -> {
                    urls.add(resultsBean.getUrl());
                    int[] bounds = ImageUtil.returnBitMapBounds(resultsBean.getUrl());
                    resultsBean.setWith(bounds[0]);
                    resultsBean.setHeight(bounds[1]);
                    if (resultsBean.getWith() == 0 || resultsBean.getHeight() == 0){
                        return Observable.just(null);
                    }
                    return Observable.just(resultsBean);
                })
                .filter(resultsBean -> resultsBean != null)
                .observeOn(AndroidSchedulers.mainThread())//指定在main线程做订阅者操作
                .subscribe(resultsBean -> {
                    Log.e("Girls", "onSuccess");
                    data.add(resultsBean);
                }, throwable -> {
                    Log.e("Girls ", " onFailure " + throwable);
                }, () -> {
                    Log.e("Girls", "onComplete");
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
