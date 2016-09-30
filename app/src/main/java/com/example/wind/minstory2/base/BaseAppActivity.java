package com.example.wind.minstory2.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.ButterKnife;

/**
 * Created by wind on 2016/7/15.
 */
public abstract class BaseAppActivity extends AppCompatActivity {
    protected Context mContext;
    protected AppCompatActivity mActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("请返回正确的layoutid");
        }
        mContext = this;
        mActivity = this;
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initViewsAndEvents();

    }

    /**
     * 绑定视图 R.layout.***
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化控件view和事件
     */
    protected abstract void initViewsAndEvents();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }
    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
