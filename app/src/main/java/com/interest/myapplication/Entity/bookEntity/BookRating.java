package com.interest.myapplication.Entity.bookEntity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/24.
 */
public class BookRating implements Serializable {
    private int max;

    private int numRaters;

    private String average;

    private int min;

    public void setMax(int max){
        this.max = max;
    }
    public int getMax(){
        return this.max;
    }
    public void setNumRaters(int numRaters){
        this.numRaters = numRaters;
    }
    public int getNumRaters(){
        return this.numRaters;
    }
    public void setAverage(String average){
        this.average = average;
    }
    public String getAverage(){
        return this.average;
    }
    public void setMin(int min){
        this.min = min;
    }
    public int getMin(){
        return this.min;
    }
}
