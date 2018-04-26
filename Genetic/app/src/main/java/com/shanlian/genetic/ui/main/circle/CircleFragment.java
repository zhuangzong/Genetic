package com.shanlian.genetic.ui.main.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.AppConst;
import com.shanlian.genetic.base.BaseFragment;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.base.OnItemClickListener;
import com.shanlian.genetic.ui.detail.GeneticDetailActivity;
import com.shanlian.genetic.ui.main.adapter.CircleAdapter;
import com.shanlian.genetic.ui.search.SearchActivity;
import com.shanlian.genetic.view.DividerItemDecoration;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/3/29.
 */

public class CircleFragment extends BaseFragment {

    @Bind(R.id.ll_search)
    AutoLinearLayout mLayout;
    @Bind(R.id.rv_circle)
    RecyclerView mRecyclerView;

    private CircleAdapter mAdapter;
    private List<String> list;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_circle;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);

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

    @Override
    public void initListener() {
        super.initListener();
        mLayout.setOnClickListener(v -> SearchActivity.launch(getActivity(), AppConst.SEARCH_CIRCLE));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(0,8,0,0));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new CircleAdapter(list,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
