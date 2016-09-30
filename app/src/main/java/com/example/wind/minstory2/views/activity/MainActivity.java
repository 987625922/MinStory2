package com.example.wind.minstory2.views.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;
import com.example.wind.minstory2.views.activity.fragment.ClassifyFragment;
import com.example.wind.minstory2.views.activity.fragment.MainFragment;
import com.example.wind.minstory2.views.activity.fragment.PersonFragment;
import com.example.wind.minstory2.views.activity.fragment.VideoFragment;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseAppActivity {


    @Bind(R.id.img_news)
    SimpleDraweeView mImgNews;
    @Bind(R.id.img_classify)
    SimpleDraweeView mImgClassify;
    @Bind(R.id.img_video)
    SimpleDraweeView mImgVideo;
    @Bind(R.id.img_person)
    SimpleDraweeView mImgPerson;
    private MainFragment mainFragment;
    private ClassifyFragment classifyFragment;
    private VideoFragment videoFragment;
    private PersonFragment personFragment;
    private Fragment[] fragments;
    private GenericDraweeHierarchy hierarchy;
    private int currentTabIndex = 1;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        mainFragment = new MainFragment();
        classifyFragment = new ClassifyFragment();
        videoFragment = new VideoFragment();
        personFragment = new PersonFragment();
        fragments = new Fragment[]{mainFragment, classifyFragment,
                videoFragment, personFragment};
        setCurrentTab(0);
    }

    public void setCurrentTab(int tabIndex) {
        if (currentTabIndex != tabIndex) {
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.hide(fragments[currentTabIndex]);

            if (!fragments[tabIndex].isAdded()) {
                tr.add(R.id.vp_content, fragments[tabIndex]);
            }

            tr.show(fragments[tabIndex]).commit();
            switch (tabIndex) {
                case 0:
                    mImgNews.getHierarchy().setPlaceholderImage(R.mipmap.ic_dashboard_black_selected);
                    mImgClassify.getHierarchy().setPlaceholderImage(R.mipmap.ic_class_black);
                    mImgVideo.getHierarchy().setPlaceholderImage(R.mipmap.ic_airplay_black);
                    mImgPerson.getHierarchy().setPlaceholderImage(R.mipmap.ic_account_box_black);
                    break;
                case 1:
                    mImgNews.getHierarchy().setPlaceholderImage(R.mipmap.ic_dashboard_black);
                    mImgClassify.getHierarchy().setPlaceholderImage(R.mipmap.ic_class_black_selected);
                    mImgVideo.getHierarchy().setPlaceholderImage(R.mipmap.ic_airplay_black);
                    mImgPerson.getHierarchy().setPlaceholderImage(R.mipmap.ic_account_box_black);
                    break;
                case 2:
                    mImgNews.getHierarchy().setPlaceholderImage(R.mipmap.ic_dashboard_black);
                    mImgClassify.getHierarchy().setPlaceholderImage(R.mipmap.ic_class_black);
                    mImgVideo.getHierarchy().setPlaceholderImage(R.mipmap.ic_airplay_black_selected);
                    mImgPerson.getHierarchy().setPlaceholderImage(R.mipmap.ic_account_box_black);
                    break;
                case 3:
                    mImgNews.getHierarchy().setPlaceholderImage(R.mipmap.ic_dashboard_black);
                    mImgClassify.getHierarchy().setPlaceholderImage(R.mipmap.ic_class_black);
                    mImgVideo.getHierarchy().setPlaceholderImage(R.mipmap.ic_airplay_black);
                    mImgPerson.getHierarchy().setPlaceholderImage(R.mipmap.ic_account_box_black_selected);
                    break;
            }

            currentTabIndex = tabIndex;
        }
    }

    @OnClick({R.id.img_news, R.id.img_classify, R.id.img_video, R.id.img_person,R.id.btn_recycle_top})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_news:
                setCurrentTab(0);
                break;
            case R.id.img_classify:
                setCurrentTab(1);
                break;
            case R.id.img_video:
                setCurrentTab(2);

                break;
            case R.id.img_person:
                setCurrentTab(3);
                break;
            case R.id.btn_recycle_top:
                if (currentTabIndex == 0) {
                    mainFragment.recyclerTop();
                }else if (currentTabIndex == 2){
                    videoFragment.recyclerTop();
                }else if (currentTabIndex == 1){
                    classifyFragment.recyclerTop();
                }
                break;
        }
    }

}
