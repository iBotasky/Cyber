package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.DouBanApi;
import com.botasky.cyberblack.network.response.SubjectsBean;
import com.botasky.cyberblack.rx.ThreadScheduler;
import com.botasky.cyberblack.util.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Botasky on 27/11/2016.
 */

public class FilmFragment extends BaseFragment {

    @BindView(R.id.film_in_theaters_recyle)
    RecyclerView film_in_theaters_recyle;
    @BindView(R.id.film_coming_soon_recyle)
    RecyclerView filmComingSoonRecyle;
    @BindView(R.id.film_rl_in_theaters)
    LinearLayout filmRlInTheaters;
    @BindView(R.id.film_rl_coming_soon)
    LinearLayout filmRlComingSoon;
    @BindView(R.id.film_top_250_recyle_view)
    RecyclerView filmTop250Recyle;
    @BindView(R.id.film_ll_top_250)
    LinearLayout filmLlTop250;

    private LinearLayoutManager linearLayoutManager;


    public static FilmFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(bundle);
        return filmFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //RecyleView设置
        LinearLayoutManager inTheater = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        film_in_theaters_recyle.setHasFixedSize(true);
        film_in_theaters_recyle.setLayoutManager(inTheater);

        LinearLayoutManager comingsoon = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        filmComingSoonRecyle.setHasFixedSize(true);
        filmComingSoonRecyle.setLayoutManager(comingsoon);

        LinearLayoutManager top250 = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        filmTop250Recyle.setHasFixedSize(true);
        filmTop250Recyle.setLayoutManager(top250);

        getFimsData();
    }

    /**
     * 获取正在热映的电影
     */
    private void getFimsData() {
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.DOU_BAN_HOST);
        httpHelper.getService(DouBanApi.class)
                .getInTheaters()
                .map(filmsResponse -> filmsResponse.getSubjects())
                .compose(bindToLifecycle())
                .compose(ThreadScheduler.applyIOSchedulers())
                .subscribe(subjectsBeen -> {
                    //完成后设置刷新为false
                    filmRlInTheaters.setVisibility(View.VISIBLE);
                    film_in_theaters_recyle.setAdapter(new Adapter(subjectsBeen, Adapter.FILM_TYPE_IN_THEATER));
                });

        httpHelper.getService(DouBanApi.class)
                .getComingSoon(0, 20)
                .map(filmsResponse -> filmsResponse.getSubjects())
                .compose(ThreadScheduler.applyIOSchedulers())
                .compose(bindToLifecycle())
                .subscribe(subjectsBeen -> {
                    filmRlComingSoon.setVisibility(View.VISIBLE);
                    filmComingSoonRecyle.setAdapter(new Adapter(subjectsBeen, Adapter.FILM_TYPE_COMING_SOON));
                }, throwable -> {
                    Log.e("FilmComingSoon", " onFailure " + throwable);
                }, () -> {
                    Log.e("FilmComingSoon", " onComplete");
                });

        httpHelper.getService(DouBanApi.class)
                .getTop250(0, 20)
                .map(filmsResponse -> filmsResponse.getSubjects())
                .compose(ThreadScheduler.applyIOSchedulers())
                .compose(bindToLifecycle())
                .subscribe(subjectsBeen -> {
                    filmLlTop250.setVisibility(View.VISIBLE);
                    filmTop250Recyle.setAdapter(new Adapter(subjectsBeen, Adapter.FILM_TYPE_TOP_250));
                }, throwable -> {
                    Log.e("Film250", " onFailure " + throwable);
                }, () -> {
                    Log.e("Film250", " onComplete ");
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
        private static final int FILM_TYPE_IN_THEATER = 0;
        private static final int FILM_TYPE_COMING_SOON = 1;
        private static final int FILM_TYPE_TOP_250 = 2;

        private LayoutInflater mLayoutInflater;
        private List<SubjectsBean> datas;
        private int film_type;

        private Adapter(List<SubjectsBean> datas, int type) {
            this.mLayoutInflater = LayoutInflater.from(mActivity);
            this.datas = datas;
            this.film_type = type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.layout_film_item, parent, false);
            ItemViewHolder item = new ItemViewHolder(view);
            return item;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
            SubjectsBean bean = datas.get(position);
            ImageUtil.displayImageByUrl(mActivity, bean.getImages().getLarge(), itemViewHolder.film_cover);
            itemViewHolder.film_name.setText(bean.getTitle());

            switch (film_type) {
                case FILM_TYPE_IN_THEATER:
                case FILM_TYPE_TOP_250:
                    itemViewHolder.film_ll_rating.setVisibility(View.VISIBLE);
                    itemViewHolder.film_rate.setText(bean.getRating().getAverage() + "");
                    itemViewHolder.film_rating_bar.setRating((float) (bean.getRating().getAverage() / 2));
                    break;
                case FILM_TYPE_COMING_SOON:
                    itemViewHolder.film_ll_type.setVisibility(View.VISIBLE);
                    String type = "";
                    for (int i = 0; i < bean.getGenres().size(); i++) {
                        type = type + bean.getGenres().get(i);
                        if (i != bean.getGenres().size() - 1)
                            type += "/";
                    }
                    itemViewHolder.film_tv_type.setText(type);
                    break;

            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView film_cover;
            TextView film_name;
            TextView film_rate;
            RatingBar film_rating_bar;
            LinearLayout film_ll_rating;
            LinearLayout film_ll_type;
            TextView film_tv_type;

            public ItemViewHolder(View itemView) {
                super(itemView);
                film_cover = (ImageView) itemView.findViewById(R.id.film_cover);
                film_name = (TextView) itemView.findViewById(R.id.film_name);
                film_ll_rating = (LinearLayout) itemView.findViewById(R.id.film_ll_rating);
                film_rate = (TextView) itemView.findViewById(R.id.film_rating);
                film_rating_bar = (RatingBar) itemView.findViewById(R.id.film_rating_bar);
                film_ll_type = (LinearLayout) itemView.findViewById(R.id.film_ll_type);
                film_tv_type = (TextView) itemView.findViewById(R.id.film_tv_type);

            }
        }
    }

}
