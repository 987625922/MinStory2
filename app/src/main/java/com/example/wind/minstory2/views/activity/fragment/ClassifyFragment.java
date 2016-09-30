package com.example.wind.minstory2.views.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wind.minstory2.Adapter.ClassifyAdapter;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseFragment;
import com.example.wind.minstory2.bean.Classifypage;
import com.example.wind.minstory2.views.activity.ClassifyActivity;
import com.example.wind.minstory2.views.activity.MainSearchActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import rx.Subscriber;

/**
 * Created by wind on 2016/7/22.
 */
public class ClassifyFragment extends BaseFragment {
    private RecyclerView recycler;
    private ClassifyAdapter classifyAdapter;
    private List<Classifypage> datas = new ArrayList<>();
    private BmobQuery<Classifypage> query;
    private ImageView search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        recycler = (RecyclerView)view.findViewById(R.id.rl_classify);
        classifyAdapter = new ClassifyAdapter(R.layout.item_classify,datas);
        search = (ImageView)view.findViewById(R.id.img_search);
        classifyAdapter.openLoadAnimation();
        recycler.setLayoutManager(new GridLayoutManager(getContext(),3));


        recycler.setAdapter(classifyAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainSearchActivity.class));
            }
        });

        //recycle点击事件
        classifyAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("keyword",datas.get(i).getTitle());
                intent.putExtras(bundle);
                intent.setClass(getActivity(),ClassifyActivity.class);

                startActivity(intent);

            }
        });

        return view;
    }
    //recycler跳转到第一个item
    public void recyclerTop() {
        recycler.smoothScrollToPosition(0);
    }
    //网络请求
    private void init() {
        query = new BmobQuery<Classifypage>();
        query.setLimit(100);//返回10条数据
        query.findObjectsObservable(Classifypage.class)
                .subscribe(new Subscriber<List<Classifypage>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage("请求错误");
                    }

                    @Override
                    public void onNext(List<Classifypage> data) {
                        datas.addAll(data);
                        classifyAdapter.notifyDataSetChanged();

                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        datas.clear();
        init();
    }

}
