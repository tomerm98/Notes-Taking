package com.example.user1.notes_taking;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by USER1 on 06/10/2016.
 */
public class CloudNoteService implements NoteServiceInterface{

    private final String TABLE_ID = "Notes";
    private final String COLUMN_NOTE_ID = "NoteId";
    private final String COLUMN_TEXT = "Text";
    private final String COLUMN_TITLE = "Title";

    private final String COLUMN_EVENT_DATE = "EventDate";

    private Context context;


    public CloudNoteService(Context context) throws IOException {
        this.context = context;

    }



    @Override
    public void saveNote(Note n) throws ParseException {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_NOTE_ID, n.getId());
        List<ParseObject> objects = query.find();
        ParseObject parse;
        if (objects.size() > 0)  //note exists - editing
            parse = objects.get(0);
        else //new note
            parse = new ParseObject(TABLE_ID);

        parse.put(COLUMN_NOTE_ID, n.getId());
        parse.put(COLUMN_TITLE, n.getTitle());
        parse.put(COLUMN_TEXT, n.getText());
        parse.put(COLUMN_EVENT_DATE, n.getEventDate());
        parse.save();
    }

    @Override
    public void deleteNote(String id) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_NOTE_ID, id);
        List<ParseObject> objects = query.find();
        if (objects.size() > 0)
            objects.get(0).deleteInBackground();
    }

    @Override
    public Note getNote(String id) throws ParseException {
        Note n = null;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_NOTE_ID, id);
        List<ParseObject> objects = query.find();
        if (objects.size() > 0)
        {

            ParseObject parse = objects.get(0);
            String text = parse.getString(COLUMN_TEXT);
            String title = parse.getString(COLUMN_TITLE);
            Date eventDate = parse.getDate(COLUMN_EVENT_DATE);
            n = new Note(title,text,eventDate,id);
        }

        return n;
    }

    @Override
    public ArrayList<String> getIdList() throws ParseException {
         ArrayList<String> ids = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        List<ParseObject> objects = query.find();
        if (objects.size() > 0)
            for (ParseObject po : objects)
                ids.add(po.getString(COLUMN_NOTE_ID));

        return ids;
    }

    @Override
    public ArrayList<Note> getNoteList() throws ParseException {

        ArrayList<String> ids = getIdList();
        ArrayList<Note> notes = new ArrayList<>();
        String  tempId;
        for (int i = 0; i < ids.size(); i++) {
            tempId = ids.get(i);
            notes.add(getNote(tempId));
        }

        return notes;
    }
}
