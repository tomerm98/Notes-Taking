package com.example.user1.notes_taking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;

import java.io.IOException;


public class NoteActivity extends AppCompatActivity {
    Intent intent;
    Note note;
    EditText etTitle, etText;
    CloudNoteService cns;
    RelativeLayout rl;

    public NoteActivity() throws IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        rl = (RelativeLayout) findViewById(R.id.rl);
        try {
            cns = new CloudNoteService(this);
        } catch (IOException e) {
            Snackbar.make(rl, "Error Connecting With Cloud", Snackbar.LENGTH_LONG).show();
        }
        intent = getIntent();
        note = (Note) intent.getSerializableExtra(getString(R.string.EXTRA_NOTE_ID));
        etTitle = (EditText) findViewById(R.id.etTitle);
        etText = (EditText) findViewById(R.id.etText);
        if (note != null) {
            etTitle.setText(note.getTitle());
            etText.setText(note.getText());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            saveNote();
        } catch (ParseException e) {
            Toast.makeText(this,"Failed To Save Note",Toast.LENGTH_LONG).show();
        }
        finish();
    }


    private void saveNote() throws ParseException {

        if (!isNoteEmpty()) {
            if (note != null) {
                note.setText(etText.getText().toString());
                note.setTitle(etTitle.getText().toString());
            } else note = new Note(etTitle.getText().toString(), etText.getText().toString());
            cns.saveNote(note);
        }
    }


    private boolean isNoteEmpty() {
        return etTitle.getText().toString().equals("") &&
                etText.getText().toString().equals("");
    }


}
