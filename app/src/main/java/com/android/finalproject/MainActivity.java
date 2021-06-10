package com.android.finalproject;


import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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
                    intent.putExtra("time_start", selecteddate.getTime());
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

    public void findDateFromDb (String year, String month, String date) {

        Cursor cursor = mDbHelper.getDateBySQL(year, month, date);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.plan_item, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.plan_title}, 0);

        ListView lv = (ListView) v.findViewById(R.id.plan_listview);

        lv.setAdapter(adapter);

        lv.setFocusable(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                v = adapterView;

                if(lv.getChildCount() == 1) {
                    Intent intent = new Intent(getApplication(), DetailedScheduleActivity.class);
                    intent.putExtra("id", ((Cursor) adapter.getItem(i)).getString(0));
                    intent.putExtra("title", ((Cursor) adapter.getItem(i)).getString(1));
                    intent.putExtra("year", ((Cursor) adapter.getItem(i)).getString(2));
                    intent.putExtra("month", ((Cursor) adapter.getItem(i)).getString(3));
                    intent.putExtra("date", ((Cursor) adapter.getItem(i)).getString(4));
                    intent.putExtra("time_start", ((Cursor) adapter.getItem(i)).getString(5));
                    intent.putExtra("time_end", ((Cursor) adapter.getItem(i)).getString(6));
                    intent.putExtra("place", ((Cursor) adapter.getItem(i)).getString(7));
                    intent.putExtra("memo", ((Cursor) adapter.getItem(i)).getString(8));
                    startActivity(intent); //액티비티 열기
                }

                else {
                    final List<String> ListItems = new ArrayList<>();

                    for(int j = 0; j < lv.getChildCount(); j++) {
                        View v = lv.getChildAt(j);
                        TextView textView = v.findViewById(R.id.plan_title);
                        ListItems.add((String) textView.getText());
                    }

                    final CharSequence[] oItems = ListItems.toArray(new String[ ListItems.size() ]);

                    AlertDialog.Builder selectDialog = new AlertDialog.Builder(context);

                    selectDialog.setTitle(year+"."+month+"."+date);
                    selectDialog.setItems(oItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getApplication(), DetailedScheduleActivity.class);
                            intent.putExtra("id", ((Cursor) adapter.getItem(which)).getString(0));
                            intent.putExtra("title", ((Cursor) adapter.getItem(which)).getString(1));
                            intent.putExtra("year", ((Cursor) adapter.getItem(which)).getString(2));
                            intent.putExtra("month", ((Cursor) adapter.getItem(which)).getString(3));
                            intent.putExtra("date", ((Cursor) adapter.getItem(which)).getString(4));
                            intent.putExtra("time_start", ((Cursor) adapter.getItem(which)).getString(5));
                            intent.putExtra("time_end", ((Cursor) adapter.getItem(which)).getString(6));
                            intent.putExtra("place", ((Cursor) adapter.getItem(which)).getString(7));
                            intent.putExtra("memo", ((Cursor) adapter.getItem(which)).getString(8));
                            startActivity(intent); //액티비티 열기

                        }
                    });

                    selectDialog.show();

                }

            }
        });
    }


    public boolean hasDate (String year, String month, String date) {
        return mDbHelper.hasDate(year, month, date);
    }

    public boolean hasMonth (String year, String month) {
        return mDbHelper.hasMonth(year, month);
    }

    public int dateCount (String year, String month, String date) {
        return mDbHelper.dateCount(year, month, date);
    }



}

