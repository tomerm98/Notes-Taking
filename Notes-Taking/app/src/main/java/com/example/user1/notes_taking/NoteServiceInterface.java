package com.example.user1.notes_taking;

import com.parse.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public interface
NoteServiceInterface {

    void saveNote(Note n) throws ParseException, FileNotFoundException;
    void deleteNote(String id) throws ParseException;
    Note getNote(String id) throws IOException, ParseException;
    ArrayList<String> getIdList() throws ParseException;
    ArrayList<Note> getNoteList() throws IOException, ParseException;

}
