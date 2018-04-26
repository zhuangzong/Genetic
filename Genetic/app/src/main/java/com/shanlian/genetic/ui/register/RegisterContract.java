package com.shanlian.genetic.ui.register;



import com.shanlian.genetic.base.BaseView;
import com.shanlian.genetic.bean.request.RegisterRequest;

/**
 * Created by kang on 2017/9/25.
 */

public class RegisterContract {
    public interface View extends BaseView {
    }


    public interface Modle {
        void getRegisterData(RegisterRequest RegisterRequest, RegisterOnLoadListener listener);
//        void getRegisterData(ParamsRegisterBean bean, RegisterOnLoadListener listener);
    }
    public interface Presenter {
//        public void getDataFromNet(HashMap<String,String> map);
        public void getDataFromNet(RegisterRequest RegisterRequest);
    }
}
