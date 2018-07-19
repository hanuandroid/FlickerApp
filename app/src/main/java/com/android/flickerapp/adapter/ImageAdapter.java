package com.android.flickerapp.adapter;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.flickerapp.R;
import com.android.flickerapp.model.Photo;
import com.android.flickerapp.utils.ImageSize;
import com.android.flickerapp.utils.ImageUtil;
import com.android.flickerapp.utils.TouchImageView;

public class ImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<Photo> photoList ;
    private LayoutInflater inflater;


    public ImageAdapter(Activity activity,
                        ArrayList<Photo> photoList) {
        this._activity = activity;
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        return this.photoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.view_image_row, container,
                false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

        Photo photo = photoList.get(position);

        Context context = _activity.getApplicationContext();

        ImageUtil.loadImage(context, photo.getImageUrl(ImageSize.MEDIUM), imgDisplay);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
