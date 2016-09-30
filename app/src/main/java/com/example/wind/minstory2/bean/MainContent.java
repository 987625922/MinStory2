package com.example.wind.minstory2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wind on 2016/8/8.
 */
public class MainContent extends BmobObject {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String title;
    private String content;


}
