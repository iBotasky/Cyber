package com.botasky.cyberblack.util;

import android.content.Context;
import android.widget.ImageView;

import com.botasky.cyberblack.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
}
