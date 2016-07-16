package com.example.deepanshu.loktraflicker.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.deepanshu.loktraflicker.R;
import com.example.deepanshu.loktraflicker.model.FlickrImage;

import java.util.List;

/**
 * Created by deepanshu on 14/7/16.
 */
public class FlickrAdapter extends RecyclerView.Adapter<FlickrAdapter.ViewHolder> {

    private List<FlickrImage> images;
    private Context mContext;

    public FlickrAdapter(Context context, List<FlickrImage> images) {
        this.images = images;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flickr_thumbnail, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        FlickrImage flickrImage = images.get(position);

        Glide.with(mContext)
                .load(flickrImage.getImage())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.titl.setText(flickrImage.getTitle());
        holder.email.setText(flickrImage.getAuthor());
        holder.date.setText(flickrImage.getDate());
        holder.published.setText(flickrImage.getPublished());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titl, email, date, published;
        public ImageView thumbnail;
        public LinearLayout thumbnail2;
        public FrameLayout frameLayout;
        public AnimatorSet setRightIn, setRightOut, setLeftIn, setLeftOut;
        boolean isBackVisible = false;

        public ViewHolder(View itemView) {
            super(itemView);

            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            thumbnail2 = (LinearLayout) itemView.findViewById(R.id.thumbnail2);
            titl = (TextView) itemView.findViewById(R.id.titl);
            email = (TextView) itemView.findViewById(R.id.email);
            date = (TextView) itemView.findViewById(R.id.date_taken);
            published = (TextView) itemView.findViewById(R.id.published);

            setRightIn = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_right_in);
            setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_right_out);

            setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_left_in);
            setLeftOut = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_left_out);

            thumbnail.setVisibility(View.VISIBLE);
            thumbnail2.setVisibility(View.GONE);

            frameLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            if (!isBackVisible) {
                setRightIn.setTarget(thumbnail2);
                setRightOut.setTarget(thumbnail);
                setLeftIn.setTarget(thumbnail2);
                setLeftOut.setTarget(thumbnail);
                setRightIn.start();
                setRightOut.start();
                setLeftIn.start();
                setLeftOut.start();
                isBackVisible = true;
                thumbnail2.setVisibility(View.VISIBLE);
                thumbnail.setVisibility(View.GONE);
            } else {
                setRightIn.setTarget(thumbnail);
                setRightOut.setTarget(thumbnail2);
                setLeftIn.setTarget(thumbnail);
                setLeftOut.setTarget(thumbnail2);
                setRightIn.start();
                setRightOut.start();
                setLeftIn.start();
                setLeftOut.start();
                isBackVisible = false;
                thumbnail.setVisibility(View.VISIBLE);
                thumbnail2.setVisibility(View.GONE);
            }
        }
    }

}
