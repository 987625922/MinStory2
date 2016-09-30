package com.example.wind.minstory2.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wind.minstory2.R;
import com.example.wind.minstory2.base.BaseAppActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wind on 2016/8/10.
 */
public class MainSearchActivity extends BaseAppActivity {
    @Bind(R.id.tv_cancel)
    TextView mTvCancel;
    @Bind(R.id.cancle)
    RelativeLayout mCancle;
    @Bind(R.id.edt_search)
    EditText mEdtSearch;
    @Bind(R.id.ll_searchs)
    LinearLayout mLlSearchs;
    @Bind(R.id.listview)
    ListView mListview;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main_search;
    }

    @Override
    protected void initViewsAndEvents() {

        mEdtSearch.setOnEditorActionListener(new EditActionListener());
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] searchs = null;
        File file = new File(mContext.getFilesDir(), "MainSearchNote.txt");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            searchs = bufferedReader.readLine().split(",");
            fis.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchs != null) {
            mLlSearchs.setVisibility(View.VISIBLE);
            ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.list_item_search_note, searchs);

            mListview.setAdapter(arrayAdapter);
            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    String searchStr = ((TextView) view).getText().toString();
                    bundle.putString("keyword", searchStr);
                    mEdtSearch.setText(searchStr);
                    intent.putExtras(bundle);
                    intent.setClass(mContext,MainSearchResultActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    @OnClick({R.id.black, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.black:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(MainSearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                finish();
                break;
            case R.id.tv_cancel:
                break;
        }
    }


    /**
     * 搜索
     */
    private class EditActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ((InputMethodManager) mEdtSearch.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(mActivity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String searchStr = mEdtSearch.getText().toString().trim();
                if (searchStr.equals("")) {
                    showMessage("请输入搜索词");
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", searchStr);
                    saveData(searchStr);
                    intent.putExtras(bundle);
                    intent.setClass(mContext,MainSearchResultActivity.class);
                    startActivity(intent);


                }
                return true;
            }
            return false;
        }
    }

    /**
     * 存数据
     *
     * @param searchStr
     */
    private void saveData(String searchStr) {
        String[] searchs = null;
        File file = new File(mContext.getFilesDir(), "MainSearchNote.txt");
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
                searchs = bufferedReader.readLine().split(",");
                fis.close();
                bufferedReader.close();
                for (String search : searchs) {
                    if (search.equals(searchStr))
                        return;
                }
            }
            FileOutputStream fos = null;
            fos = mContext.openFileOutput("MainSearchNote.txt", Context.MODE_APPEND);
            fos.write((searchStr + ",").getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
