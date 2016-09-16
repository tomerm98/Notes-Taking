package com.example.user1.notes_taking;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = (GridView) findViewById(R.id.gridView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        notes = new ArrayList<Note>();
       notes.add(new Note("name",new Date(),"title1","Text1"));
      // notes.add(new Note("name",new Date(),"title2","Text2"));
      // notes.add(new Note("name",new Date(),"title3","Text3"));
      // notes.add(new Note("name",new Date(),"title4","Text4"));
        adapter = new GridViewAdapter(notes);
        gv.setAdapter(adapter);


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
            view = getLayoutInflater().inflate(R.layout.gridview_item, viewGroup);
            TextView tvText = (TextView) view.findViewById(R.id.tvText);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            Note tempNote = notes.get(i);

            tvText.setText(tempNote.getText());
            tvTitle.setText(tempNote.getTitle());

            return view;

            //***This is an alternative better version. but first, the bug must be EXTERMINATED
            //ViewHolder vh;
            //if (view == null)
            //{
            //    vh = new ViewHolder();
            //    view = getLayoutInflater().inflate(R.layout.gridview_item,viewGroup);
            //    vh.tvText = (TextView) view.findViewById(R.id.tvText);
            //    vh.tvText = (TextView) view.findViewById(R.id.tvText);
            //    view.setTag(vh);
            //}
            //else vh = (ViewHolder) view.getTag();
//
            //Note tempNote = notes.get(i);
            //if (tempNote != null) {
            //    vh.tvText.setText(tempNote.getText());
            //    vh.tvTitle.setText(tempNote.getTitle());
            //}
//
            //return view;
        }
        // static class ViewHolder
        // {
        //     TextView tvTitle,tvText;
        // }
    }


}
