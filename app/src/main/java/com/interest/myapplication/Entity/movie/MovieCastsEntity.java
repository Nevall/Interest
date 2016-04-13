package com.interest.myapplication.Entity.movie;

/**
 * Created by Android on 2016/3/30.
 */
public class MovieCastsEntity {
    private String alt;

    private MovieAvatarsEntity avatars;

    private String name;

    private String id;

    public void setAlt(String alt){
        this.alt = alt;
    }
    public String getAlt(){
        return this.alt;
    }
    public void setAvatars(MovieAvatarsEntity avatars){
        this.avatars = avatars;
    }
    public MovieAvatarsEntity getAvatars(){
        return this.avatars;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
}
