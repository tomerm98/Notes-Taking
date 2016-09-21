package com.example.user1.notes_taking;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Created by USER1 on 15/09/2016.
 */
public class Note implements Comparable<Note>,Serializable{
    private String id;
    private String title;
    private Date dateLastModified;
    private String text;
    private final int idLength = 25;
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        dateLastModified = new Date();
        id = generateRandomId(idLength);
    }

    public Note(String title, String text, Date dateLastModified) {
        this.title = title;
        this.text = text;
        this.dateLastModified = dateLastModified;
        id = generateRandomId(idLength);
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

    private String generateRandomId(int length) {
        if (length <5) length = 5;
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        String id = "NOTE_";
        Random rng = new Random();
        for (int i = 0; i < length-5; i++) {
            char c = chars[rng.nextInt(chars.length)];
            id += c;
        }
        return id;
    }

    @Override
    public int compareTo(@NonNull Note other) {
        return - getDateLastModified().compareTo(((Note)other).getDateLastModified());
    }
}
