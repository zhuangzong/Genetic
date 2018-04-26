package com.shanlian.genetic.ui.register;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.bean.request.RegisterRequest;
import com.shanlian.genetic.util.UIUtils;

/**
 * Created by kang on 2017/9/25.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    private RegisterModel model;

    public RegisterPresenter(BaseActivity context) {
        super(context);
        model=new RegisterModel();
    }


    @Override
    public void getDataFromNet(RegisterRequest RegisterRequest) {
        mContext.showWaitingDialog(UIUtils.getString(R.string.waiting));
        model.getRegisterData(RegisterRequest, new RegisterOnLoadListener() {
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
