package com.yrg.firstdaily.entity;

import java.util.List;

/**
 * Created by yrg on 2016/6/20.
 */
public class ThemePsychology {
    //背景图片小图版
    private String image;
    //背景图片大图版
    private String background;
    //主题日报介绍
    private String description;
    //主题日报名称
    private String name;
    //主题日报编辑
    private List<ThemeEditor> editors;
    //文章列表
    private List<Story> stories;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ThemeEditor> getEditors() {
        return editors;
    }

    public void setEditors(List<ThemeEditor> editors) {
        this.editors = editors;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
