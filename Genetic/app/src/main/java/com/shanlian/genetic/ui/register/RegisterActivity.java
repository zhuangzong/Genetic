package com.shanlian.genetic.ui.register;


import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.MyApplication;
import com.shanlian.genetic.bean.request.RegisterRequest;
import com.shanlian.genetic.ui.main.main.MainActivity;
import com.shanlian.genetic.util.LogUtils;
import com.shanlian.genetic.util.UIUtils;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2017/9/25.
 */

public class RegisterActivity extends BaseActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View {


    @Bind(R.id.et_register_phone)
    EditText etRegisterPhone;
    @Bind(R.id.ll_register_phone)
    LinearLayout llRegisterPhone;
    @Bind(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @Bind(R.id.ll_register_pwd)
    LinearLayout llRegisterPwd;
    @Bind(R.id.et_register_pwd2)
    EditText etRegisterPwd2;
    @Bind(R.id.ll_register_pwd2)
    LinearLayout llRegisterPwd2;
    @Bind(R.id.et_register_name)
    EditText etRegisterName;
    @Bind(R.id.ll_register_name)
    LinearLayout llRegisterName;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.tv_register_back)
    TextView tvRegisterBack;
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.toolbar)
    AutoLinearLayout toolbar;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.et_register_company)
    EditText etRegisterCompany;
    @Bind(R.id.ll_register_company)
    AutoLinearLayout llRegisterCompany;
    @Bind(R.id.ib_visible_reg)
    ImageButton ibVisibleReg;
    @Bind(R.id.ib_visible_reg2)
    ImageButton ibVisibleReg2;

    private boolean flag1,flag2;
    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle.setText("注册");
    }

    @Override
    public void initListener() {
        super.initListener();
        tvBack.setOnClickListener(v -> finish());
        tvRegisterBack.setOnClickListener(v -> finish());
        ibVisibleReg.setOnClickListener(v -> {
            if (!flag1) {
                etRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ibVisibleReg.setBackgroundResource(R.mipmap.pwd_invisible);
            } else {
                etRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ibVisibleReg.setBackgroundResource(R.mipmap.pwd_visible);
            }
            flag1 = !flag1;
            etRegisterPwd.postInvalidate();
            CharSequence text2 = etRegisterPwd.getText();
            if (text2 instanceof Spannable) {
                Spannable spanText = (Spannable) text2;
                Selection.setSelection(spanText, text2.length());
            }
        });
        ibVisibleReg2.setOnClickListener(v -> {
            if (!flag2) {
                etRegisterPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ibVisibleReg2.setBackgroundResource(R.mipmap.pwd_invisible);
            } else {
                etRegisterPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ibVisibleReg2.setBackgroundResource(R.mipmap.pwd_visible);
            }
            flag2 = !flag2;
            etRegisterPwd2.postInvalidate();
            CharSequence text2 = etRegisterPwd2.getText();
            if (text2 instanceof Spannable) {
                Spannable spanText = (Spannable) text2;
                Selection.setSelection(spanText, text2.length());
            }
        });
        tvRegister.setOnClickListener(v -> {
            String phone = etRegisterPhone.getText().toString().trim();
            String pwd = etRegisterPwd.getText().toString().trim();
            String pwd2 = etRegisterPwd2.getText().toString().trim();
            String name = etRegisterName.getText().toString().trim();
            String company = etRegisterCompany.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                etRegisterName.setError(UIUtils.getString(R.string.name_not_empty));
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                etRegisterPwd.setError(UIUtils.getString(R.string.password_not_empty));
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                etRegisterPhone.setError(UIUtils.getString(R.string.phone_not_empty));
                return;
            }
            if (!pwd2.equals(pwd)) {
                UIUtils.showToast(UIUtils.getString(R.string.password_not_right));
                return;
            }
            mPresenter.getDataFromNet(new RegisterRequest(0, name, pwd, company, phone, 0, ""));
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
        LogUtils.i(TAG, result);
//        SPUtils.getInstance(this).putBoolean(AppConst.ISLOGIN,true);
        jumpToActivityAndClearTask(MainActivity.class);
        MyApplication.exit();
    }

    @Override
    public void onLoadFail(String msg) {
        UIUtils.showToast(msg);
    }
}
