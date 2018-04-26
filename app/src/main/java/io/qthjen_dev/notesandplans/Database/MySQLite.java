package io.qthjen_dev.notesandplans.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Blob;

public class MySQLite extends SQLiteOpenHelper {

    public static final String DB_NAME = "noteandplan";
    public static final int DB_VERSION = 1;

    /** note **/
    public static final String TABLE_NOTE = "Notes";
    public static final String NOTE_ID = "id";
    public static final String NOTE_DESCRIPTION = "description";
    public static final String NOTE_TIME = "time";
    public static final String NOTE_COLOR = "color";
    public static final String NOTE_IMAGE = "image";

    /** note image **/
    public static final String TABLE_NOTE_IMAGE = "NoteImage";
    public static final String NOTE_IMAGE_ID = "id";
    public static final String NOTE_IMAGE_NAME = "image";

    public MySQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void QueryData(String sql) {

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }
/*
    public void INSERT_NOTE(String description, String time, String color, byte[] image) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Notes VALUES(null, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, description);
        statement.bindString(2, time);
        statement.bindString(3, color);
        statement.bindBlob(4, image);
        statement.executeInsert();

    }
    public void INSERT_PLAN() {


    }
    public void UPDATE_NOTE(String description, String time, String color, byte[] image, int id) {

        SQLiteDatabase database = getWritableDatabase();
        String update = "UPDATE Notes SET description = ?, time = ?, color = ?, image = ? WHERE id = '" + id +"'";
        SQLiteStatement statement =  database.compileStatement(update);

        statement.bindString(1, description);
        statement.bindString(2, time);
        statement.bindString(3, color);
        statement.bindBlob(4, image);

        statement.execute();
        database.close();

    }
    public void UPDATE_PLAN() {

    }
*/
    public Cursor getDataSQLite(String sql) {

        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryNote = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE +  "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE_DESCRIPTION + " VARCHAR, " + NOTE_TIME + " VARCHAR, " + NOTE_COLOR + " INTEGER, " + NOTE_IMAGE + " BLOB)";
        db.execSQL(queryNote);

        String queryNoteImage = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE_IMAGE + "(" + NOTE_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE_IMAGE_NAME + " BLOB)";
        db.execSQL(queryNoteImage);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
