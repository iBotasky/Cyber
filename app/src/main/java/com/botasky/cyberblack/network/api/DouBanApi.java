package com.botasky.cyberblack.network.api;

import com.botasky.cyberblack.network.response.FilmsResponse;
import com.google.gson.JsonObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Botasky on 02/12/2016.
 */

public interface DouBanApi {
    @GET("movie/in_theaters")
    Observable<FilmsResponse> getInTheaters();

    @GET("movie/coming_soon")
    Observable<FilmsResponse> getComingSoon(@Query("start") int start, @Query("count") int count);//Get用参数Query来做

    @GET("movie/top250")
    Observable<FilmsResponse> getTop250(@Query("start") int start, @Query("count") int connt);
}
