package com.shanlian.genetic.ui.select;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.util.log.IFillLogger;
import com.shanlian.genetic.R;
import com.shanlian.genetic.base.OnItemClickListener;
import com.shanlian.genetic.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/24.
 */

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.MyViewHolder> {
    List<String> list;
    Context context;
    Map<Integer,Integer> selects=new HashMap<>();//获取选中项

    public Map<Integer, Integer> getSelects() {
        return selects;
    }

    SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private void setItemSpread(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }
    //根据位置判断条目是否展开
    private boolean isItemSpread(int position) {
        return mSelectedPositions.get(position);
    }
    public SelectAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        setItemSpread(position, false);
        List<String> list=new ArrayList<>();
        list.add("一级");
        list.add("二级");
        list.add("三级");
        list.add("四级");
        list.add("五级");
        list.add("六级");
        SelectItemAdapter adapter=new SelectItemAdapter(list,context);
        holder.mRecyclerView.setAdapter(adapter);
        selects.put(position,adapter.getSelected());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if (adapter.getSelected()==i){
                    adapter.setSelection(-1);
                    selects.put(position,-1);
                }else {
                    adapter.setSelection(i);
                    selects.put(position,i);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        holder.ivItemSelect.setOnClickListener(v -> {
            if (isItemSpread(position)){
                setItemSpread(position, false);
                rotateImageView(holder.ivItemSelect,0);
                adapter.setIsSpread(isItemSpread(position));
                adapter.notifyDataSetChanged();
            }else {
                setItemSpread(position, true);
                rotateImageView(holder.ivItemSelect,180);
                adapter.setIsSpread(isItemSpread(position));
                adapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_select_title)
        TextView tvItemSelectTitle;
        @Bind(R.id.iv_item_select)
        ImageView ivItemSelect;
        @Bind(R.id.rv_item_select)
        RecyclerView mRecyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initRecyclerView();
        }

        private void initRecyclerView() {
            GridLayoutManager gridLayoutManager= new GridLayoutManager(context,4){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(10,10,10,10));
        }
    }
    /**
     * 旋转图片
     *
     * @param iv
     * @param i
     */
    public void rotateImageView(ImageView iv, int i) {
        Matrix matrix = new Matrix();
        Bitmap bitmap = ((BitmapDrawable) context.getResources()
                .getDrawable(R.mipmap.down)).getBitmap();
        // 设置旋转角度
        matrix.setRotate(i);
        // 重新绘制Bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        iv.setImageBitmap(bitmap);
    }
}
