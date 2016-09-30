package com.example.wind.minstory2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wind on 2016/8/24.
 */
public class Videopage extends BmobObject {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    private String title;
    private String time;
    private String content;
    private int id;

}
