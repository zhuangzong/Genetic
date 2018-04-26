package com.shanlian.genetic.ui.login;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.bean.request.LoginRequest;
import com.shanlian.genetic.util.UIUtils;

import java.util.HashMap;

/**
 * Created by kang on 2017/9/25.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private LoginModel model;

    public LoginPresenter(BaseActivity context) {
        super(context);
        model=new LoginModel();
    }


    @Override
    public void getDataFromNet(LoginRequest loginRequest) {
        mContext.showWaitingDialog(UIUtils.getString(R.string.waiting));
        model.getLoginData(loginRequest, new LoginOnLoadListener() {
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
