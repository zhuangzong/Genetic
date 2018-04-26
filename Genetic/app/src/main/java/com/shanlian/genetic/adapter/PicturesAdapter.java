package com.shanlian.genetic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shanlian.genetic.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/23.
 */

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.MyViewHolder> {
    List<String> list;
    Context context;

    public PicturesAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Glide.with(context).load(list.get(position)).error(R.mipmap.wrong_pic).into(holder.ivItemPicture);
    }

    @Override
    public int getItemCount() {
        if (list.size()>3){
            return 3;
        }else {
            return list.size();
        }

    }

    class MyViewHolder extends ViewHolder {
        @Bind(R.id.iv_item_picture)
        ImageView ivItemPicture;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
