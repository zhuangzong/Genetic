package com.shanlian.genetic.ui.select;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.OnItemClickListener;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/24.
 */

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.MyViewHolder> {
    List<String> list;
    Context context;
    boolean isSpread;//是否展开
    private int selected = -1;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelection(int position){
        this.selected = position;
        notifyDataSetChanged();
    }
    public int getSelected(){
        return selected;
    }

    public void setIsSpread(boolean flag) {
        this.isSpread = flag;
    }

    public SelectItemAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvItemSelectItem.setText(list.get(position));
        if(selected == position){
            holder.rel.setBackgroundResource(R.drawable.shape_corner_blue_rec);
            holder.itemView.setSelected(true);
        } else {
            holder.rel.setBackgroundResource(R.drawable.shape_corner_gray_rec);
            holder.itemView.setSelected(false);
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 4) {
            if (isSpread) {
                return list.size();
            } else {
                return 4;
            }
        } else {
            return list.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_select_item)
        TextView tvItemSelectItem;
        @Bind(R.id.rel_item_select_item)
        AutoRelativeLayout rel;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
