package com.interest.myapplication.Entity.movie;

import java.util.List;

/**
 * Created by Android on 2016/3/29.
 */
public class MovieListEntity {
    private int count;

    private int start;

    private int total;

    private List<MovieDetailEntity> subjects ;

    private String title;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setSubjects(List<MovieDetailEntity> subjects){
        this.subjects = subjects;
    }
    public List<MovieDetailEntity> getSubjects(){
        return this.subjects;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
