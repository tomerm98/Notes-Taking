package com.example.user1.notes_taking;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public interface
NoteServiceInterface {
    public void editNote(String id , String newTitle, String newText);
    public void createNewNote(String title,String text);
    public void deleteNote(String id);
    public String getNoteText(String id);
    public String getNoteTitle(String id);
    public Date getDateModified(String id);
    public ArrayList<String> getIdList();
    public ArrayList<Note> getNoteList();
    public int getNoteCount();

}
