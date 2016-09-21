package com.example.user1.notes_taking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


public class NoteActivity extends AppCompatActivity {
    Intent intent;
    Note note;
    EditText etTitle, etText;
    LocalNoteService lns = new LocalNoteService(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        intent = getIntent();
        note = (Note) intent.getSerializableExtra(getString(R.string.EXTRA_NOTE_ID));
        etTitle = (EditText) findViewById(R.id.etTitle);
        etText = (EditText) findViewById(R.id.etText);
        if (note != null)
        {
            etTitle.setText(note.getTitle());
            etText.setText(note.getText());
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        saveNote();
    }

    private void saveNote()
    {

       if (!(etTitle.getText().toString().equals("") && etText.getText().toString().equals("")))
       {
           note.setText(etText.getText().toString());
           note.setTitle(etTitle.getText().toString());
           new SaveTask().execute(note);
       }
    }


    class SaveTask extends AsyncTask<Note ,Object,Object>
    {

        @Override
        protected Object doInBackground(Note... notes) {
            for (Note n : notes)
                lns.saveNote(n);
            return null;
        }
    }



    

}
