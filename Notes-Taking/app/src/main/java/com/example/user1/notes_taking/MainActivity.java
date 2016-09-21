package com.example.user1.notes_taking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    GridView gv;
    ArrayList<Note> mainNotes;
    GridViewAdapter adapter;
    LocalNoteService lns;
    Intent noteActivityIntent;
    RelativeLayout rl;
    Snackbar sbDeleteNote;

    @Override
    protected void onRestart() {
        super.onRestart();
        new SyncNotesTask().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lns = new LocalNoteService(this);
        rl = (RelativeLayout) findViewById(R.id.rl);
        sbDeleteNote = Snackbar.make(rl,"Delete Note?",Snackbar.LENGTH_INDEFINITE);


        gv = (GridView) findViewById(R.id.gridView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        mainNotes = new ArrayList<Note>();
        adapter = new GridViewAdapter(mainNotes);
        gv.setAdapter(adapter);

        new SyncNotesTask().execute();



    }

    //the add note button onclick event method
    public void addNote(View view) {
        noteActivityIntent = new Intent(this,NoteActivity.class);
        startActivity(noteActivityIntent);
    }




    class GridViewAdapter extends BaseAdapter {
        ArrayList<Note> notes;

        public GridViewAdapter(ArrayList<Note> notes) {
            this.notes = notes;
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int i) {
            return notes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //creates a view from the gridview_item layout and puts the note data inside
            view = getLayoutInflater().inflate(R.layout.gridview_item, viewGroup, false);
            TextView tvText = (TextView) view.findViewById(R.id.tvText);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            final Note tempNote = notes.get(i);
            tvText.setText(tempNote.getText());
            tvTitle.setText(tempNote.getTitle());

            //go to NoteActivity when clicking a note
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteActivityIntent = new Intent(MainActivity.this,NoteActivity.class);
                    noteActivityIntent.putExtra(getString(R.string.EXTRA_NOTE_ID),tempNote);
                    startActivity(noteActivityIntent);
                }
            });

            //start a delete-note snackbar when long-pressing a note
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sbDeleteNote.setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DeleteNoteTask().execute(tempNote.getId());
                            new SyncNotesTask().execute();
                        }
                    });
                    sbDeleteNote.show();
                    return true;
                }
            });
            return view;

        }






    }



    //syncs the gridview with the local storage notes **no params**
    class SyncNotesTask extends AsyncTask<Object,Object,ArrayList<Note>>
    {

        @Override
        protected ArrayList<Note> doInBackground(Object[] objects) {
            ArrayList<Note> notes = lns.getNoteList();
            Collections.sort(notes);
            return notes ;
        }

        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);
            mainNotes.clear();
            for (Note n : notes)
                mainNotes.add(n);
            adapter.notifyDataSetChanged();;
            gv.setAdapter(adapter);
        }
    }

    //deletes a note from local storage. **String id**
    class DeleteNoteTask extends AsyncTask<String,Object,Object>
    {

        @Override
        protected Integer doInBackground(String... params) {

            String id =params[0];
            lns.deleteNote(id);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            new SyncNotesTask().execute();
        }
    }
}
