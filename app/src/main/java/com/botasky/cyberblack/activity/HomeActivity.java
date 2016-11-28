package com.botasky.cyberblack.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.botasky.cyberblack.R;
import com.botasky.cyberblack.fragment.BaseFragment;
import com.botasky.cyberblack.fragment.FilmFragment;
import com.botasky.cyberblack.fragment.GirlsFragment;
import com.botasky.cyberblack.fragment.ReadingFragment;
import com.botasky.cyberblack.fragment.SplashFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 首页
 * Created by Botasky on 25/11/2016.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.home_drawer)
    DrawerLayout homeDrawer;
    @BindView(R.id.home_tool_bar)
    Toolbar homeToolBar;
    @BindView(R.id.home_tab_layout)
    TabLayout homeTabLayout;
    @BindView(R.id.home_app_bar)
    AppBarLayout homeAppBar;
    @BindView(R.id.home_view_pager)
    ViewPager homeViewPager;
    @BindView(R.id.home_nav)
    NavigationView homeNav;
    @BindView(R.id.home_iv_avater)
    BootstrapCircleThumbnail homeIvAvater;
    @BindView(R.id.home_tv_name)
    TextView homeTvName;

    //Splash的操作
    private Handler mHandler = new Handler();
    private static class DelayRunnable implements Runnable{
        private WeakReference<Context> contextRef;
        private WeakReference<SplashFragment> fragmentRef;

        public DelayRunnable(Context contextRef, SplashFragment fragmentRef) {
            this.fragmentRef = new WeakReference<SplashFragment>(fragmentRef);
            this.contextRef = new WeakReference<Context>(contextRef);
        }

        @Override
        public void run() {
            AppCompatActivity context = (AppCompatActivity) contextRef.get();
            if (context != null) {
                SplashFragment splashFragment = fragmentRef.get();
                if (splashFragment == null)
                    return;
                final FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.remove(splashFragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final SplashFragment splashFragment = new SplashFragment();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_view_continer, splashFragment);
        transaction.commit();
        mHandler.postDelayed(new DelayRunnable(this, splashFragment), 2500);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews() {
        setSupportActionBar(homeToolBar);
        setupDrawerContent();
        //获得ActionBar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.home_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //设置Title不可见
        actionBar.setDisplayShowTitleEnabled(false);
        setupViewPager();

        homeTabLayout.setupWithViewPager(homeViewPager);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                homeDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent() {
        homeNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                switch (item.getItemId()) {
                    case R.id.nav_film:
                        homeViewPager.setCurrentItem(0);
                        homeDrawer.closeDrawers();
                        break;
                    case R.id.nav_reading:
                        homeViewPager.setCurrentItem(1);
                        homeDrawer.closeDrawers();
                        break;
                    case R.id.nav_girls:
                        homeViewPager.setCurrentItem(2);
                        homeDrawer.closeDrawers();
                        break;
                    case R.id.nav_account:
                        break;
                    case R.id.nav_link1:
                        break;
                    case R.id.nav_link2:
                        break;
                }
                return true;
            }
        });

    }


    private void setupViewPager() {
        FilmFragment filmFragment = FilmFragment.newInstance("电影");
        ReadingFragment readingFragment = ReadingFragment.newInstance("阅读");
        GirlsFragment girlsFragment = GirlsFragment.newInstance("妹子");
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(filmFragment);
        adapter.addFragment(readingFragment);
        adapter.addFragment(girlsFragment);
        homeViewPager.setAdapter(adapter);
        homeViewPager.setOffscreenPageLimit(adapter.getCount());
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
