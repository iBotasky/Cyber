package com.botasky.cyberblack.network.api;

import com.botasky.cyberblack.network.response.GirlsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Botasky on 28/11/2016.
 */

public interface GirlsApi {
    @GET("data/福利/20/{index}")
    Observable<GirlsResponse> getGirls(@Path("index") int index);

}
