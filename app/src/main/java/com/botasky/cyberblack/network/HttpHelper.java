package com.botasky.cyberblack.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.botasky.cyberblack.CyberApplication;
import com.botasky.cyberblack.util.AppUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anthony on 2016/7/8.
 * Class Note:
 * entrance class to access network with {@link Retrofit}
 * used only by  DataManager is recommended
 * <p>
 * 使用retrofit进行网络访问的入口类，推荐只在 DataManager中使用
 */
public class HttpHelper {
    private static final int DEFAULT_TIMEOUT = 30;
    private HashMap<String, Object> mServiceMap;
    private Context mContext;
    private String end_points;
//    private Gson gson = new GsonBuilder().setLenient().create();


    public String getEnd_points() {
        return end_points;
    }

    public void setEnd_points(String end_points) {
        this.end_points = end_points;
    }

    public HttpHelper() {
        //Map used to store RetrofitService
        mServiceMap = new HashMap<>();
        this.mContext = CyberApplication.getInstance().getApplicationContext();
    }


    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    private <S> S createService(Class<S> serviceClass) {
        //custom OkHttp
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //time our
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //cache
        File httpCacheDirectory = new File(mContext.getCacheDir(), "OkHttpCache");
        httpClient.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        //Interceptor
        httpClient.addNetworkInterceptor(new LogInterceptor());
        httpClient.addInterceptor(new CacheControlInterceptor());

        return createService(serviceClass, httpClient.build());
    }

    private <S> S createService(Class<S> serviceClass, OkHttpClient client) {
        String end_point = null;
        if (TextUtils.isEmpty(end_points))
            end_point = "http://service.runmaf.com:8552";
        else
            end_point = end_points;
//        try {
//            Field field1 = serviceClass.getField("end_point");
//            end_point = (String) field1.get(serviceClass);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.getMessage();
//            e.printStackTrace();
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(end_point)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

//            Request request = original.newBuilder()
//                    .header("X-User-Token", GlobalVar.serial == null? "":GlobalVar.serial)
//                    .method(original.method(), original.body())
//                    .build();

            long t1 = System.nanoTime();
            Log.e("HttpHelper" , String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            Log.e("HttpHelper" , String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;

            // log Response Body
//            if(BuildConfig.DEBUG) {
//                String responseBody = response.body().string();
//                Log.v("HttpHelper", String.format("Received response for %s in %.1fms%n%s%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers(), responseBody));
//                return response.newBuilder()
//                        .body(ResponseBody.create(response.body().contentType(), responseBody))
//                        .build();
//            } else {
//                Log.v("HttpHelper", String.format("Received response for %s in %.1fms%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//                return response;
//            }
        }
    }

    private class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!AppUtil.isNetworkAvailable(mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            if (AppUtil.isNetworkAvailable(mContext)) {
                int maxAge = 60 * 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}