package com.interest.myapplication.Entity.bookEntity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/24.
 */
public class BookImageEntity implements Serializable {
    private String small;

    private String large;

    private String medium;

    public void setSmall(String small){
        this.small = small;
    }
    public String getSmall(){
        return this.small;
    }
    public void setLarge(String large){
        this.large = large;
    }
    public String getLarge(){
        return this.large;
    }
    public void setMedium(String medium){
        this.medium = medium;
    }
    public String getMedium(){
        return this.medium;
    }
}
