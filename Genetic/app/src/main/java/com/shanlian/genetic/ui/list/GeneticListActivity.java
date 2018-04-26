package com.shanlian.genetic.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.base.OnItemClickListener;
import com.shanlian.genetic.ui.detail.GeneticDetailActivity;
import com.shanlian.genetic.ui.main.adapter.CircleAdapter;
import com.shanlian.genetic.ui.main.main.MainActivity;
import com.shanlian.genetic.ui.select.SelectActivity;
import com.shanlian.genetic.view.DividerItemDecoration;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/25.
 */

public class GeneticListActivity extends BaseActivity {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.toolbar)
    AutoLinearLayout toolbar;
    @Bind(R.id.ll_search)
    AutoLinearLayout llSearch;
    @Bind(R.id.rv_genetic_list)
    RecyclerView mRecyclerView;

    private List<String> list;
    private GeneticListAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_genetic_list;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle.setText("列表");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("筛选");
    }

    @Override
    public void initData() {
        super.initData();
        list=new ArrayList<>();
        for (int i=0;i<25;i++){
            list.add(i+"");
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(0,8,0,0));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new GeneticListAdapter(list,this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        tvBack.setOnClickListener(v -> finish());
        tvRight.setOnClickListener(v -> startActivity(new Intent(GeneticListActivity.this, SelectActivity.class)));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(GeneticListActivity.this, GeneticDetailActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
