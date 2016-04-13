package com.interest.myapplication.Entity.newsEntity;

import java.io.Serializable;
import java.util.List;

public class Latest implements Serializable{
    private List<TopStoriesEntity> top_stories;
    private List<StoriesEntity> stories;
    private String date;

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public String getDate() {
        return date;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Latest{" +
                "top_stories=" + top_stories +
                ", stories=" + stories +
                ", date='" + date + '\'' +
                '}';
    }
}
