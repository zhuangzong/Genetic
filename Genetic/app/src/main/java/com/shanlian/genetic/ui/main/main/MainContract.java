package com.shanlian.genetic.ui.main.main;



import com.shanlian.genetic.base.BaseView;
import com.shanlian.genetic.bean.request.MainRequest;


/**
 * Created by kang on 2017/9/25.
 */

public class MainContract {
    public interface View extends BaseView {
    }


    public interface Modle {
        void getMainData(MainRequest MainRequest, MainOnLoadListener listener);
    }
    public interface Presenter {
        void getDataFromNet(MainRequest MainRequest);
    }
}
