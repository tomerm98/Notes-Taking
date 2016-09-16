package com.example.user1.notes_taking;

import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public class Note implements Comparable{
    private String name;
    private String title;
    private Date dateLastModified;
    private String text;

    public Note(String name) {
        this.name = name;
        this.dateLastModified = new Date();
        this.title = "New Note";
        this.text = "";
    }

    public Note(String name, Date dateLastModified, String title,String text) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public int compareTo(Object o) {
        return getDateLastModified().compareTo(((Note)o).getDateLastModified());
    }
}
