package com.example.wind.minstory2.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by wind on 2016/8/6.
 */
public class BaseFragment extends Fragment {

        public void showMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

    }

}
