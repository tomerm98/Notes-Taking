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
import java.util.List;
import java.util.Random;

/**
 * Created by USER1 on 06/10/2016.
 */
public class CloudNoteService implements NoteServiceInterface{
    private final String ID_FILE_NAME = "id.txt";
    private final String TABLE_ID = "Notes";
    private final int ID_LENGTH = 50;
    private ParseObject po;
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
                .applicationId("hOWu3YDCSWNu8TIKGfAX3ApSuI1i05Sy7IP7KPnY")
                .clientKey("BVgfoPAgU7wMU2vPqmv0p8ToRsesxuGqhdB0OvRe")
                .server("https://parseapi.back4app.com/").build()
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
    public void saveNote(Note n) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_ID);
        query.whereEqualTo("", "Dan Stemkoski");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                   //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                //    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

    @Override
    public void deleteNote(String id) {

    }

    @Override
    public Note getNote(String id) {
        return null;
    }

    @Override
    public ArrayList<String> getIdList() {
        return null;
    }

    @Override
    public ArrayList<Note> getNoteList() {
        return null;
    }
}
