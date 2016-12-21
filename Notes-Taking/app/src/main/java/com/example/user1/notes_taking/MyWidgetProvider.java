package com.example.user1.notes_taking;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.parse.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Tomer on 21/12/2016.
 */

public class MyWidgetProvider extends AppWidgetProvider {
    CloudNoteService cns;
    ArrayList<Note> notes;
    RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        try {
            cns = new CloudNoteService(context);
            notes = cns.getNoteList();
        } catch (IOException e) {
            throw new RuntimeException();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Note nearestNote = getNearestNote(notes);
        if (nearestNote == null)
            return;
        remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
        remoteViews.setTextViewText(R.id.tvNoteTitle,nearestNote.getTitle());
        remoteViews.setTextViewText(R.id.tvDate,nearestNote.getEventDate().toString());

        for (int i =0; i<appWidgetIds.length; i++)
        {
            int widgetId = appWidgetIds[i];
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
    private Note getNearestNote(ArrayList<Note> notes)  {
        Note nearestNote = null;
        if (notes.size() > 0)
        {
            Collections.sort(notes);
            for (int i = notes.size()-1; i>= 0; i--)
            {
                if (nearestNote.getEventDate().compareTo(new Date()) >= 0)
                    return nearestNote;
            }
        }
        return nearestNote;
    }

}
