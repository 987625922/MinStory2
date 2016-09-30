package com.example.wind.minstory2.views.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wind.minstory2.Adapter.VideoAdapter;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseFragment;
import com.example.wind.minstory2.bean.Videopage;
import com.example.wind.minstory2.views.activity.MainSearchActivity;
import com.example.wind.minstory2.views.activity.VideoActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import rx.Subscriber;

/**
 * Created by wind on 2016/7/22.
 */
public class VideoFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Videopage> data = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private VideoAdapter videoAdapter;
    private BmobQuery<Videopage> query;
    private int pageIndex = 1;
    private int lastVisibleItem = 0;
    private ImageView search;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rl_main);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_main);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        videoAdapter = new VideoAdapter(R.layout.item_video,data);
        search = (ImageView) view.findViewById(R.id.img_search);

        videoAdapter.openLoadAnimation();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoAdapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));

        //recycle点击事件
        videoAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id",data.get(i).getId());
                intent.putExtras(bundle);
                intent.setClass(getActivity(),VideoActivity.class);

                startActivity(intent);


            }
        });

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                data.clear();
                init();
            }
        });

        //上拉加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == videoAdapter.getItemCount()) {

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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainSearchActivity.class));
            }
        });

        return view;
    }

    public void recyclerTop() {
        recyclerView.smoothScrollToPosition(0);
    }


    //网络请求
    private void init() {

        query = new BmobQuery<Videopage>();
        query.setLimit(10);//返回10条数据
        query.order("-createdAt");
        if (pageIndex > 1) {
            query.setSkip(10 * (pageIndex - 1));
        }

        query.findObjectsObservable(Videopage.class)
                .subscribe(new Subscriber<List<Videopage>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage("请求错误");
                    }

                    @Override
                    public void onNext(List<Videopage> pageData) {
                        data.addAll(pageData);
                        videoAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        init();
    }

}

