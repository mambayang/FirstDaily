package com.yrg.firstdaily.entity;

import java.util.List;

/**
 * Created by yrg on 2016/6/16.
 * 自己构建带日期的story实体
 */
public class DateStory {
    private String id;
    private String date;
    private String title;
    private String image;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
