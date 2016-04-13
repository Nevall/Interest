package com.interest.myapplication.Entity.bookEntity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/24.
 */
public class BookAuthor implements Serializable {
    private String[] author;

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }
}
