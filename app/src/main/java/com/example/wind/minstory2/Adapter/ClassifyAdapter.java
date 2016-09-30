package com.example.wind.minstory2.Adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.bean.Classifypage;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by wind on 2016/8/11.
 */
public class ClassifyAdapter extends BaseQuickAdapter<Classifypage> {
    private SimpleDraweeView simpleDraweeView;
    public ClassifyAdapter(int layoutResId, List<Classifypage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Classifypage data) {
        baseViewHolder.setText(R.id.tv_title,data.getTitle());
        simpleDraweeView = (SimpleDraweeView)baseViewHolder.getView(R.id.img_classify);
        simpleDraweeView.setImageURI(Uri.parse(data.getImg().getUrl()));



    }
}
