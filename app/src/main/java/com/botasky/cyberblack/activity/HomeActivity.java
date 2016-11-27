package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Adapter;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.fragment.BaseFragment;
import com.botasky.cyberblack.fragment.FilmFragment;
import com.botasky.cyberblack.fragment.GirlsFragment;
import com.botasky.cyberblack.fragment.ReadingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Botasky on 25/11/2016.
 */

public class HomeActivity extends BaseActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.home_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupViewPager();

        homeTabLayout.setupWithViewPager(homeViewPager);
    }

    @Override
    protected void loadData() {

    }

    private void setupDrawerContent(){
        homeNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                switch (item.getItemId()){
                    case R.id.nav_film:
                        break;
                    case R.id.nav_reading:
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


    private void setupViewPager(){
        FilmFragment filmFragment = FilmFragment.newInstance("电影");
        ReadingFragment readingFragment = ReadingFragment.newInstance("阅读");
        GirlsFragment girlsFragment = GirlsFragment.newInstance("妹子");
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(filmFragment);
        adapter.addFragment(readingFragment);
        adapter.addFragment(girlsFragment);
        homeViewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter{
        private final List<BaseFragment> fragmentList = new ArrayList<>();

        public Adapter(FragmentManager fm){
            super(fm);
        }

        public void addFragment(BaseFragment fragment){
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
