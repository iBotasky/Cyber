package com.botasky.cyberblack.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.ImageView;

import com.botasky.cyberblack.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 显示图片相关操作
 * Created by Botasky on 27/11/2016.
 */

public class ImageUtil {
    /**
     * 通过URL显示图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImageByUrl(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.default_image)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .crossFade()
                .into(imageView);

    }

    /**
     * 通过URL显示图片
     * @param context
     * @param url
     * @param imageView
     * @param with  需要调整的宽度
     * @param height 需要调整的高度
     */
    public static void displayImageByUrl(Context context, String url, ImageView imageView, int with, int height) {
        Glide
                .with(context)
                .load(url)
                .override(with,height)
                .placeholder(R.drawable.default_image)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .crossFade()
                .into(imageView);

    }


    /**
     * 通过URL播放GIF
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayGifByUrl(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        //.into(new GlideDrawableImageViewTarget(mImageView, 1));播放一次用这个
    }


    /**
     * return a bitmap bounds from service
     * @param url
     * @return bitmap with and height
     */
    public final static int[] returnBitMapBounds(String url) {
        URL myFileUrl = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        int[] bounds = new int[]{0,0};
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();

            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            BitmapFactory.decodeStream(is, null, options);
            bounds[0] = options.outWidth;
            bounds[1] = options.outHeight;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bounds;
    }



    public static void displayWeather(Context context, int img, ImageView imageView){
        int resourceId = 0;
        switch (img){
            case 0:
                resourceId = R.drawable.w_sunny;
                break;
            case 1:
                resourceId = R.drawable.w_cloundy;
                break;
            case 2:
                resourceId = R.drawable.w_yin;
                break;
            case 3:
            case 4:
                resourceId = R.drawable.w_rain_thunder;
                break;
            case 5:
            case 19:
                resourceId = R.drawable.w_rain_bao;
                break;
            case 6:
                resourceId = R.drawable.w_rain_snow;
                break;
            case 7:
                resourceId = R.drawable.w_rain_s;
                break;
            case 8:
            case 21:
                resourceId = R.drawable.w_rain_m;
                break;
            case 9:
            case 22:
                resourceId = R.drawable.w_rain_l;
                break;
            case 10:
            case 23:
                resourceId = R.drawable.w_rain_xl;
                break;
            case 11:
            case 12:
            case 24:
            case 25:
                resourceId = R.drawable.w_rain_xxl;
                break;
            case 13:
            case 15:
            case 27:
                resourceId = R.drawable.w_snow_m;
                break;
            case 14:
            case 26:
                resourceId = R.drawable.w_snow_s;
                break;
            case 16:
            case 17:
            case 28:
                resourceId = R.drawable.w_snow_l;
                break;
            case 18:
                resourceId = R.drawable.w_fog;
                break;
            case 20:
                resourceId = R.drawable.w_sand;
                break;
            case 53:
                resourceId = R.drawable.w_fog_big;
                break;
            default:
                resourceId = R.drawable.w_unknow;
                break;
        }

        Glide.with(context)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
