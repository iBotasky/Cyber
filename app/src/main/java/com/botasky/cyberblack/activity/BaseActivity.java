package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.botasky.cyberblack.fragment.BaseFragment;

/**
 * 项目Activity的父类
 * Created by Botasky on 25/11/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected abstract void initVariables();

    protected abstract void initViews();

    protected abstract void loadData();
}
