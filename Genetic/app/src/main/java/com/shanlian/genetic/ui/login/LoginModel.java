package com.shanlian.genetic.ui.login;



import com.shanlian.genetic.R;
import com.shanlian.genetic.api.ApiRetrofit;
import com.shanlian.genetic.bean.request.LoginRequest;
import com.shanlian.genetic.util.UIUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kang on 2017/9/25.
 */

public class LoginModel implements LoginContract.Modle {

    @Override
    public void getLoginData(LoginRequest loginRequest, final LoginOnLoadListener listener) {
//        ApiRetrofit.getInstance().login(loginRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber < String > () {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        listener.onFailure(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        listener.onSuccess(s);
//                    }
//                });
        listener.onSuccess("LoginMVPSuccess");
    }
}
