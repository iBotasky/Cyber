package com.botasky.cyberblack.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.botasky.cyberblack.R;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.entity.LocWeatherBean;
import com.botasky.cyberblack.fragment.BaseFragment;
import com.botasky.cyberblack.fragment.film.FilmFragment;
import com.botasky.cyberblack.fragment.girl.GirlsFragment;
import com.botasky.cyberblack.fragment.read.ReadingFragment;
import com.botasky.cyberblack.fragment.SplashFragment;
import com.botasky.cyberblack.service.CyberService;
import com.botasky.cyberblack.util.ImageUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

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

    private long mExitTime = 0;

    //Splash的操作
    private Handler mHandler = new Handler();

    private static class DelayRunnable implements Runnable {
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


    //接收的广播
    private Receiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SplashFragment splashFragment = new SplashFragment();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_view_continer, splashFragment);
        transaction.commit();
        mHandler.postDelayed(new DelayRunnable(this, splashFragment), 4000);
        ButterKnife.bind(this);
        initViews();

        //广播初始化
        mReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.WEATHER_BROCAST);
        registerReceiver(mReceiver, intentFilter);


        requestPermission();

    }

    private void requestPermission() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission.request(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted ->{
                    if (granted){
                        //启动后台进程
                        Intent toService = new Intent(HomeActivity.this, CyberService.class);
                        toService.putExtra(CyberService.SERVICE_COMMAND, CyberService.SERVICE_COMMAND_START_LOC_FOR_WEAHTER);
                        startService(toService);
                    }else {

                    }
                });
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
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
    protected int getLayoutId() {
        return R.layout.activity_home;
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
        homeNav.setNavigationItemSelectedListener(item -> {
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
        });

    }


    private void setupViewPager() {
        FilmFragment filmFragment = FilmFragment.newInstance("电影");
//        TestUIFragment testUIFragment = TestUIFragment.newInstance("测试");
        ReadingFragment readingFragment = ReadingFragment.newInstance("知乎");
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


    //获取并展示天气
    private void showWeather(Intent intent) {
        LocWeatherBean locWeatherBean = intent.getParcelableExtra(Constant.WEATHER_KEY);
        if (locWeatherBean != null) {
            LinearLayout home_ll_location = (LinearLayout) homeNav.findViewById(R.id.home_ll_location);
            LinearLayout home_ll_weather = (LinearLayout) homeNav.findViewById(R.id.home_ll_weather);
            TextView home_tv_loc = (TextView) homeNav.findViewById(R.id.home_tv_loc);
            ImageView home_iv_weather = (ImageView) homeNav.findViewById(R.id.home_iv_weather);
            TextView home_tv_weather = (TextView) homeNav.findViewById(R.id.home_tv_weather);

            home_ll_location.setVisibility(View.VISIBLE);
            home_ll_weather.setVisibility(View.VISIBLE);
            home_tv_loc.setText(locWeatherBean.getCity_name());
            ImageUtil.displayWeather(this, locWeatherBean.getImg(), home_iv_weather);
            home_tv_weather.setText(locWeatherBean.getInfo() + " " + locWeatherBean.getTemperature() + "°");
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                // 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                System.exit(0);// 否则退出程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int intentKey = intent.getIntExtra(Constant.BROADCAST_KEY, 0);
            Log.e("HomeReceiver", " onReceiver" + intentKey);
            switch (intentKey) {
                case Constant.WEATHER_BROCAST_KEY:
                    showWeather(intent);
            }
        }
    }
}
