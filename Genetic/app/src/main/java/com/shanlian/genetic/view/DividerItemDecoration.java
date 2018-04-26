package com.shanlian.genetic.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration{
    private int topSpace;
    private int leftSpace;
    private int rightSpace;
    private int bottomSpace;

    public DividerItemDecoration(int topSpace,int bottomSpace,int leftSpace,int rightSpace) {
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top=topSpace;
        outRect.bottom=bottomSpace;
        outRect.left=leftSpace;
        outRect.right=rightSpace;
    }

}
