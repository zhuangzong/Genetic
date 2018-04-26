package com.shanlian.genetic.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.ui.list.GeneticListActivity;
import com.shanlian.genetic.ui.search.SearchActivity;
import com.shanlian.genetic.view.DividerItemDecoration;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/24.
 */

public class SelectActivity extends BaseActivity {

    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.toolbar)
    AutoLinearLayout toolbar;
    @Bind(R.id.rv_select)
    RecyclerView mRecyclerView;
    @Bind(R.id.bt_select)
    Button btSelect;

    private SelectAdapter adapter;
    private List<String> list;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(0, 0, 0, 0));
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("筛选");
        initAdapter();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        list.add("");
        list.add("");
        adapter = new SelectAdapter(list, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        tvBack.setOnClickListener(v -> finish());
        btSelect.setOnClickListener(v -> {
            Log.i(TAG,adapter.getSelects().toString());
            Intent intent=new Intent(SelectActivity.this, GeneticListActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
