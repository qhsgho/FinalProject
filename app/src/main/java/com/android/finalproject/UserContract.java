package com.android.finalproject;

import  android.provider.BaseColumns ;

public final class UserContract {
    public static final String DB_NAME="plans.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private UserContract() {}

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Plans";
        public static final String KEY_TITLE = "Title";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_MONTH = "Month";
        public static final String KEY_DATE = "Date";
        public static final String KEY_TIME_START = "Time_start";
        public static final String KEY_TIME_END = "Time_end";
        public static final String KEY_PLACE = "PLACE";
        public static final String KEY_MEMO = "Memo";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_YEAR + TEXT_TYPE + COMMA_SEP +
                KEY_MONTH + TEXT_TYPE + COMMA_SEP +
                KEY_DATE + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_START + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_END + TEXT_TYPE + COMMA_SEP +
                KEY_PLACE + TEXT_TYPE + COMMA_SEP +
                KEY_MEMO + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}