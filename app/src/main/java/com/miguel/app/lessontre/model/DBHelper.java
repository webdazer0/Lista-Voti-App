package com.miguel.app.lessontre.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "school.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StudentDB.SQL_CREATE_TABLE);
        db.execSQL(VoteDB.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StudentDB.SQL_DROP_TABLE);
        db.execSQL(VoteDB.SQL_DROP_TABLE);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(StudentDB.Data.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor selectById(int selectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(StudentDB.Data.TABLE_NAME, null, "" + StudentDB.Data._ID + "=?", new String[]{String.valueOf(selectId)}, null, null, null);
        return cursor;
    }

    public long delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String customQuery = "DELETE FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=?";

        long result = db.delete(StudentDB.Data.TABLE_NAME, "" + StudentDB.Data._ID + "=?", new String[]{String.valueOf(id)});
        Log.i("MITO_TAG", "SQL DELETE | result: " + result);

        return result;
    }

    public long insert(String name, String lastname, String birthdate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("lastname", lastname);
        values.put("birthdate", birthdate);

        return db.insert(StudentDB.Data.TABLE_NAME, null, values);
    }

    public long insertVote(int id, String vote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", vote);
        values.put("student_id", String.valueOf(id));
        return db.insert(VoteDB.Data.TABLE_NAME, null, values);
    }

    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(StudentDB.SQL_DROP_TABLE);
        db.execSQL(VoteDB.SQL_DROP_TABLE);
        onCreate(db);
    }
}
