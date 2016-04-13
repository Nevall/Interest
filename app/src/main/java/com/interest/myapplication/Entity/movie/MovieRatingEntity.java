package com.interest.myapplication.Entity.movie;

/**
 * Created by Android on 2016/3/30.
 */
public class MovieRatingEntity {
    private int max;

    private double average;

    private String stars;

    private int min;

    public void setMax(int max){
        this.max = max;
    }
    public int getMax(){
        return this.max;
    }
    public void setAverage(double average){
        this.average = average;
    }
    public double getAverage(){
        return this.average;
    }
    public void setStars(String stars){
        this.stars = stars;
    }
    public String getStars(){
        return this.stars;
    }
    public void setMin(int min){
        this.min = min;
    }
    public int getMin(){
        return this.min;
    }
}
