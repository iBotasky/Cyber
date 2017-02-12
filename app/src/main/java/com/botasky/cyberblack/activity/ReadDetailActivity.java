package com.botasky.cyberblack.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.ZhiHuDailyApi;
import com.botasky.cyberblack.network.response.DailyDetailResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadDetailActivity extends BaseActivity {

    @BindView(R.id.wv_content)
    WebView wvContent;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_detail);
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

    private void loadView(DailyDetailResponse dailyDetailResponse) {
        wvContent.getSettings().setJavaScriptEnabled(true);
        String css = "<link rel=\"stylesheet\" href=\"" + dailyDetailResponse.getCss().get(0) + "\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + dailyDetailResponse.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
//        Log.e("html", css + " daily " + dailyDetailResponse.getCss().get(0));

        wvContent.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }
}
