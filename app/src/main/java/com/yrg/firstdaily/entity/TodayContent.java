package com.yrg.firstdaily.entity;

import java.util.ArrayList;

/**
 * Created by yrg on 2016/6/14.
 */
public class TodayContent {
    private String date;
    private ArrayList<TodayStory> stories;
    private ArrayList<TodayTopStory> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<TodayStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<TodayStory> stories) {
        this.stories = stories;
    }

    public ArrayList<TodayTopStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<TodayTopStory> top_stories) {
        this.top_stories = top_stories;
    }
}
