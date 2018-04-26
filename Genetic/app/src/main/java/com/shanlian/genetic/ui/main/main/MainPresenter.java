package com.shanlian.genetic.ui.main.main;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.bean.request.MainRequest;
import com.shanlian.genetic.util.UIUtils;

/**
 * Created by kang on 2017/9/25.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    private MainModel model;

    public MainPresenter(BaseActivity context) {
        super(context);
        model=new MainModel();
    }


    @Override
    public void getDataFromNet(MainRequest MainRequest) {
        mContext.showWaitingDialog(UIUtils.getString(R.string.waiting));
        model.getMainData(MainRequest, new MainOnLoadListener() {
            @Override
            public void onSuccess(String result) {
                mContext.hideWaitingDialog();
                getView().onLoadSuccess(result);
            }

            @Override
            public void onFailure(String e) {
                mContext.hideWaitingDialog();
                getView().onLoadFail(e);
            }

        });
    }
}
