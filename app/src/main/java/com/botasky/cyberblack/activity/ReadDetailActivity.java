package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.ZhiHuDailyApi;
import com.botasky.cyberblack.network.response.DailyDetailResponse;
import com.botasky.cyberblack.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadDetailActivity extends BaseActivity {

    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.iv_read_bg)
    ImageView ivBackground;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collaspToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initVariables();
        loadData();
    }

    @Override
    protected void initVariables() {


        id = getIntent().getIntExtra(Constant.INTENT_KEY_DAILY_ID, 0);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {
        HttpHelper httpHelper = new HttpHelper();
        httpHelper.setEnd_points(Urls.ZHI_HU_HOST);
        httpHelper.getService(ZhiHuDailyApi.class)
                .getNewsDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyDetailResponse -> {
                    loadView(dailyDetailResponse);
                }, throwable -> {

                }, () -> {

                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_detail;
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

    private void loadView(DailyDetailResponse dailyDetailResponse) {
        ivBackground.setTransitionName("img");
        ImageUtil.displayImageByUrl(this,dailyDetailResponse.getImage(),ivBackground);
        collaspToolbar.setTitle(dailyDetailResponse.getTitle());
        wvContent.getSettings().setJavaScriptEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String css = "<link rel=\"stylesheet\" href=\"" + dailyDetailResponse.getCss().get(0) + "\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + dailyDetailResponse.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");

        wvContent.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }


}
