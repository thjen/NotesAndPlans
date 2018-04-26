package io.qthjen_dev.notesandplans.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.qthjen_dev.notesandplans.Database.MySQLite;
import io.qthjen_dev.notesandplans.Model.NoteImagesModel;
import io.qthjen_dev.notesandplans.Model.NoteModel;

public class NotesDatabaseManager {

    SQLiteDatabase database;
    MySQLite noteSQLite;

    public NotesDatabaseManager(Context context) {
        noteSQLite = new MySQLite(context);
    }

    public void openDatabase() {
        database = noteSQLite.getWritableDatabase();
    }

    public void closeDatabase() {
        noteSQLite.close();
    }

    public boolean insertNote(NoteModel note) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLite.NOTE_DESCRIPTION, note.getDescription());
        contentValues.put(MySQLite.NOTE_TIME, note.getTime());
        contentValues.put(MySQLite.NOTE_COLOR, note.getColor());
        contentValues.put(MySQLite.NOTE_IMAGE, note.getImage());

        long idNote = database.insert(MySQLite.TABLE_NOTE, null, contentValues);

        if ( idNote != 0 )
            return true;    // add successfuly
        else
            return false;   // add failed

    }

    public boolean insertNoteImages(NoteImagesModel imagesModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLite.NOTE_IMAGE_NAME, imagesModel.getImage());

        long idNoteImages = database.insert(MySQLite.TABLE_NOTE_IMAGE, null, contentValues);

        if ( idNoteImages != 0 )
            return true;
        else
            return false;

    }


}
