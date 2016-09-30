package com.example.wind.minstory2.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wind.minstory2.Adapter.MainRececlyAdapter;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;
import com.example.wind.minstory2.bean.Homepage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import rx.Subscriber;

/**
 * Created by wind on 2016/8/11.
 */
public class ClassifyActivity extends BaseAppActivity {
    @Bind(R.id.rl_classify)
    RecyclerView mRlClassify;
    @Bind(R.id.sw_classify)
    SwipeRefreshLayout mSwClassify;

    private String keyword = "全部";
    private BmobQuery<Homepage> query;
    private List<Homepage> datas = new ArrayList<>();
    private MainRececlyAdapter mainRececlyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem = 0;
    private int pageIndex = 1;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_classify;
    }

    @Override
    protected void initViewsAndEvents() {
        keyword = getIntent().getExtras().getString("keyword");

        linearLayoutManager = new LinearLayoutManager(ClassifyActivity.this);
        mainRececlyAdapter = new MainRececlyAdapter(R.layout.item_mainrececly, datas);
        mainRececlyAdapter.openLoadAnimation();
        mRlClassify.setLayoutManager(linearLayoutManager);


        mRlClassify.setAdapter(mainRececlyAdapter);

        mSwClassify.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));

        //下拉刷新
        mSwClassify.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                datas.clear();
                init();
            }
        });



        mRlClassify.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == mainRececlyAdapter.getItemCount()) {

                    pageIndex++;
                    init();


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        //recycle点击事件
        mainRececlyAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id",datas.get(i).getId());
                intent.putExtras(bundle);
                intent.setClass(mContext,ContentActivity.class);

                startActivity(intent);

            }
        });

    }


    //网络请求
    private void init() {
        query = new BmobQuery<Homepage>();
        query.setLimit(10);//返回10条数据
        query.addWhereContains("provenance","精选童话");
        query.order("createdAt");

        if (pageIndex > 1) {
            query.setSkip(10 * (pageIndex - 1));
        }

        query.findObjectsObservable(Homepage.class)
                .subscribe(new Subscriber<List<Homepage>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage("请求错误");
                    }

                    @Override
                    public void onNext(List<Homepage> pageData) {
                        datas.addAll(pageData);
                        mainRececlyAdapter.notifyDataSetChanged();
                        mSwClassify.setRefreshing(false);

                    }
                });


    }


    @OnClick({R.id.black, R.id.img_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.black:
                finish();
                break;
            case R.id.img_search:
                startActivity(new Intent(ClassifyActivity.this, MainSearchActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();

    }
}
