package com.shanlian.genetic.ui.main.main;



import com.shanlian.genetic.bean.request.MainRequest;

/**
 * Created by kang on 2017/9/25.
 */

public class MainModel implements MainContract.Modle {

    @Override
    public void getMainData(MainRequest MainRequest, final MainOnLoadListener listener) {
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
        listener.onSuccess("MainMVPSuccess");
    }
}
