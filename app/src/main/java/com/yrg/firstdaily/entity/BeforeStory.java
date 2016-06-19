package com.yrg.firstdaily.entity;

import java.util.List;

/**
 * Created by yrg on 2016/6/19.
 */
public class BeforeStory {
    String date;
    List<Story> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
