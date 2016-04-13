package com.interest.myapplication.Entity.bookEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/24.
 */
public class BookListEntity implements Serializable {

    private int count;

    private int start;

    private int total;

    private List<BookDetailEntity> books ;

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
    public void setBooks(List<BookDetailEntity> books){
        this.books = books;
    }
    public List<BookDetailEntity> getBooks(){
        return this.books;
    }

}
