package com.android.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Testdb extends AppCompatActivity {

    private DBHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb);

        mDbHelper = new DBHelper(this);

        viewAllToListView();
    }

    public void viewAllToListView () {

        Cursor cursor = mDbHelper.getAllUsersBySQL();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.test_item, cursor, new String[]{
                UserContract.Users._ID,
                UserContract.Users.KEY_TITLE,
                UserContract.Users.KEY_DATE,
                UserContract.Users.KEY_TIME_START,
                UserContract.Users.KEY_TIME_END,
                UserContract.Users.KEY_PLACE,
                UserContract.Users.KEY_MEMO},
                new int[]{R.id.idid,R.id.ttitle,R.id.date,R.id.tstart,
                        R.id.tend,R.id.place,R.id.memo}, 0);

        ListView lv = (ListView) findViewById(R.id.test_listview);
        lv.setAdapter(adapter);

        lv.setFocusable(false);
        lv.setClickable(false);
        lv.setFocusableInTouchMode(false);

    }

}