package com.shanlian.genetic.ui.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanlian.genetic.PublishActivity;
import com.shanlian.genetic.R;
import com.shanlian.genetic.base.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/25.
 */

public class GeneticListAdapter extends RecyclerView.Adapter<GeneticListAdapter.MyViewHolder> {
    List<String> list;
    Context context;

    public GeneticListAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_genetic_list, parent, false));
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
        holder.tvItemGeneticListFaxian.setOnClickListener(v -> context.startActivity(new Intent(context, PublishActivity.class)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item_genetic_list)
        ImageView ivItemGeneticList;
        @Bind(R.id.tv_item_genetic_list_name)
        TextView tvItemGeneticListName;
        @Bind(R.id.tv_item_genetic_list_qun)
        TextView tvItemGeneticListQun;
        @Bind(R.id.tv_item_genetic_list_type)
        TextView tvItemGeneticListType;
        @Bind(R.id.tv_item_genetic_list_fenbu)
        TextView tvItemGeneticListFenbu;
        @Bind(R.id.iv_item_genetic_list_type)
        ImageView ivItemGeneticListType;
        @Bind(R.id.tv_item_genetic_list_animal)
        TextView tvItemGeneticListAnimal;
        @Bind(R.id.tv_item_genetic_list_faxian)
        TextView tvItemGeneticListFaxian;
        @Bind(R.id.tv_item_genetic_list_pos)
        TextView tvItemGeneticListPos;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
