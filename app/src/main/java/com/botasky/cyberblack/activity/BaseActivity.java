package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Window;

import com.botasky.cyberblack.R;
import com.flyco.systembar.SystemBarHelper;

/**
 * 项目Activity的父类
 * Created by Botasky on 25/11/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set Explode enter transition animation for current activity
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode().setDuration(1000));
        getWindow().setExitTransition(null);
        setContentView(getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //透明状态栏，5.0系统
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow()
//                .getDecorView()
//                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (toolbar != null) {
            SystemBarHelper.immersiveStatusBar(this, 0);
            SystemBarHelper.setHeightAndPadding(this, toolbar);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected abstract void initVariables();

    protected abstract void initViews();

    protected abstract void loadData();

    protected abstract int getLayoutId();


}
