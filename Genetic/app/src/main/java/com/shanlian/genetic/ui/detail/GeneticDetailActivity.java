package com.shanlian.genetic.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.shanlian.genetic.PublishActivity;
import com.shanlian.genetic.R;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.BasePresenter;
import com.shanlian.genetic.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/4/23.
 */

public class GeneticDetailActivity extends BaseActivity {
    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;


    private List<String> mDatas;
    private MyAdapter myAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_genetic_detail;
    }

    @Override
    public void initView() {
        super.initView();
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 10);
        toolbar.setTitle("GeneticDetailActivity");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        collapsingToolbarLayout.setTitle("敖鲁古雅驯鹿");
        ivImage.setImageResource(R.mipmap.lu);
        findViewById(R.id.fab).setOnClickListener(view -> startActivity(new Intent(GeneticDetailActivity.this, PublishActivity.class)));
//        setUpRecyclerView();

    }
//    private void setUpRecyclerView(){
//        mDatas= new ArrayList<>();
//        for (int i=0;i<100;i++){
//            mDatas.add("Protect your dream  "+i);
//        }
//        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        myAdapter=new MyAdapter(GeneticDetailActivity.this,mDatas);
//        mRecyclerView.setAdapter(myAdapter);
//    }

    //设置适配器RecyclerView.Adapter
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<String> mDatas;
        private LayoutInflater mInflater;

        public MyAdapter(Context context, List<String> datas){
            mInflater = LayoutInflater.from(context);
            mDatas = datas;
        }

        /*列表数据总数*/
        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        /*加载视图*/
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView= mInflater.inflate(R.layout.item_recycle_view_list,parent,false);
            MyViewHolder myViewHolder=new MyViewHolder(mView);
            return myViewHolder;
        }

        /*添加数据*/
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.textView.setText(mDatas.get(position));
        }

        /**与视图打交道的ViewHolder*/
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public MyViewHolder(View view){
                super(view);
                textView=(TextView)view.findViewById(R.id.text);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
