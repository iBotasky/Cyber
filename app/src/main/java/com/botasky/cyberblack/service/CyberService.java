package com.botasky.cyberblack.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.botasky.cyberblack.biz.LocationBusiness;

/**
 * 后台进程
 * Created by Botasky on 17/12/2016.
 */

public class CyberService extends Service {
    public static final String SERVICE_COMMAND = "SERVICE_COMMAND";
    public static final int SERVICE_COMMAND_START_LOC_FOR_WEAHTER = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command_id = intent.getIntExtra(SERVICE_COMMAND, 0);
        switch (command_id){
            case SERVICE_COMMAND_START_LOC_FOR_WEAHTER:
                Log.e("Location ", " onLocWeather");
                startLocForWeather();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 开始定位并且去拿天气数据
     */
    private void startLocForWeather() {
        LocationBusiness.getInstance().startLoc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
