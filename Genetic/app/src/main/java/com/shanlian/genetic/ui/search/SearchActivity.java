package com.shanlian.genetic.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.AppConst;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.ui.list.GeneticListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/25.
 */

public class SearchActivity extends BaseActivity {
    @Bind(R.id.iv_search_back)
    ImageView ivSearchBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_search)
    TextView tvSearch;

    private int type;

    public static void launch(Context context,int type){
        Intent intent=new Intent(context,SearchActivity.class);
        intent.putExtra(AppConst.SEARCH_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        type=getIntent().getIntExtra(AppConst.SEARCH_TYPE,0);
        Log.i(TAG,"type===="+type);
    }

    @Override
    public void initListener() {
        super.initListener();
        ivSearchBack.setOnClickListener(v -> finish());
        tvSearch.setOnClickListener(v -> {
            switch (type){
                case AppConst.SEARCH_FIRST:
                    Intent intent=new Intent(SearchActivity.this, GeneticListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case AppConst.SEARCH_CIRCLE:
                    break;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
