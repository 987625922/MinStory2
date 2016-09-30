package com.example.wind.minstory2.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wind.minstory2.R;
import com.example.wind.minstory2.bean.Videopage;

import java.util.List;

/**
 * Created by wind on 2016/8/24.
 */
public class VideoAdapter  extends BaseQuickAdapter<Videopage> {
    public VideoAdapter(int layoutResId, List<Videopage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Videopage videopage) {

        baseViewHolder.setText(R.id.tv_title,videopage.getTitle()).setText(R.id.tv_time,
                videopage.getTime()).setText(R.id.tv_introduction,videopage.getContent());


    }
}
