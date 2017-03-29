package com.botasky.cyberblack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.botasky.cyberblack.util.ViewAnimUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (iv.getVisibility() == View.VISIBLE) {
            ViewAnimUtil.hideVisiableView(iv);
        } else {
            ViewAnimUtil.showInvisiableView(iv);
        }
    }
}
