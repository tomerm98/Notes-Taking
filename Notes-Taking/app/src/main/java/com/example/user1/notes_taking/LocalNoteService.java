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
    final int idLength = 25; // must be greater than 5

    public LocalNoteService(Context context) {
        this.context = context;

    }


    @Override
    public void editNote(String id, String newTitle, String newText) {
        try {
            String fileName = id + ".txt";
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(newTitle);
            pw.print(newText);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewNote(String title, String text) {
        String fileName = generateRandomId(idLength) + ".txt";

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(title);
            pw.print(text);
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
    public String getNoteText(String id) {
        String fileName = id + ".txt";
        String text = "";
        try {
            FileInputStream in = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine(); // get rid of title
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
            text = "ERROR: FILE NOT FOUND";
        }
        return text;
    }

    @Override
    public String getNoteTitle(String id) {
        String fileName = id + ".txt";
        String title;
        try {
            FileInputStream in = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            title = bufferedReader.readLine();


        } catch (IOException e) {
            e.printStackTrace();
            title = "ERROR: FILE NOT FOUND";
        }
        return title;
    }

    @Override
    public Date getDateModified(String id) {
        String fileName = id + ".txt";
        File[] files = context.getFilesDir().listFiles();
        for (File f : files)
            if (f.getName().equals(fileName))
                return new Date(f.lastModified());
        return new Date();
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
        String tempTitle, tempText, tempId;
        Date tempDate;
        for (int i = 0; i < ids.size(); i++) {
            tempId = ids.get(i);
            tempDate = getDateModified(tempId);
            tempText = getNoteText(tempId);
            tempTitle = getNoteTitle(tempId);
            if (tempDate == null) tempDate = new Date();
            notes.add(new Note(tempId, tempDate, tempTitle, tempText));
        }

        return notes;
    }

    @Override
    public int getNoteCount() {
        int count = 0;
        for (File f : context.getFilesDir().listFiles())
            if (f.getName().contains("NOTE"))
                count++;
        return count;
    }

    private String generateRandomId(int length) {
        if (length <5) return null;
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        String id = "NOTE_";
        Random rng = new Random();
        for (int i = 0; i < length-5; i++) {
            char c = chars[rng.nextInt(chars.length)];
            id += c;
        }
        return id;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
