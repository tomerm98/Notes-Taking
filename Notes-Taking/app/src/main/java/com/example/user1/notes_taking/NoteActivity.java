package com.example.user1.notes_taking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


public class NoteActivity extends AppCompatActivity {
    Intent intent;
    boolean newNote;
    EditText etTitle, etText;
    LocalNoteService lns = new LocalNoteService(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        intent = getIntent();

        etTitle = (EditText) findViewById(R.id.etTitle);
        etText = (EditText) findViewById(R.id.etText);


    }


    @Override
    protected void onStop() {
        super.onStop();
        saveNote();
    }

    private void saveNote()
    {
        if (intent.getExtras().get("id") != null)
        {
            new EditNote().execute(intent.getExtras().get("id").toString(),
                    etTitle.getText().toString(),
                    etText.getText().toString());
        }
        else
        {
            new CreateNewNote().execute(etTitle.getText().toString(),
                    etText.getText().toString());
        }
    }


    class CreateNewNote extends AsyncTask<String,Object,Object>
    {

        @Override
        protected Integer doInBackground(String... params) {

            String title =params[0];
            String text =params[1];
            lns.createNewNote(title,text);
            return null;
        }
    }
    class EditNote extends AsyncTask<String,Object,Object>
    {

        @Override
        protected Integer doInBackground(String... params) {
            String id =params[0];
            String title = (String)params[1];
            String text = (String)params[2];
            lns.editNote(id,title,text);
            return null;
        }
    }


    

}
