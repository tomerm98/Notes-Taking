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
    private Date eventDate;
    private String text;
    private final int idLength = 25;


    public Note(String title, String text, Date eventDate) {
        this.title = title;
        this.text = text;
        this.eventDate = eventDate;
        id = generateRandomId(idLength);
    }

    public Note(String title, String text, Date eventDate, String id) {
        this.title = title;
        this.text = text;
        this.eventDate = eventDate;
        this.id = id;
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
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
        return - getEventDate().compareTo(((Note)other).getEventDate());
    }
}
