package com.example.user1.notes_taking;

import java.util.Date;

/**
 * Created by USER1 on 15/09/2016.
 */
public interface NoteServiceInterface {
    public void EditNote(String id , String newText,String newTitle);
    public void CreateNewNote(String text,String title);
    public void DeleteNote(String id);
    public String GetNoteText(String id);
    public String GetNoteTitle(String id);
    public Date GetDateModified(String id);
    public String[] GetIdList();

}
