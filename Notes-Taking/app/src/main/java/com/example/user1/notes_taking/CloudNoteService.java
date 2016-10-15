package com.example.user1.notes_taking;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.parse.FindCallback;
import com.parse.Parse;
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
    private final String ID_FILE_NAME = "id.txt";
    private final String TABLE_ID = "Notes";
    private final String COLUMN_NOTE_ID = "Note Id";
    private final String COLUMN_TEXT = "Text";
    private final String COLUMN_TITLE = "Title";
    private final String COLUMN_USER_ID = "UserId";
    private final String COLUMN_LAST_MODIFIED = "Last Modified Date";
    private final int ID_LENGTH = 50;
    private final String APPLICATION_ID ="hOWu3YDCSWNu8TIKGfAX3ApSuI1i05Sy7IP7KPnY";
    private final String CLIENT_KEY ="BVgfoPAgU7wMU2vPqmv0p8ToRsesxuGqhdB0OvRe";
    private final String SERVER_ADDRESS ="https://parseapi.back4app.com/";
    private String userId;
    private Context context;


    public CloudNoteService(Context context) throws IOException {
        this.context = context;

        File[] files = context.getFilesDir().listFiles();
        userId = "";
        for (File f : files)
            if (f.getName().equals(ID_FILE_NAME)) {
                userId = readAllTextFromFile(ID_FILE_NAME, this.context);
                break;
            }

        if (userId.equals("")) {
            userId = generateRandomId(ID_LENGTH);
            writeToFile(userId,ID_FILE_NAME,this.context);
        }

        Parse.initialize(new Parse.Configuration.Builder(this.context)
                .applicationId(APPLICATION_ID)
                .clientKey(CLIENT_KEY)
                .server(SERVER_ADDRESS).build()
        );


    }
    private void writeToFile(String text, String fileName, Context context) throws FileNotFoundException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        PrintWriter pw = new PrintWriter(fos);
        pw.print(text);
        pw.close();
    }

    private String readAllTextFromFile(String fileName, Context context) throws IOException {
        String text = "";
        FileInputStream in = context.openFileInput(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            text += line;
        }
        return text;
    }

    private String generateRandomId(int length) {

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        String id = "";
        Random rng = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[rng.nextInt(chars.length)];
            id += c;
        }
        return id;
    }

    @Override
    public void saveNote(final Note n) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_NOTE_ID, n.getId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                ParseObject parse;
                if (e == null) { //note exists - editing
                    parse = objects.get(0);
                    parse.put(COLUMN_TITLE, n.getTitle());
                    parse.put(COLUMN_TEXT, n.getText());
                    parse.put(COLUMN_LAST_MODIFIED, new Date());
                } else { // note doesn't exist - creating new one
                    parse = new ParseObject(TABLE_ID);
                    parse.put(COLUMN_USER_ID, userId);
                    parse.put(COLUMN_NOTE_ID, n.getId());
                    parse.put(COLUMN_TITLE, n.getTitle());
                    parse.put(COLUMN_TEXT, n.getText());
                    parse.put(COLUMN_LAST_MODIFIED, new Date());
                }
                parse.saveInBackground();
            }
        });


    }

    @Override
    public void deleteNote(String id) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_NOTE_ID, id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                 if (e == null)
                     objects.get(0).deleteInBackground();

            }
        });
    }

    @Override
    public Note getNote(String id) {
        final Note n = new Note("Error","Error",new Date(),"Error"); // these values will stay if note not found

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_NOTE_ID, id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null)
                {
                    ParseObject parse = objects.get(0);
                    n.setText(parse.getString(COLUMN_TEXT));
                    n.setTitle(parse.getString(COLUMN_TITLE));
                    n.setDateLastModified(parse.getDate(COLUMN_LAST_MODIFIED));
                }
            }
        });

        return n;
    }

    @Override
    public ArrayList<String> getIdList() {
        final ArrayList<String> ids = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo(COLUMN_USER_ID,userId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null)
                {
                 for (ParseObject po : objects)
                     ids.add(po.getString(COLUMN_NOTE_ID));
                }
            }
        });
        return ids;
    }

    @Override
    public ArrayList<Note> getNoteList() {
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
