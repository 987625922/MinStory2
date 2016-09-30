package com.example.wind.minstory2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wind on 2016/8/5.
 */
public class User extends BmobObject {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String email;
    private String password;
    private String username;




}
