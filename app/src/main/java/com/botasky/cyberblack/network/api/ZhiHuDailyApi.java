package com.botasky.cyberblack.network.api;

import com.botasky.cyberblack.network.response.DailyDetailResponse;
import com.botasky.cyberblack.network.response.DailyResponse;

import org.json.JSONObject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Botasky on 21/12/2016.
 */

public interface ZhiHuDailyApi {
    /**
     * 获取最新消息
     * @return
     */
    @GET("/api/4/news/latest")
    Observable<DailyResponse> getLastNews();


    /**
     * 获取单个具体内容
     */
    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<DailyDetailResponse> getNewsDetail(@Path("id") int id);
}
