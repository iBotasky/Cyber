package com.botasky.cyberblack.network.api;

import com.botasky.cyberblack.network.response.FilmsResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Botasky on 02/12/2016.
 */

public interface DouBanApi {
    @GET("movie/in_theaters")
    Observable<FilmsResponse> getInTheaters();
}
