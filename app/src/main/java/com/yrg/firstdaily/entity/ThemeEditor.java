package com.yrg.firstdaily.entity;

/**
 * Created by yrg on 2016/6/20.
 */
public class ThemeEditor {
    //主编姓名
    private String name;
    //主编简介
    private String bio;
    //主编头像
    private String avatar;
    //主编主页
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
