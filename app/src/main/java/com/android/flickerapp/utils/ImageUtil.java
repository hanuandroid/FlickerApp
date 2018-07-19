package com.android.flickerapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * @author hthumma
 * @Created Date    18-07-2018.
 * @Project FlickerApp
 * @Purpose
 */
public class ImageUtil {

    public static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

    public static void loadImage(Context mContext , String imageUrl , ImageView imageView){

        if(mContext != null && imageUrl != null && imageView != null){

            if(imageUrl.length() > 0){
                Glide.with(mContext).load(imageUrl)
                        .thumbnail(0.5f)
                        .into(imageView);
            }

        }


    }
}
