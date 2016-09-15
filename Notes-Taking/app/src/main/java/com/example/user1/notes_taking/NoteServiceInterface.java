package com.example.user1.notes_taking;

/**
 * Created by USER1 on 15/09/2016.
 */
public interface NoteServiceInterface {
    public void EditNote(String name, String newText);
    public void CreateNewNote(String text);
    public void DeleteNote(String name);
    public String GetNoteText(String name);
    public String[] GetNameList();

}
