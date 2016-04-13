package com.interest.myapplication.Entity.bookEntity;

import com.interest.myapplication.Entity.SaveEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/24.
 */
public class BookDetailEntity extends SaveEntity implements Serializable{
    private BookRating rating;

    private String subtitle;

    private List<String> author ;

    private String pubdate;

    private List<BookTabEntity> tags ;

    private String origin_title;

    private String image;

    private String binding;

    private List<String> translator ;

    private String catalog;

    private String ebook_url;

    private String pages;

    private BookImageEntity images;

    private String alt;

    private String id;

    private String publisher;

    private String isbn10;

    private String isbn13;

    private String title;

    private String url;

    private String alt_title;

    private String author_intro;

    private String summary;

    private String ebook_price;

    private String price;

    public void setRating(BookRating rating){
        this.rating = rating;
    }
    public BookRating getRating(){
        return this.rating;
    }
    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }
    public String getSubtitle(){
        return this.subtitle;
    }
    public void setAuthor(List<String> author){
        this.author = author;
    }
    public List<String> getAuthor(){
        return this.author;
    }
    public void setPubdate(String pubdate){
        this.pubdate = pubdate;
    }
    public String getPubdate(){
        return this.pubdate;
    }
    public void setTags(List<BookTabEntity> tags){
        this.tags = tags;
    }
    public List<BookTabEntity> getTags(){
        return this.tags;
    }
    public void setOrigin_title(String origin_title){
        this.origin_title = origin_title;
    }
    public String getOrigin_title(){
        return this.origin_title;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setBinding(String binding){
        this.binding = binding;
    }
    public String getBinding(){
        return this.binding;
    }
    public void setTranslator(List<String> translator){
        this.translator = translator;
    }
    public List<String> getTranslator(){
        return this.translator;
    }
    public void setCatalog(String catalog){
        this.catalog = catalog;
    }
    public String getCatalog(){
        return this.catalog;
    }
    public void setEbook_url(String ebook_url){
        this.ebook_url = ebook_url;
    }
    public String getEbook_url(){
        return this.ebook_url;
    }
    public void setPages(String pages){
        this.pages = pages;
    }
    public String getPages(){
        return this.pages;
    }
    public void setImages(BookImageEntity images){
        this.images = images;
    }
    public BookImageEntity getImages(){
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
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public String getPublisher(){
        return this.publisher;
    }
    public void setIsbn10(String isbn10){
        this.isbn10 = isbn10;
    }
    public String getIsbn10(){
        return this.isbn10;
    }
    public void setIsbn13(String isbn13){
        this.isbn13 = isbn13;
    }
    public String getIsbn13(){
        return this.isbn13;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setAlt_title(String alt_title){
        this.alt_title = alt_title;
    }
    public String getAlt_title(){
        return this.alt_title;
    }
    public void setAuthor_intro(String author_intro){
        this.author_intro = author_intro;
    }
    public String getAuthor_intro(){
        return this.author_intro;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setEbook_price(String ebook_price){
        this.ebook_price = ebook_price;
    }
    public String getEbook_price(){
        return this.ebook_price;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return this.price;
    }
}
