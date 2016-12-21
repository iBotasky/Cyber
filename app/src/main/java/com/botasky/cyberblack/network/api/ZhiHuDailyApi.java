package com.botasky.cyberblack.network.api;

import com.botasky.cyberblack.network.response.DailyResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Botasky on 21/12/2016.
 */

public interface ZhiHuDailyApi {
    @GET("/api/4/news/latest")
    Observable<DailyResponse> getLastNews();
}
