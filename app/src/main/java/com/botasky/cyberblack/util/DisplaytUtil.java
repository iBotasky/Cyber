package com.botasky.cyberblack.util;

import android.content.Context;
import android.view.WindowManager;

import com.botasky.cyberblack.CyberApplication;

/**
 * 一些屏幕的操作
 * Created by Botasky on 15/12/2016.
 */

public class DisplaytUtil {
    /**
     * 获得屏幕宽度
     * @return
     */
    public static int getScreenWith(){
        WindowManager wm = (WindowManager) CyberApplication.getmContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
}
