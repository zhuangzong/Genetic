package com.shanlian.genetic.base;

/**
 * Created by kang on 2017/9/25.
 */

public interface BaseView {
    //加载数据成功
    public void onLoadSuccess(String result);
    //加载数据失败
    public void onLoadFail(String msg);
}
