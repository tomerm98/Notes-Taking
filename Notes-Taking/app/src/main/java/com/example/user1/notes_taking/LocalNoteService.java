package com.example.user1.notes_taking;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public void EditNote(String name, String newText) {
        try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(newText.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CreateNewNote(String text) {
        String name = GenerateRandomId(20) + ".txt";
        try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteNote(String name) {
        context.deleteFile(name);
    }

    @Override
    public String GetNoteText(String name) {
        String text = "";
        try {
            FileInputStream in = context.openFileInput(name);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
    public Date GetDateModified(String name) {
        File[] files = context.getFilesDir().listFiles();
        for (File f : files)
            if (f.getName().equals(name))
                return new Date(f.lastModified());
        return null;
    }

    @Override
    public String[] GetNameList() {
        File[] files = context.getFilesDir().listFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++)
            names[i] = files[i].getName();
        return names;

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

}
