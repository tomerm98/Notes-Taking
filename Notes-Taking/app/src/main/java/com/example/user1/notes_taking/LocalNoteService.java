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
    private int idLength;

    public LocalNoteService(Context context,int idLength) {
        this.context = context;
        this.idLength = idLength;
    }


    @Override
    public void EditNote(String id, String newText, String newTitle) {
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
    public void CreateNewNote(String text, String title) {
        String fileName = GenerateRandomId(getIdLength()) + ".txt";

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
    public void DeleteNote(String id) {
        String fileName = id + ".txt";
        context.deleteFile(fileName);
    }

    @Override
    public String GetNoteText(String id) {
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
    public String GetNoteTitle(String id) {
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
    public Date GetDateModified(String id) {
        String fileName = id + ".txt";
        File[] files = context.getFilesDir().listFiles();
        for (File f : files)
            if (f.getName().equals(fileName))
                return new Date(f.lastModified());
        return null;
    }

    @Override
    public String[] GetIdList() {
        File[] files = context.getFilesDir().listFiles();
        String[] ids = new String[files.length];
        for (int i = 0; i < files.length; i++)
            ids[i] = files[i].getName().replace(".txt","");
        return ids;

    }

    private String GenerateRandomId(int length) {

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        String id = "";
        Random rng = new Random();
        for (int i = 0; i < length; i++) {
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

    public int getIdLength() {
        return idLength;
    }

    public void setIdLength(int idLength) {
        this.idLength = idLength;
    }
}
