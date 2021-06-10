package com.android.finalproject;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    SelectedDate selecteddate = new SelectedDate("", "", "", "");

    public static Context context;
    private DBHelper mDbHelper;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DBHelper(this);
        context = this;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_container, new MonthViewFragment());
        fragmentTransaction.commit();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selecteddate.getDate() != "") {
                    Intent intent = new Intent(getApplication(), DetailedScheduleActivity.class);
                    intent.putExtra("year", selecteddate.getYear());
                    intent.putExtra("month", selecteddate.getMonth());
                    intent.putExtra("date", selecteddate.getDate());
                    intent.putExtra("time", selecteddate.getTime());
                    startActivity(intent); //액티비티 열기
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month_view:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new MonthViewFragment());
                fragmentTransaction.commit();

                return true;
            case R.id.week_view:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new WeekViewFragment());
                fragmentTransaction.commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 앱바 핸들링 메서드
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void viewAllToListView () {

        Cursor cursor = mDbHelper.getAllUsersBySQL();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.plan_item, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.plan_title}, 0);

        ListView lv = (ListView) v.findViewById(R.id.plan_listview);
        lv.setAdapter(adapter);

        lv.setFocusable(false);
        lv.setClickable(false);
        lv.setEnabled(false);
    }

    public void findDateFromDb (String year, String month, String date) {

        Cursor cursor = mDbHelper.getDateBySQL(year, month, date);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.plan_item, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.plan_title}, 0);

        ListView lv = (ListView) v.findViewById(R.id.plan_listview);
        lv.setAdapter(adapter);


        lv.setFocusable(false);
//        lv.setClickable(false);
//        lv.setEnabled(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();

                Toast.makeText(view.getContext(),((Cursor) adapter.getItem(i)).getString(1),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), DetailedScheduleActivity.class);
                intent.putExtra("year", ((Cursor) adapter.getItem(i)).getString(2));
                intent.putExtra("month", ((Cursor) adapter.getItem(i)).getString(3));
                intent.putExtra("date", ((Cursor) adapter.getItem(i)).getString(4));
                intent.putExtra("time", ((Cursor) adapter.getItem(i)).getString(5));
                startActivity(intent); //액티비티 열기
            }
        });

    }

    /*
    public Cursor findDateToListView (String year, String month) {

        Cursor cursor = mDbHelper.getDateBySQL(year, month);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.plan_item, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.plan_title}, 0);

        ListView lv = (ListView) v.findViewById(R.id.plan_listview);
        lv.setAdapter(adapter);

        lv.setFocusable(false);
        lv.setClickable(false);
        lv.setEnabled(false);

    }

     */

    public void viewDateTimeListView (Cursor cursor, View v) {

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.plan_item, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.plan_title}, 0);

        ListView lv = (ListView) v.findViewById(R.id.plan_listview);
        lv.setAdapter(adapter);

        /*
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();

                mId.setText(((Cursor) adapter.getItem(i)).getString(0));
                mName.setText(((Cursor) adapter.getItem(i)).getString(1));
                mPhone.setText(((Cursor) adapter.getItem(i)).getString(2));
            }
        });

         */
    }

    public boolean hasDate (String year, String month, String date) {
        boolean hasdate = mDbHelper.hasDate(year, month, date);

        return hasdate;
    }

    public boolean hasMonth (String year, String month) {
        boolean hasmonth = mDbHelper.hasMonth(year, month);

        return hasmonth;
    }

    public int dateCount (String year, String month, String date) {
        int dateCount = mDbHelper.dateCount(year, month, date);

        return dateCount;
    }

}