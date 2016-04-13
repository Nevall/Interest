package com.interest.myapplication.Entity.newsEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/5.
 * 过往新闻消息实体类
 */
public class Before implements Serializable{
    private List<StoriesEntity> stories;
    private String date;

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Before{" +
                "stories=" + stories +
                ", date='" + date + '\'' +
                '}';
    }
}
