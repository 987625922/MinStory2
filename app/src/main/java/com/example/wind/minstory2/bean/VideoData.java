package com.example.wind.minstory2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wind on 2016/9/27.
 */

public class VideoData extends BmobObject {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private int id;
    private String title;
    private String url;
    private String imgUrl;




}