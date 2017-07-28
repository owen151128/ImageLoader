package com.hpcnt.imageloader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hpcnt.imageloader.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 0wen151128 on 2017. 7. 28..
 */

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> imageList;

    public ImageRecyclerViewAdapter(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String target = imageList.get(position);
        holder.simpleDraweeView.setImageURI(target);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
