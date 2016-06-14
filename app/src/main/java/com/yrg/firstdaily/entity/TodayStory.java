package com.yrg.firstdaily.entity;

import java.util.ArrayList;

/**
 * Created by yrg on 2016/6/14.
 */
public class TodayStory {
    private String id;
    private String title;
    private String [] images;

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

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
