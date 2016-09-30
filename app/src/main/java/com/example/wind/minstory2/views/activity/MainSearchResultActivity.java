package com.example.wind.minstory2.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wind.minstory2.Adapter.MainRececlyAdapter;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;
import com.example.wind.minstory2.bean.Homepage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import rx.Subscriber;

/**
 * Created by wind on 2016/8/10.
 */
public class MainSearchResultActivity extends BaseAppActivity {
    @Bind(R.id.black)
    ImageView mBlack;
    @Bind(R.id.edt_search)
    EditText mEdtSearch;
    @Bind(R.id.rl_main)
    RecyclerView mRlMain;
    @Bind(R.id.sw_main)
    SwipeRefreshLayout mSwMain;
    private String searchResult;
    private MainRececlyAdapter mainRececlyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Homepage> data = new ArrayList<>();
    private int pageIndex = 1;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.actitvity_main_search_result;
    }

    @Override
    protected void initViewsAndEvents() {
        searchResult = getIntent().getExtras().getString("keyword");
        mEdtSearch.setText(searchResult);
        mEdtSearch.setOnEditorActionListener(new EditActionListener());
        showMessage(searchResult);


        linearLayoutManager = new LinearLayoutManager(mContext);
        mainRececlyAdapter = new MainRececlyAdapter(R.layout.item_mainrececly, data);

        //recycle点击事件
        mainRececlyAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id",data.get(i).getId());
                intent.putExtras(bundle);
                intent.setClass(mContext,ContentActivity.class);

                startActivity(intent);

            }
        });

        mainRececlyAdapter.openLoadAnimation();
        mRlMain.setLayoutManager(linearLayoutManager);


        mRlMain.setAdapter(mainRececlyAdapter);

        mSwMain.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
        //下拉刷新
        mSwMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pageIndex = 1;
                queryObjects();

            }
        });
        mBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        queryObjects();

    }

    //网络请求搜索
    public void queryObjects() {
        if (pageIndex == 1){
            data.clear();
        }

        final BmobQuery<Homepage> bmobQuery = new BmobQuery<Homepage>();
        bmobQuery.addWhereContains("title", searchResult);
        bmobQuery.setLimit(10);

        if (pageIndex > 1) {
            bmobQuery.setSkip(10 * (pageIndex - 1));
        }

        bmobQuery.order("createdAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(Homepage.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存取数据，如果没有的话，再从网络取。
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则先从网络中取
        }
//		observable形式
        bmobQuery.findObjectsObservable(Homepage.class)
                .subscribe(new Subscriber<List<Homepage>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Homepage> datas) {

                        if (datas.size() >= 1) {
                            data.addAll(datas);
                            mainRececlyAdapter.notifyDataSetChanged();
                            mSwMain.setRefreshing(false);

                        } else {
                            showMessage("内容页出错");

                        }


                    }
                });

    }



    /**
     * 搜索
     */
    private class EditActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ((InputMethodManager) mEdtSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String searchStr = mEdtSearch.getText().toString().trim();
                if (searchStr.equals("")) {
                    showMessage("请输入搜索词");
                } else {

                    searchResult = searchStr;
                    pageIndex=1;
                    queryObjects();


                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(MainSearchResultActivity.this
                        .getCurrentFocus().getWindowToken(), 0);
            }

            searchResult = mEdtSearch.getText().toString();
            pageIndex=1;

            queryObjects();


            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
