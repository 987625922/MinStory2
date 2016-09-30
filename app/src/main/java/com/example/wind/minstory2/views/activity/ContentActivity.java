package com.example.wind.minstory2.views.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;
import com.example.wind.minstory2.bean.MainContent;
import com.example.wind.minstory2.utils.ImageHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import rx.Subscriber;

/**
 * Created by wind on 2016/7/28.
 */
public class ContentActivity extends BaseAppActivity {
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    private Html.ImageGetter imageGetter;
    private int mScreenWidth = 0;
    private int id;
    private Handler mHandler;
    private ProgressDialog pd;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_content;
    }


    @Override
    protected void initViewsAndEvents() {

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        pd = ProgressDialog.show(ContentActivity.this, "文章内容", "加载中，请稍后……");


        //测量出当前的设备 屏幕


        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x101) {
                    pd.dismiss();
                    mTvContent.setText((CharSequence) msg.obj);
                }
                return false;
            }
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;

        queryObjects();
    }


    //网络请求搜索
    public void queryObjects() {
        pd.show();
        final BmobQuery<MainContent> bmobQuery = new BmobQuery<MainContent>();
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.setLimit(1);
        bmobQuery.order("createdAt");
        //先判断是否有缓存

        boolean isCache = bmobQuery.hasCachedResult(MainContent.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存取数据，如果没有的话，再从网络取。
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则先从网络中取
        }

//		observable形式
        bmobQuery.findObjectsObservable(MainContent.class)
                .subscribe(new Subscriber<List<MainContent>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                        showMessage("文章加载失败");
                    }

                    @Override
                    public void onNext(final List<MainContent> persons) {
                        if (persons.size() == 1) {
                            mTvTitle.setText(persons.get(0).getTitle());
                            new Thread(new Runnable() {
                                Message msg = Message.obtain();

                                @Override
                                public void run() {

                                    imageGetter = new Html.ImageGetter() {
                                        @Override
                                        public Drawable getDrawable(String source) {

                                            Drawable drawable = getImageFromNetwork(source);
                                            if (drawable != null) {

                                                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                Bitmap bitmapCompress = null;

                                                bitmapCompress = ImageHelper.comp(bitmap);

                                                float floatHeight = drawable.getIntrinsicHeight();
                                                float floatWidth = drawable.getIntrinsicWidth();
                                                float screenWidth = mScreenWidth;
                                                int h = (int) (screenWidth * (floatHeight / floatWidth));

                                                if (bitmapCompress != null) {
                                                    drawable = new BitmapDrawable(ContentActivity.this.getResources(), bitmapCompress);
                                                    //比屏幕宽的bitmap按比例设置成和屏幕一样宽
                                                    if (screenWidth<bitmap.getWidth()) {

                                                        drawable.setBounds(0, 0, mScreenWidth, h);

                                                    }else { //比屏幕小的按原图显示
                                                        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                                                    }
                                                }
                                            }
                                            return drawable;
                                        }
                                    };
                                    CharSequence charSequence = Html.fromHtml(persons.get(0).getContent(), imageGetter, null);
                                    msg.what = 0x101;
                                    msg.obj = charSequence;
                                    mHandler.sendMessage(msg);
                                }
                            }).start();


                        } else {
                            showMessage("内容页出错");
                            pd.dismiss();
                        }


                    }
                });

    }


    /**
     * @param imageUrl
     * @return
     */
    Drawable getImageFromNetwork(String imageUrl) {
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


    @OnClick({R.id.img_back, R.id.img_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            //分享
            case R.id.img_share:
                break;
        }
    }

}
