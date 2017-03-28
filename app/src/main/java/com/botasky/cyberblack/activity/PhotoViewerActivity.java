package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.util.ImageUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by botasky on 28/03/2017.
 */

public class PhotoViewerActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView back;

    private ArrayList<String> urls;
    private int current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        ButterKnife.bind(this);
        initVariables();
        initViews();
    }

    @Override
    protected void initVariables() {
        urls = getIntent().getStringArrayListExtra(Constant.INTENT_KEY_PHOTO_VIEWER_IMG_RULS);
        current = getIntent().getIntExtra(Constant.INTENT_KEY_PHOTO_VIEWER_CURRENT, 0);
    }

    @Override
    protected void initViews() {
        if (urls != null) {

            SamplePagerAdapter adapter = new SamplePagerAdapter();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(current);
        }
    }

    @Override
    protected void loadData() {

    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageUtil.displayImageByUrl(PhotoViewerActivity.this,urls.get(position), photoView);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
