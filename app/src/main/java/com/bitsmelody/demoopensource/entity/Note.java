package com.bitsmelody.demoopensource.entity;

import java.util.Date;

/**
 * Created by black on 2016/3/3.
 */
public class Note {

    private Long id;
    /** Not-null value. */
    private String text;
    private String comment;
    private Date date;

    public Note(){

    }

    public Note(Long id) {
        this.id = id;
    }

    public Note(Long id, String text, String comment, Date date) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getText() {
        return text;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
