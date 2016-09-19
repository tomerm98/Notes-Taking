package com.example.user1.notes_taking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteActivity extends AppCompatActivity {
    Intent intent;
    boolean newNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        intent = getIntent();

    }
}
