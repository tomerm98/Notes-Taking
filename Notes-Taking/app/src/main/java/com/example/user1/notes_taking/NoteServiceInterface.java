package com.example.user1.notes_taking;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public interface
NoteServiceInterface {
    void saveNote(Note n);
    void deleteNote(String id);
    Note getNote(String id);
    ArrayList<String> getIdList();
    ArrayList<Note> getNoteList();

}
