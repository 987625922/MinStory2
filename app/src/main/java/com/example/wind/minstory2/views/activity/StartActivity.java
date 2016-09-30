package com.example.wind.minstory2.views.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.example.wind.minstory2.R;


/**
 * Created by wind on 2016/7/15.
 */
public class StartActivity extends AppCompatActivity {
    /*启动延迟1000毫秒*/
    private final int sleepTime = 1000;

    Handler mHandler;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.amin_start);

        tv = (TextView)findViewById(R.id.tv_start);
        animator.setInterpolator(new BounceInterpolator());
        animator.setTarget(tv);
        animator.start();

        mHandler = new Handler();
        mHandler.postDelayed(runMain, sleepTime);

    }

    Runnable runMain = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        }
    };

}
