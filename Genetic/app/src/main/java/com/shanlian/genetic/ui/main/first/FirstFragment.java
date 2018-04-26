package com.shanlian.genetic.ui.main.first;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanlian.genetic.R;
import com.shanlian.genetic.base.AppConst;
import com.shanlian.genetic.base.BaseFragment;
import com.shanlian.genetic.base.BasePresenter;
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

public class FirstFragment extends BaseFragment {

    @Bind(R.id.ll_search)
    AutoLinearLayout mSearchLayout;
    @Bind(R.id.iv_first_1)
    ImageView ivFirst1;
    @Bind(R.id.tv_first_title_1)
    TextView tvFirstTitle1;
    @Bind(R.id.tv_first_content_1)
    TextView tvFirstContent1;
    @Bind(R.id.iv_first_2)
    ImageView ivFirst2;
    @Bind(R.id.tv_first_title_2)
    TextView tvFirstTitle2;
    @Bind(R.id.tv_first_content_2)
    TextView tvFirstContent2;
    @Bind(R.id.iv_first_3)
    ImageView ivFirst3;
    @Bind(R.id.tv_first_title_3)
    TextView tvFirstTitle3;
    @Bind(R.id.tv_first_content_3)
    TextView tvFirstContent3;
    @Bind(R.id.iv_first_4)
    ImageView ivFirst4;
    @Bind(R.id.tv_first_title_4)
    TextView tvFirstTitle4;
    @Bind(R.id.tv_first_content_4)
    TextView tvFirstContent4;
    @Bind(R.id.rv_first)
    RecyclerView mRecyclerView;

    private CircleAdapter mAdapter;
    private List<String> list;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_first;
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
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(0,8,0,0));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new CircleAdapter(list,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void initListener() {
        super.initListener();
        mSearchLayout.setOnClickListener(v -> SearchActivity.launch(getActivity(), AppConst.SEARCH_FIRST));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
