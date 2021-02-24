package com.miguel.app.lessontre.model;

import android.provider.BaseColumns;

public class StudentDB {

    public static class Data implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COL_NAME = "name";
        public static final String COL_LASTNAME = "lastname";
        public static final String COL_BIRTHDATE = "birthdate";
        public static final String COL_GENDER = "gender";
    }

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Data.TABLE_NAME + " (" +
                    Data._ID + " INTEGER PRIMARY KEY," +
                    Data.COL_NAME + " TEXT, " +
                    Data.COL_LASTNAME + " TEXT," +
                    Data.COL_BIRTHDATE + " TEXT," +
                    Data.COL_GENDER + " TEXT" +
                    ")";
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Data.TABLE_NAME;
}
