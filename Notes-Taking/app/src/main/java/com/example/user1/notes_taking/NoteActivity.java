package com.example.user1.notes_taking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class NoteActivity extends AppCompatActivity {
    Intent intent;
    Note note;
    EditText etTitle, etText;
    Button btnDate;
    CloudNoteService cns;
    RelativeLayout rl;
    final int DIALOG_ID = 777;
    DatePickerDialog.OnDateSetListener myDateListener;
    int defaultYear, defaultMonth, defaultDay; //month must be -1
    Calendar calendar;
    Date chosenDate;
    public NoteActivity() throws IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        calendar = Calendar.getInstance();

        rl = (RelativeLayout) findViewById(R.id.rl);
        try {
            cns = new CloudNoteService(this);
        } catch (IOException e) {
            Snackbar.make(rl, "Error Connecting With Cloud", Snackbar.LENGTH_LONG).show();
        }
        intent = getIntent();
        note = (Note) intent.getSerializableExtra(MainActivity.EXTRA_NOTE_ID);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etText = (EditText) findViewById(R.id.etText);
        btnDate = (Button) findViewById(R.id.btnDate);
        if (note != null) {
            etTitle.setText(note.getTitle());
            etText.setText(note.getText());

            defaultDay = note.getEventDate().getDate();
            defaultMonth = note.getEventDate().getMonth();
            defaultYear = note.getEventDate().getYear();

            chosenDate = new Date(defaultYear,defaultMonth,defaultDay);

        }
        else
        {
            defaultDay = calendar.get(Calendar.DAY_OF_MONTH);
            defaultMonth = calendar.get(Calendar.MONTH);
            defaultYear = calendar.get(Calendar.YEAR); //calendar uses normal values for year
            chosenDate = new Date();
        }

        updateBtnDateText();
          myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                // arg1 is year BUT WITH -1900
                // arg2 is month
                // arg3 is day
                defaultYear = arg1 + 1900;
                defaultMonth = arg2;
                defaultDay = arg3 ;
                chosenDate = new Date(defaultYear,defaultMonth,defaultDay);

                updateBtnDateText();
            }
        };
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
            } else note = new Note(etTitle.getText().toString(),
                                 etText.getText().toString(),
                                chosenDate);
            cns.saveNote(note);
        }
    }


    private boolean isNoteEmpty() {
        return etTitle.getText().toString().equals("") &&
                etText.getText().toString().equals("");
    }


    public void btnDateOnClickListener(View view) {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this,myDateListener, defaultYear +1900, defaultMonth, defaultDay); //default values.


        return super.onCreateDialog(id);
    }
    private void updateBtnDateText()
    {
        btnDate.setText(String.valueOf(defaultDay) + "." +
                String.valueOf(defaultMonth +1) + "." +
                String.valueOf(defaultYear));
    }

}

