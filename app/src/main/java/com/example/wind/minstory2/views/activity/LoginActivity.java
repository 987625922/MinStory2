package com.example.wind.minstory2.views.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wind on 2016/7/28.
 */
public class LoginActivity extends BaseAppActivity {
    @Bind(R.id.img_back)
    ImageView mImgBack;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents() {

    }


    @OnClick({R.id.img_back, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
                break;
        }
    }
}
