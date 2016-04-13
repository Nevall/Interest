package com.interest.myapplication.Entity.bookEntity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/24.
 */
public class BookTabEntity implements Serializable {

    private int count;

    private String name;

    private String title;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

}
