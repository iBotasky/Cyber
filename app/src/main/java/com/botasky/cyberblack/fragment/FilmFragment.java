package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.DouBanApi;
import com.botasky.cyberblack.network.response.FilmsResponse;
import com.botasky.cyberblack.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Botasky on 27/11/2016.
 */

public class FilmFragment extends BaseFragment {

    @BindView(R.id.film_in_theaters_recyle)
    RecyclerView film_in_theaters_recyle;
    @BindView(R.id.film_refresh)
    SwipeRefreshLayout filmRefresh;


    private List<FilmsResponse.SubjectsBean> mFilms = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private Adapter adapter;

    public static FilmFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(bundle);
        return filmFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //swipeRefresh设置
        //设置刷新图标背景色
        filmRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置刷新转圈的颜色
        filmRefresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        filmRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filmRefresh.setRefreshing(false);
            }
        });
        filmRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //RecyleView设置
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        film_in_theaters_recyle.setHasFixedSize(true);
        film_in_theaters_recyle.setLayoutManager(linearLayoutManager);
        film_in_theaters_recyle.setAdapter(adapter = new Adapter());
        getFimsInTheatersData();


    }

    /**
     * 获取正在热映的电影
     */
    private void getFimsInTheatersData() {
        filmRefresh.setRefreshing(true);
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.DOU_BAN_HOST);
        httpHelper.getService(DouBanApi.class)
                .getInTheaters()
                .subscribeOn(Schedulers.io())//指定在io线程做Observeable的创建
                .observeOn(Schedulers.io())//指定在io线程做map转换
                .map(new Func1<FilmsResponse, List<FilmsResponse.SubjectsBean>>() {
                    @Override
                    public List<FilmsResponse.SubjectsBean> call(FilmsResponse filmsResponse) {
                        return filmsResponse.getSubjects();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//指定在main线程做订阅者
                .subscribe(new Action1<List<FilmsResponse.SubjectsBean>>() {
                    @Override
                    public void call(List<FilmsResponse.SubjectsBean> subjectsBeen) {
                        Log.e("Films", "" + subjectsBeen.size());
                        //完成后设置刷新为false
                        mFilms.addAll(subjectsBeen);
                        adapter.notifyDataSetChanged();
                        filmRefresh.setRefreshing(false);
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_film;
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


    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater mLayoutInflater;

        private Adapter() {
            mLayoutInflater = LayoutInflater.from(mActivity);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.item_films_layout, parent, false);
            ItemViewHolder item = new ItemViewHolder(view);
            return item;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageUtil.displayImageByUrl(mActivity, mFilms.get(position).getImages().getLarge(), ((ItemViewHolder) holder).film_cover);
            ((ItemViewHolder) holder).film_name.setText(mFilms.get(position).getTitle());
            ((ItemViewHolder) holder).film_rate.setText(mFilms.get(position).getRating().getAverage() + "");
            ((ItemViewHolder) holder).film_rating_bar.setRating((float) (mFilms.get(position).getRating().getAverage() / 2));
        }

        @Override
        public int getItemCount() {
            return mFilms.size();
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView film_cover;
            TextView film_name;
            TextView film_rate;
            RatingBar film_rating_bar;

            public ItemViewHolder(View itemView) {
                super(itemView);
                film_cover = (ImageView) itemView.findViewById(R.id.film_cover);
                film_name = (TextView) itemView.findViewById(R.id.film_name);
                film_rate = (TextView) itemView.findViewById(R.id.film_rating);
                film_rating_bar = (RatingBar) itemView.findViewById(R.id.film_rating_bar);
            }
        }
    }

}
