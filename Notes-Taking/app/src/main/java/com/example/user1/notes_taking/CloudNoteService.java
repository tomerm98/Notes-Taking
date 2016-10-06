package com.example.user1.notes_taking;

import android.content.Context;

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
import java.util.Random;

/**
 * Created by USER1 on 06/10/2016.
 */
public class CloudNoteService implements NoteServiceInterface{
    private final String ID_FILE_NAME = "id.txt";
    private final String TABLE_ID = "Notes";
    private final int idLength = 50;
    private ParseObject po;
    private String userId;
    private Context context;

    public CloudNoteService(Context context) throws IOException {
        this.context = context;

        File[] files = context.getFilesDir().listFiles();
        userId = "";
        for (File f : files) {
            if (f.getName().equals(ID_FILE_NAME)) ;
            {
                userId = "";
                FileInputStream in = context.openFileInput(ID_FILE_NAME);
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    userId += line;
                }

            }
        }
        if (userId.equals(""))
        {
            userId = generateRandomId(idLength);
            FileOutputStream fos = context.openFileOutput(ID_FILE_NAME, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            pw.print(userId);
            pw.flush();
            pw.close();
        }


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
