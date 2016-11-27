package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.util.ImageUtil;

/**
 * Created by Botasky on 27/11/2016.
 */

public class FilmFragment extends BaseFragment {

    public static FilmFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(bundle);
        return filmFragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ImageView imageView = ((ImageView) view.findViewById(R.id.film_test));
//        ImageUtil.displayGifByUrl(getActivity(),"http://ww2.sinaimg.cn/large/610dc034jw1fa42ktmjh4j20u011hn8g.jpg", imageView);
        ImageUtil.displayGifByUrl(getActivity(),"http://qq.yh31.com/ka/qw/84674.html",imageView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_film;
    }
}
