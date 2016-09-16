package com.example.user1.notes_taking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    GridView gv;
    ArrayList<Note> notes;
    ArrayAdapter<Note> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = (GridView) findViewById(R.id.gridView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        notes = new ArrayList<>();
        adapter = new ArrayAdapter<Note>(this,R.layout.support_simple_spinner_dropdown_item,notes);
        gv.setAdapter(adapter);
        adapter.add(new Note("name",new Date()));
        adapter.add(new Note("name",new Date()));
        adapter.add(new Note("name",new Date()));
        adapter.add(new Note("name",new Date()));

    }
}
