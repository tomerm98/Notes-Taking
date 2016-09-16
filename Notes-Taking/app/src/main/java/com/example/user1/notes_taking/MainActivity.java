package com.example.user1.notes_taking;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    GridView gv;
    ArrayList<Note> notes;
    GridViewAdapter adapter;
    LocalNoteService lns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lns = new LocalNoteService(this,20);
        gv = (GridView) findViewById(R.id.gridView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        notes = new ArrayList<Note>();
        adapter = new GridViewAdapter(notes);
        gv.setAdapter(adapter);



    }
    class InitializeNotesTask extends AsyncTask
    {

        @Override
        protected ArrayList<Note> doInBackground(Object[] objects) {
            String[] names = lns.GetNameList();
            Date tempDate;

            return null;
        }
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
            view = getLayoutInflater().inflate(R.layout.gridview_item, viewGroup, false);
            TextView tvText = (TextView) view.findViewById(R.id.tvText);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            Note tempNote = notes.get(i);

            tvText.setText(tempNote.getText());
            tvTitle.setText(tempNote.getTitle());

            return view;

        }

    }
}
