package com.android.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="CalendarPlan";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public void insertUserBySQL(String title, String year, String month, String date, String time_start, String time_end, String place, String memo, String place_point) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_TITLE,
                    UserContract.Users.KEY_YEAR,
                    UserContract.Users.KEY_MONTH,
                    UserContract.Users.KEY_DATE,
                    UserContract.Users.KEY_TIME_START,
                    UserContract.Users.KEY_TIME_END,
                    UserContract.Users.KEY_PLACE,
                    UserContract.Users.KEY_MEMO,
                    UserContract.Users.KEY_PLACE_POINT,
                    title,
                    year,
                    month,
                    date,
                    time_start,
                    time_end,
                    place,
                    memo,
                    place_point);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getDateBySQL(String year, String month, String date) {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME + " Where " + UserContract.Users.KEY_YEAR + "= '"+ year +"' AND " + UserContract.Users.KEY_MONTH + "= '"+ month +
                "' AND " + UserContract.Users.KEY_DATE + "= '"+ date +"'";

        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getTimeDateBySQL(String year, String month, String date, String time_start, String time_end) {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME + " Where " + UserContract.Users.KEY_YEAR + "= '"+ year +"' AND " + UserContract.Users.KEY_MONTH + "= '"+ month +
                "' AND " + UserContract.Users.KEY_DATE + "= '"+ date + "' AND " + UserContract.Users.KEY_TIME_START + "= '"+ time_start +"' AND " + UserContract.Users.KEY_TIME_END +
                "= '"+ time_end +"'";

        return getReadableDatabase().rawQuery(sql,null);
    }

    public boolean hasDate(String year, String month, String date) {

        String sql = "SELECT * FROM " + UserContract.Users.TABLE_NAME + " WHERE " + UserContract.Users.KEY_YEAR + "= '"+ year +"' AND " + UserContract.Users.KEY_MONTH + "= '"+ month +
                "' AND " + UserContract.Users.KEY_DATE + "= '"+ date +"'";

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        cursor.close();

        return true;
    }

    public boolean hasTime(String year, String month, String date, String time_start) {

        String sql = "SELECT * FROM " + UserContract.Users.TABLE_NAME + " WHERE " + UserContract.Users.KEY_YEAR + "= '"+ year +"' AND " + UserContract.Users.KEY_MONTH + "= '"+ month +
                "' AND " + UserContract.Users.KEY_DATE + "= '"+ date +  "' AND " + UserContract.Users.KEY_TIME_START + "= '"+ time_start + "'";

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        cursor.close();

        return true;
    }

    public int dateCount(String year, String month, String date) {

        String sql = "SELECT * FROM " + UserContract.Users.TABLE_NAME + " WHERE " + UserContract.Users.KEY_YEAR + "= '"+ year +"' AND " + UserContract.Users.KEY_MONTH + "= '"+ month +
                "' AND " + UserContract.Users.KEY_DATE + "= '"+ date +"'";

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        return cursor.getCount();
    }

    public boolean hasMonth(String year, String month) {

        String sql = "SELECT * FROM " + UserContract.Users.TABLE_NAME + " WHERE " + UserContract.Users.KEY_YEAR + "= '"+ year +"' AND " + UserContract.Users.KEY_MONTH + "= '"+ month + "'";

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        cursor.close();

        return true;
    }


    public void deleteUserBySQL(String _id) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    _id);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

    public void updateUserBySQL(String _id, String title, String year, String month, String date, String time_start, String time_end, String place, String memo, String place_point) {
        try {
            String sql = String.format (
                    "UPDATE  %s SET %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s' WHERE %s = %s",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users.KEY_TITLE, title,
                    UserContract.Users.KEY_YEAR, year,
                    UserContract.Users.KEY_MONTH, month,
                    UserContract.Users.KEY_DATE, date,
                    UserContract.Users.KEY_TIME_START, time_start,
                    UserContract.Users.KEY_TIME_END, time_end,
                    UserContract.Users.KEY_PLACE, place,
                    UserContract.Users.KEY_MEMO, memo,
                    UserContract.Users.KEY_PLACE_POINT, place_point,
                    UserContract.Users._ID, _id) ;

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in updating recodes");
        }
    }

    public long insertUserByMethod(String title) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_TITLE, title);

        return db.insert(UserContract.Users.TABLE_NAME,null,values);
    }

    public Cursor getAllUsersByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(UserContract.Users.TABLE_NAME,null,null,null,null,null,null);
    }

    public long deleteUserByMethod(String _id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = UserContract.Users._ID +" = ?";
        String[] whereArgs ={_id};
        return db.delete(UserContract.Users.TABLE_NAME, whereClause, whereArgs);
    }

    public long updateUserByMethod(String _id, String title) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_TITLE, title);

        String whereClause = UserContract.Users._ID +" = ?";
        String[] whereArgs ={_id};

        return db.update(UserContract.Users.TABLE_NAME, values, whereClause, whereArgs);
    }

}
