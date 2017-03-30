package com.botasky.cyberblack.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.activity.ReadDetailActivity;
import com.botasky.cyberblack.adapter.ItemClickListener;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.ZhiHuDailyApi;
import com.botasky.cyberblack.network.response.DailyStories;
import com.botasky.cyberblack.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Botasky on 27/11/2016.
 */

public class ReadingFragment extends BaseFragment {
    public static final String TAG = "ReadingFragment";

    @BindView(R.id.read_rv)
    RecyclerView readRv;
    @BindView(R.id.read_srl)
    SwipeRefreshLayout readSrl;


    private ReadAdapter adapter;
    private List<DailyStories> mStories;

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

        getReadDatas();
        readSrl.setOnRefreshListener(() -> {
            getReadDatas();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        readRv.setLayoutManager(linearLayoutManager);


    }

    private void getReadDatas() {
        Log.e("SwipeLayout", " isRefresh " + readSrl.isRefreshing());
        readSrl.setRefreshing(true);
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.ZHI_HU_HOST);
        httpHelper.getService(ZhiHuDailyApi.class)
                .getLastNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .map(dailyResponse -> dailyResponse.getStories())
                .observeOn(AndroidSchedulers.mainThread())
                //onNExt onThrowable onComplete
                .subscribe(list -> {
                    Log.e(TAG, " " + list.size());
                    mStories = new ArrayList<DailyStories>();
                    mStories.addAll(list);
                    adapter = new ReadAdapter(mStories);
                    adapter.setOnItemClickListener(((view, position) -> {
                        startActivity(view, position, list);
                    }));
                    readRv.setAdapter(adapter);
                }, throwable -> {
                    Log.e(TAG, " " + throwable);
                    readSrl.setRefreshing(false);
                }, () -> {
                    Log.e(TAG, " onComplete ");
                    readSrl.setRefreshing(false);
                });
    }

    /**
     * 动画启动
     * @param view
     * @param position
     */
    private void startActivity(View view, int position, List<DailyStories> list){
        View img = view.findViewById(R.id.iv_read_img);
        // set share element transition animation for current activity
        Transition ts = new ChangeTransform();
        ts.setDuration(3000);
        getActivity().getWindow().setExitTransition(ts);

        Intent intent = new Intent(mActivity, ReadDetailActivity.class);
        intent.putExtra(Constant.INTENT_KEY_DAILY_ID, list.get(position).getId());
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                Pair.create(img,"img")).toBundle();
        startActivity(intent, bundle);
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


    class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ViewHolder> {
        LayoutInflater mLayoutInflater;
        List<DailyStories> stories;
        ItemClickListener mClickListener;

        public ReadAdapter(List<DailyStories> list) {
            this.mLayoutInflater = LayoutInflater.from(mActivity);
            this.stories = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.layout_read_item, parent, false), mClickListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DailyStories story = stories.get(position);
            ImageUtil.displayImageByUrl(mActivity, story.getImages().get(0), holder.ivReadImg);
            holder.tvReadTitle.setText(story.getTitle());
        }

        @Override
        public int getItemCount() {
            return stories.size();
        }


        /**
         * 设置Item点击监听
         *
         * @param listener
         */
        public void setOnItemClickListener(ItemClickListener listener) {
            this.mClickListener = listener;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.iv_read_img)
            ImageView ivReadImg;
            @BindView(R.id.tv_read_title)
            TextView tvReadTitle;
//            @BindView(R.id.tv_read_content)
//            TextView tvReadContent;

            ItemClickListener mClickListener;

            ViewHolder(View view, ItemClickListener listener) {
                super(view);
                ButterKnife.bind(this, view);
                mClickListener = listener;
                view.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, getPosition());
                }
            }
        }
    }
}
