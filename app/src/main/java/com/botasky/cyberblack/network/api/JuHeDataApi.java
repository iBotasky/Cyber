package com.botasky.cyberblack.network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 聚合数据的接口
 * Created by Botasky on 18/12/2016.
 */

public interface JuHeDataApi {
    //获取天气的
    @GET("/onebox/weather/query")
    Observable<JsonObject> getWeather(@Query("cityname") String city_name, @Query("key") String key);
}
