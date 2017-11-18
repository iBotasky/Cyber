package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.fragment.BaseFragment;
import com.botasky.cyberblack.fragment.photo.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by botasky on 28/03/2017.
 */

public class PhotoViewerActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ArrayList<String> urls;
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (urls != null) {
//            SamplePagerAdapter adapter = new SamplePagerAdapter();
//            viewPager.setAdapter(adapter);
//            viewPager.setCurrentItem(current);
            Adapter adapter = new Adapter(getSupportFragmentManager());
            for (String url : urls){
                PhotoFragment fragment =  PhotoFragment.newInstance(url);
                adapter.addFragment(fragment);
            }
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(current);

        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finishAfterTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_viewer;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<BaseFragment> fragmentList = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(BaseFragment fragment) {
            fragmentList.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList.get(position).getTitle();
        }
    }
}
