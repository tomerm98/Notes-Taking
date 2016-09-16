package com.example.user1.notes_taking;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public class Note implements Comparable<Note>{
    private String id;
    private String title;
    private Date dateLastModified;
    private String text;

    public Note(String id) {
        this.id = id;
        this.dateLastModified = new Date();
        this.title = "New Note";
        this.text = "";
    }

    public Note(String id, Date dateLastModified, String title,String text) {
        this.id = id;
        this.title = title;
        this.dateLastModified = dateLastModified;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }


    @Override
    public int compareTo(@NonNull Note other) {
        return - getDateLastModified().compareTo(((Note)other).getDateLastModified());
    }
}
