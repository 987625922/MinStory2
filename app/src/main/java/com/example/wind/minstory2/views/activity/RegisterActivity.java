package com.example.wind.minstory2.views.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;
import com.example.wind.minstory2.bean.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wind on 2016/7/28.
 */
public class RegisterActivity extends BaseAppActivity {

    @Bind(R.id.tv_username)
    MaterialEditText mTvUsername;
    @Bind(R.id.tv_email)
    MaterialEditText mTvEmail;
    @Bind(R.id.tv_password)
    MaterialEditText mTvPassword;
    @Bind(R.id.tv_password_again)
    MaterialEditText mTvPasswordAgain;

    private User user;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
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
                register();
                break;
        }
    }

    private void register() {
        if (mTvUsername.getText().toString().length()<6){
            showMessage("用户名不能少于6个");
        }else if (mTvPassword.getText().length()<6){
            showMessage("密码不能少于6");
        }else {
            if (mTvPassword.getText().toString().equals(mTvPasswordAgain.getText().toString())) {
                user = new User();
                user.setUsername(mTvUsername.getText().toString());
                user.setEmail(mTvEmail.getText().toString());
                user.setPassword(mTvPassword.getText().toString());
                user.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            objectId = user.getObjectId();
                            Log.e("提示", "添加数据成功，返回objectId为：" + objectId);
                        } else {
                            Log.e("提示", "创建数据失败：" + e.getMessage());
                        }
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "2次密码不相同", Toast.LENGTH_SHORT).show();

            }
        }

    }


}
