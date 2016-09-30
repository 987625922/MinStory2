package com.example.wind.minstory2.views.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.views.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wind on 2016/7/26.
 */
public class PersonFragment extends Fragment {


    @Bind(R.id.tv_login)
    TextView mTvLogin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
