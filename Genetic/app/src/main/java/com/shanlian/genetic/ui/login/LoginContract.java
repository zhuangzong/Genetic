package com.shanlian.genetic.ui.login;



import com.shanlian.genetic.base.BaseView;
import com.shanlian.genetic.bean.request.LoginRequest;


/**
 * Created by kang on 2017/9/25.
 */

public class LoginContract {
    public interface View extends BaseView {
    }


    public interface Modle {
        void getLoginData(LoginRequest loginRequest, LoginOnLoadListener listener);
    }
    public interface Presenter {
        void getDataFromNet(LoginRequest loginRequest);
    }
}
