package com.miguel.app.lessontre.model;

import android.provider.BaseColumns;

public class VoteDB {

    public static class Data implements BaseColumns {
        public static final String TABLE_NAME = "vote"; // Tabella "vote"
        public static final String COL_VOTE = "score";  // Colonna score prenderà il voto del studente
        public static final String COL_FK_ID = "student_id";
    }

    public static final String SQL_CREATE_TABLE =
            "CREATE DATABASE IF NOT EXISTS " + VoteDB.Data.TABLE_NAME + " (" +
                    VoteDB.Data._ID + " INTEGER PRIMARY KEY," +
                    VoteDB.Data.COL_VOTE + " TEXT, " +
                    VoteDB.Data.COL_FK_ID + " INTEGER," +
                    "FOREIGN KEY(" + VoteDB.Data.COL_FK_ID + ") REFERENCES " + StudentDB.Data.TABLE_NAME + "(" + StudentDB.Data._ID + ")" +
                    ")";
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + VoteDB.Data.TABLE_NAME;
}

