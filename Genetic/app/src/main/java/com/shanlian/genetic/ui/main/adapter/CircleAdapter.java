package com.shanlian.genetic.ui.main.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.shanlian.genetic.R;
import com.shanlian.genetic.adapter.PicturesAdapter;
import com.shanlian.genetic.base.OnItemClickListener;
import com.shanlian.genetic.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kang on 2018/4/23.
 */

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyViewHolder> {

    List<String> list;
    Context context;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CircleAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_circle, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int pos = position;
                onItemClickListener.onItemClick(holder.itemView, pos);
            });
            holder.itemView.setOnLongClickListener(v -> {
                int pos = position;
                onItemClickListener.onItemLongClick(holder.itemView, pos);
                return true;
            });
        }
        holder.ivItemCircleZan.setImageResource(R.mipmap.zan_gray);
        holder.ivItemCircleZan.setOnClickListener(v -> holder.ivItemCircleZan.setImageResource(R.mipmap.zan));
        List<String> mData=new ArrayList<>();
        mData.add("云南中蜂");
        mData.add("云南中云南中蜂");
        mData.add("云南中南中蜂");
        mData.add("云南中蜂云南中蜂");
        mData.add("云南");
        mData.add("云南中中蜂");
        setPicLists(holder.rvItemCirclePics,mData);
        setCategory(holder.rvItemCircleCat,mData);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setPicLists(RecyclerView mRecyclerView,List<String> mData) {

        PicturesAdapter mAdapter=new PicturesAdapter(mData,context);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void setCategory(RecyclerView mRecyclerView,List<String> mData) {

        CategoryAdapter mAdapter=new CategoryAdapter(mData,context);
        mRecyclerView.setAdapter(mAdapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item_circle_profile)
        CircleImageView ivItemCircleProfile;
        @Bind(R.id.tv_item_circle_name)
        TextView tvItemCircleName;
        @Bind(R.id.tv_item_circle_time)
        TextView tvItemCircleTime;
        @Bind(R.id.iv_item_circle_zan)
        ImageView ivItemCircleZan;
        @Bind(R.id.rv_item_circle_pics)
        RecyclerView rvItemCirclePics;
        @Bind(R.id.rv_item_circle_cat)
        RecyclerView rvItemCircleCat;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initPicRecyclerView();
            initRecyclerView();
        }

        private void initPicRecyclerView() {
            GridLayoutManager gridLayoutManager= new GridLayoutManager(context,3){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            rvItemCirclePics.setLayoutManager(gridLayoutManager);
            rvItemCirclePics.addItemDecoration(new DividerItemDecoration(0,0,4,4));
            rvItemCirclePics.setItemAnimator(new DefaultItemAnimator());
        }
        private void initRecyclerView() {
            ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .setScrollingEnabled(false  )
                    .build();
            rvItemCircleCat.setLayoutManager(chipsLayoutManager);
            rvItemCircleCat.addItemDecoration(new DividerItemDecoration(0,0,8,8));
            rvItemCircleCat.setItemAnimator(new DefaultItemAnimator());
        }
    }

}
