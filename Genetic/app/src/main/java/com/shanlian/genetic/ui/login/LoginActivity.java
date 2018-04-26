package com.shanlian.genetic.ui.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.bean.request.LoginRequest;
import com.shanlian.genetic.ui.main.main.MainActivity;
import com.shanlian.genetic.ui.register.RegisterActivity;
import com.shanlian.genetic.util.LogUtils;
import com.shanlian.genetic.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2017/9/25.
 */

public class LoginActivity extends BaseActivity<LoginContract.View,LoginPresenter> implements LoginContract.View {


    @Bind(R.id.et_login_username)
    EditText etLoginUsername;
    @Bind(R.id.iv_login_clear_login)
    ImageView ivLoginClearLogin;
    @Bind(R.id.ll_login_user)
    LinearLayout llLoginUser;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;
    @Bind(R.id.iv_login_clear_pwd)
    ImageView ivLoginClearPwd;
    @Bind(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        super.init();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void initView() {
        super.initView();
        StatusBarUtil.hideFakeStatusBarView(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        etLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginUsername.getText().toString().length()>0){
                    ivLoginClearLogin.setVisibility(View.VISIBLE);
                }else {
                    ivLoginClearLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginPassword.getText().toString().length()>0){
                    ivLoginClearPwd.setVisibility(View.VISIBLE);
                }else {
                    ivLoginClearPwd.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvRegister.setOnClickListener(v -> jumpToActivity(RegisterActivity.class));
        tvLogin.setOnClickListener(v -> {
            String phone = etLoginUsername.getText().toString().trim();
            String pwd = etLoginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                etLoginUsername.setError(UIUtils.getString(R.string.phone_not_empty));
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                etLoginPassword.setError(UIUtils.getString(R.string.password_not_empty));
                return;
            }
            mPresenter.getDataFromNet(new LoginRequest(phone,pwd));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onLoadSuccess(String result) {
        LogUtils.i(TAG,result);
//        SPUtils.getInstance(this).putBoolean("login",true);
        jumpToActivityAndClearTask(MainActivity.class);
    }

    @Override
    public void onLoadFail(String msg) {
        UIUtils.showToast(msg);
    }
}
