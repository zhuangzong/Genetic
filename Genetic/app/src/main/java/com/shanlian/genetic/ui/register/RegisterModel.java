package com.shanlian.genetic.ui.register;



import com.shanlian.genetic.api.ApiRetrofit;
import com.shanlian.genetic.bean.request.RegisterRequest;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kang on 2017/9/25.
 */

public class RegisterModel implements RegisterContract.Modle {

    @Override
    public void getRegisterData(RegisterRequest RegisterRequest, final RegisterOnLoadListener listener) {
//        ApiRetrofit.getInstance().Register(RegisterRequest)
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
        listener.onSuccess("registerMVPSuccess");
    }
}
