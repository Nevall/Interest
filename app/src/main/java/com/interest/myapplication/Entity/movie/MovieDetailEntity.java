package com.interest.myapplication.Entity.movie;

import java.util.List;

/**
 * Created by Android on 2016/3/29.
 */
public class MovieDetailEntity {
    private MovieRatingEntity rating;

    private List<String> genres ;

    private String title;

    private List<MovieCastsEntity> casts ;

    private int collect_count;

    private String original_title;

    private String subtype;

    private List<MovieDirectorEntity> directors ;

    private String year;

    private MovieImageEntity images;

    private String alt;

    private String id;

    public void setRating(MovieRatingEntity rating){
        this.rating = rating;
    }
    public MovieRatingEntity getRating(){
        return this.rating;
    }
    public void setGenres(List<String> genres){
        this.genres = genres;
    }
    public List<String> getGenres(){
        return this.genres;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setCasts(List<MovieCastsEntity> casts){
        this.casts = casts;
    }
    public List<MovieCastsEntity> getCasts(){
        return this.casts;
    }
    public void setCollect_count(int collect_count){
        this.collect_count = collect_count;
    }
    public int getCollect_count(){
        return this.collect_count;
    }
    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    }
    public String getOriginal_title(){
        return this.original_title;
    }
    public void setSubtype(String subtype){
        this.subtype = subtype;
    }
    public String getSubtype(){
        return this.subtype;
    }
    public void setDirectors(List<MovieDirectorEntity> directors){
        this.directors = directors;
    }
    public List<MovieDirectorEntity> getDirectors(){
        return this.directors;
    }
    public void setYear(String year){
        this.year = year;
    }
    public String getYear(){
        return this.year;
    }
    public void setImages(MovieImageEntity images){
        this.images = images;
    }
    public MovieImageEntity getImages(){
        return this.images;
    }
    public void setAlt(String alt){
        this.alt = alt;
    }
    public String getAlt(){
        return this.alt;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
}
