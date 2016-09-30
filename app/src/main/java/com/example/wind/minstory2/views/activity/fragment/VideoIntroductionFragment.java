package com.example.wind.minstory2.views.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseFragment;

/**
 * Created by wind on 2016/9/28.
 */

public class VideoIntroductionFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_video_introduction, container, false);

        return view;
    }


}
