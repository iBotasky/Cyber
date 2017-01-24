package com.botasky.cyberblack.biz;

import android.content.Intent;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.botasky.cyberblack.CyberApplication;
import com.botasky.cyberblack.constant.Constant;
import com.botasky.cyberblack.entity.LocWeatherBean;
import com.botasky.cyberblack.network.HttpHelper;
import com.botasky.cyberblack.network.Urls;
import com.botasky.cyberblack.network.api.JuHeDataApi;
import com.google.gson.JsonObject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 定位服务单例
 * Created by Botasky on 17/12/2016.
 */

public class LocationBusiness {
    private AMapLocationClient mLocationClient = null;
    private AMapLocationListener mLocationListener = null;

    //单列
    private LocationBusiness() {
    }

    private static class SingletonHolder {
        private static final LocationBusiness instance = new LocationBusiness();
    }

    public static LocationBusiness getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 开始定位
     */
    public void startLoc() {
        if (mLocationClient == null && mLocationListener == null) {
            mLocationClient = new AMapLocationClient(CyberApplication.getmContext());
            mLocationListener = new LocationListener();
            mLocationClient.setLocationListener(mLocationListener);
            initLocOption();
            mLocationClient.startLocation();
        }
    }

    /**
     * 结束定位
     */
    public void stopLoc() {
        if (mLocationClient != null && mLocationListener != null) {
            mLocationClient.stopLocation();
            mLocationClient.unRegisterLocationListener(mLocationListener);
            mLocationClient = null;
            mLocationListener = null;
        }
    }

    private void initLocOption() {

        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        option.setOnceLocation(true);
        option.setOnceLocationLatest(true);
        option.setNeedAddress(true);
        option.setMockEnable(false);
        mLocationClient.setLocationOption(option);

    }

    private class LocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            // TODO: 18/12/2016 获取天气发送到前台;完成后关掉定位
            Observable.just(aMapLocation.getCity())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.immediate())
                    .flatMap(new Func1<String, Observable<JsonObject>>() {
                        @Override
                        public Observable<JsonObject> call(String s) {
                            HttpHelper httpHelper = new HttpHelper();
                            httpHelper.setEnd_points(Urls.WEATHRE_HOST);
                            return httpHelper.getService(JuHeDataApi.class)
                                    .getWeather(s, Constant.WEATHER_API_KEY);
                        }
                    })
                    .map(jsonObject ->  {
                            LocWeatherBean locWeatherBean = new LocWeatherBean();
                            JsonObject result = jsonObject.getAsJsonObject("result");
                            JsonObject data = result.getAsJsonObject("data");
                            JsonObject realmTime = data.getAsJsonObject("realtime");
                            locWeatherBean.setCity_name(realmTime.get("city_name").getAsString());
                            JsonObject weather = realmTime.getAsJsonObject("weather");
                            locWeatherBean.setImg(Integer.valueOf(weather.get("img").getAsString()));
                            locWeatherBean.setInfo(weather.get("info").getAsString());
                            locWeatherBean.setTemperature(Integer.valueOf(weather.get("temperature").getAsString()));
                            return locWeatherBean;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LocWeatherBean>() {
                        @Override
                        public void onCompleted() {
                            stopLoc();
                        }

                        @Override
                        public void onError(Throwable e) {
                            stopLoc();
                        }

                        @Override
                        public void onNext(LocWeatherBean locWeatherBean) {
                            Log.e("HomeReceiver", " onNext");
                            Intent send = new Intent(Constant.WEATHER_BROCAST);
                            send.putExtra(Constant.WEATHER_KEY, locWeatherBean);
                            send.putExtra(Constant.BROADCAST_KEY,Constant.WEATHER_BROCAST_KEY);
                            CyberApplication.getInstance().getmContext().sendBroadcast(send);
                        }
                    });
        }
    }


}
