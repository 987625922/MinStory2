package com.example.wind.minstory2.Adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.bean.Homepage;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by wind on 2016/7/19.
 */
public class MainRececlyAdapter extends BaseQuickAdapter<Homepage> {
    SimpleDraweeView simpleDraweeView;

    public MainRececlyAdapter(int layoutResId, List<Homepage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Homepage homepage) {
        baseViewHolder.setText(R.id.tv_title, homepage.getTitle())
        .setText(R.id.tv_time,homepage.getTime())
        .setText(R.id.tv_good,homepage.getAuthor())
        .setText(R.id.tv_readed,homepage.getProvenance());
        simpleDraweeView = baseViewHolder.getView(R.id.ic_img);
        simpleDraweeView.setImageURI(Uri.parse(homepage.getImgUrl()));

    }
}
