package com.example.user1.notes_taking;

import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public class Note {
    private String name;
    private String Title;
    private Date dateLastModified;

    public Note(String name, Date dateLastModified) {
        this.name = name;
        this.dateLastModified = dateLastModified;
        this.Title = "New Note";
    }

    public Note(String name, String title, Date dateLastModified) {
        this.name = name;
        Title = title;
        this.dateLastModified = dateLastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }
}
