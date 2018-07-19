package com.android.flickerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.flickerapp.DetailActivity;
import com.android.flickerapp.R;
import com.android.flickerapp.model.Photo;
import com.android.flickerapp.utils.ImageSize;
import com.android.flickerapp.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hthumma
 * @Created Date    18-07-2018.
 * @Project FlickerApp
 * @Purpose
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Selection{

        void onGridItemClick(int position);
    }

    private Context mContext;
    private Selection listener ;
    private ArrayList<Photo> itemsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //public ImageView flickrImageView ;
        @BindView(R.id.flickr_image_view)
        protected ImageView flickrImageView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            //flickrImageView   = (ImageView) view.findViewById(R.id.flickr_image_view);

        }
    }


    public GalleryAdapter(Context context, ArrayList<Photo> itemsList) {
        mContext = context;
        this.itemsList = itemsList;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_gallery_row, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myHolder = (MyViewHolder) holder;

        Photo photo = itemsList.get(position);

        ImageUtil.loadImage(mContext, photo.getImageUrl(ImageSize.MEDIUM), myHolder.flickrImageView);

        myHolder.flickrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener != null){
                    listener.onGridItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setListener(Selection listener){
        this.listener = listener ;
    }


}