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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.DouBanApi;
import com.botasky.cyberblack.network.response.FilmsResponse;
import com.botasky.cyberblack.network.response.SubjectsBean;
import com.botasky.cyberblack.util.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
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
    @BindView(R.id.film_coming_soon_recyle)
    RecyclerView filmComingSoonRecyle;
    @BindView(R.id.film_rl_in_theaters)
    LinearLayout filmRlInTheaters;
    @BindView(R.id.film_rl_coming_soon)
    LinearLayout filmRlComingSoon;

    //    private List<SubjectsBean> mFilms = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
//    private Adapter adapter;


    public static FilmFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(bundle);
        return filmFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        adapter = new Adapter();
        //RecyleView设置
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        film_in_theaters_recyle.setHasFixedSize(true);
        film_in_theaters_recyle.setLayoutManager(linearLayoutManager);

        LinearLayoutManager comingsoon = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        filmComingSoonRecyle.setHasFixedSize(true);
        filmComingSoonRecyle.setLayoutManager(comingsoon);
//        film_in_theaters_recyle.setAdapter(adapter);
        getFimsInTheatersData();


    }

    /**
     * 获取正在热映的电影
     */
    private void getFimsInTheatersData() {
//        filmRefresh.setRefreshing(true);
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.DOU_BAN_HOST);
        httpHelper.getService(DouBanApi.class)
                .getInTheaters()
                .subscribeOn(Schedulers.io())//指定在io线程做Observeable的创建
                .observeOn(Schedulers.io())//指定在io线程做map转换
                .map(new Func1<FilmsResponse, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(FilmsResponse filmsResponse) {
                        return filmsResponse.getSubjects();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//指定在main线程做订阅者
                .subscribe(new Action1<List<SubjectsBean>>() {
                    @Override
                    public void call(List<SubjectsBean> subjectsBeen) {
                        Log.e("Films", "" + subjectsBeen.size());
                        //完成后设置刷新为false
                        filmRlInTheaters.setVisibility(View.VISIBLE);
                        film_in_theaters_recyle.setAdapter(new Adapter(subjectsBeen, Adapter.FILM_TYPE_IN_THEATER));
                    }
                });

        httpHelper.getService(DouBanApi.class)
                .getComingSoon(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<FilmsResponse, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(FilmsResponse filmsResponse) {
                        return filmsResponse.getSubjects();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SubjectsBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("FilmComingSoon", " onCOmplete" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("FilmComingSoon", " ERROR " + e);
                    }

                    @Override
                    public void onNext(List<SubjectsBean> subjectsBeen) {
                        Log.e("FilmComingSoon", " " + subjectsBeen.size());
                        filmRlComingSoon.setVisibility(View.VISIBLE);
                        filmComingSoonRecyle.setAdapter(new Adapter(subjectsBeen, Adapter.FILM_TYPE_COMING_SOON));
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
                    for (int i = 0; i < bean.getGenres().size(); i ++) {
                        type = type + bean.getGenres().get(i);
                        if (i != bean.getGenres().size()-1)
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


//    class FilmSection extends StatelessSection {
//        final static int TYPE_IN_THEATER = 0;//正在热映
//        final static int TYPE_COMING_SOON = 1;//即将上映
//        final static int TYPE_TOP_250 = 2;//TOP250
//
//        private List<SubjectsBean> list;
//        private int type;
//
//
//        public FilmSection(List<SubjectsBean> datas, int type) {
//            super(R.layout.layout_film_head, R.layout.layout_film_item);
//            this.type = type;
//            this.list = datas;
//        }
//
//        @Override
//        public int getContentItemsTotal() {
//            return list.size();
//        }
//
//        @Override
//        public RecyclerView.ViewHolder getItemViewHolder(View view) {
//            return new ItemViewHolder(view);
//        }
//
//        @Override
//        public RecyclerView.ViewHolder getHeaderViewHolder(View view)
//        {
//
//            return new HeadViewHolder(view);
//        }
//
//        @Override
//        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//            SubjectsBean bean = list.get(position);
//            ImageUtil.displayImageByUrl(mActivity, bean.getImages().getLarge(), itemViewHolder.filmCover);
//            itemViewHolder.filmName.setText(bean.getTitle());
//            itemViewHolder.filmRating.setText(bean.getRating().getAverage() + "");
//            itemViewHolder.filmRatingBar.setRating(((float) (bean.getRating().getAverage() / 2)));
////            itemViewHolder.filmNamesetText(mFilms.get(position).getTitle());
////            setText(mFilms.get(position).getRating().getAverage() + "");
////            setRating((float) (mFilms.get(position).getRating().getAverage() / 2));
//        }
//
//        @Override
//        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
//            HeadViewHolder headViewHolder = ((HeadViewHolder) holder);
//            switch (type){
//                case TYPE_IN_THEATER:
//                    headViewHolder.filmHeadTvTitle.setText("正在热映");
//                    headViewHolder.filmHeadTvTitle2.setText("厦门");
//                    break;
//                default:
//                    headViewHolder.filmHeadTvTitle.setText("正在热映");
//                    headViewHolder.filmHeadTvTitle2.setText("厦门");
//                    break;
//            }
//        }
//
//
//        class HeadViewHolder extends RecyclerView.ViewHolder{
//            @BindView(R.id.film_head_tv_title)
//            BootstrapLabel filmHeadTvTitle;
//            @BindView(R.id.film_head_tv_title2)
//            BootstrapLabel filmHeadTvTitle2;
//
//            HeadViewHolder(View view) {
//                super(view);
//                ButterKnife.bind(this, view);
//            }
//        }
//
//        class ItemViewHolder extends RecyclerView.ViewHolder{
//            @BindView(R.id.film_cover)
//            ImageView filmCover;
//            @BindView(R.id.film_name)
//            TextView filmName;
//            @BindView(R.id.film_rating_bar)
//            RatingBar filmRatingBar;
//            @BindView(R.id.film_rating)
//            TextView filmRating;
//
//            ItemViewHolder(View view) {
//                super(view);
//                ButterKnife.bind(this, view);
//            }
//        }
//    }

}
