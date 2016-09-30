package com.example.wind.minstory2.views.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;
import com.example.wind.minstory2.bean.VideoData;
import com.example.wind.minstory2.views.activity.fragment.VideoIntroductionFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Subscriber;


/**
 * Created by wind on 2016/9/1.
 */
public class VideoActivity extends BaseAppActivity{
    private ImageView back;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private Handler handler;
    private Drawable drawable = null;
    private BmobQuery<VideoData> bmobQuery;
    private int id = -1;
    private final static int msgWhat = 0x101;
    private String[] mTitle = {"简介", "评论"};
    private Fragment[] fragments = new Fragment[mTitle.length];
    private TabLayout videoTab;
    private ViewPager viewPager;
    private MyOrderAdapter myOrderAdapter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_video;
    }
    @Override
    protected void initViewsAndEvents() {
        videoTab = (TabLayout)findViewById(R.id.main_tab);
        viewPager = (ViewPager)findViewById(R.id.vp_content);
        back = (ImageView)findViewById(R.id.img_back);
        fragments[0] = new VideoIntroductionFragment();
        fragments[1] = new VideoIntroductionFragment();
        myOrderAdapter = new MyOrderAdapter(getSupportFragmentManager());
        jcVideoPlayerStandard = (JCVideoPlayerStandard)findViewById(R.id.video);

        viewPager.setAdapter(myOrderAdapter);
        viewPager.setCurrentItem(0);
        videoTab.setupWithViewPager(viewPager);
        videoTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        if (id == -1){
            showMessage("id错误");
            finish();
        }

        handler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message message) {
                if (msgWhat == message.what) {
                    jcVideoPlayerStandard.thumbImageView.setImageDrawable(drawable);
                }


                return false;
            }
        });



        getVideoData();
        onClick();
    }

    private void getVideoData(){

        bmobQuery = new BmobQuery<VideoData>();
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.setLimit(1);
        bmobQuery.order("createdAt");
        //先判断是否有缓存

        boolean isCache = bmobQuery.hasCachedResult(VideoData.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存取数据，如果没有的话，再从网络取。
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则先从网络中取
        }

        bmobQuery.findObjectsObservable(VideoData.class)
                .subscribe(new Subscriber<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage("请求错误");
                    }

                    @Override
                    public void onNext(final List<VideoData> pageData) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = Message.obtain();
                                drawable
                                        = getImageFromNetwork(pageData.get(0).getImgUrl());
                                msg.what = 0x101;
                                handler.sendMessage(msg);

                            }
                        }).start();

                        jcVideoPlayerStandard.setUp(pageData.get(0).getUrl()
                                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, pageData.get(0).getTitle());


                    }
                });


    }



    private void onClick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    /**
     * 把url变成drawable
     *
     * @param imageUrl
     * @return
     */
    private Drawable getImageFromNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);

            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }
    //viewpage的adapter
    class MyOrderAdapter extends FragmentPagerAdapter {

        public MyOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }


}
