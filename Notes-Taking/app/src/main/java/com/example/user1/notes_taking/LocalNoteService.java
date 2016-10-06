package com.example.user1.notes_taking;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by USER1 on 15/09/2016.
 */
public class LocalNoteService implements NoteServiceInterface {

    private Context context;


    public LocalNoteService(Context context) {
        this.context = context;

    }


    @Override
    public void saveNote(Note n) {
        try {
            String fileName = n.getId() + ".txt";
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(n.getTitle());
            pw.print(n.getText());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void deleteNote(String id) {
        String fileName = id + ".txt";
        context.deleteFile(fileName);
    }

    @Override
    public Note getNote(String id) {
        String fileName = id + ".txt";
        String text = "";
        String title = "";
        Date date = new Date();
        try {
            File f = new File(fileName);
            Date lastModified = new Date(f.lastModified());

            FileInputStream in = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            title = bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text += line;
            }



        } catch (IOException e) {
            e.printStackTrace();
            text = "ERROR: FILE NOT FOUND";
        }
        return new Note(title,text,date,id);
    }



    @Override
    public ArrayList<String> getIdList() {
        File[] files = context.getFilesDir().listFiles();
        ArrayList<String> ids = new ArrayList<>();
        for (File f : files)
            if (f.getName().contains("NOTE"))
            ids.add(f.getName().replace(".txt", ""));
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




    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
