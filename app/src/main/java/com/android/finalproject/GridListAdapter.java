package com.android.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<DateItem> items = new ArrayList<DateItem>();
    Context context;
    int year;
    int month;
    int date_start;
    int date_num;

    boolean isdate;

    private DBHelper mDbHelper;

    public GridListAdapter(int year, int month, int date_start, int date_num, boolean isdate) {
        this.year = year;
        this.month = month;
        this.date_start = date_start;
        this.date_num = date_num;
        this.isdate = isdate;
    }

    public void addItem(DateItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View calView, ViewGroup parent) {
        context = parent.getContext();
        DateItem dateitem = items.get(position);

        mDbHelper = new DBHelper(context);

        if(calView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            calView = inflater.inflate(R.layout.cal_item, parent, false);
        }

        // 그리드뷰 크기 결정
        ViewGroup.LayoutParams lp = calView.getLayoutParams();
        lp.height = parent.getHeight()/6;
        lp.width = parent.getWidth()/7;

        calView.setLayoutParams(lp);

        TextView dateText = calView.findViewById(R.id.item_text);
        dateText.setText(dateitem.getDate());


/*
        for(int i = date_start; i < date_num + date_start; i++) {

            if(position == i) {
                ((MainActivity) MainActivity.context).v = calView;
                ((MainActivity) MainActivity.context).viewAllToListView();
            }
        }

 */

        if(isdate) {
            if(date_start <= position && position < date_start + date_num) {
                if(((MainActivity) MainActivity.context).hasDate(Integer.toString(year), Integer.toString(month), Integer.toString(position - date_start + 1))) {
                    ((MainActivity) MainActivity.context).v = calView;
                    ((MainActivity) MainActivity.context).findDateFromDb(Integer.toString(year), Integer.toString(month), Integer.toString(position - date_start + 1));
                }
            }
        }

//        if(date_start <= position && position < date_start + date_num) {
 //           if(((MainActivity) MainActivity.context).hasDate(Integer.toString(year), Integer.toString(month), Integer.toString(position))) {
//            ((MainActivity) MainActivity.context).v = calView;
//            ((MainActivity) MainActivity.context).findDateFromDb(Integer.toString(year), Integer.toString(month), Integer.toString(position));
//            ((MainActivity) MainActivity.context).viewAllToListView();
  //          }

//            viewAllToListView(calView, context);
  //      }


/*
        if(position == 1) {
            ((MainActivity) MainActivity.context).v = calView;
            ((MainActivity) MainActivity.context).viewAllToListView();
        }

 */

        return calView;
    }


    public void viewAllToListView (View calView, Context context) {

        Cursor cursor = mDbHelper.getAllUsersBySQL();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                R.layout.plan_item, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.plan_title}, 0);

        ListView lv = (ListView) calView.findViewById(R.id.plan_listview);
        lv.setAdapter(adapter);

        lv.setFocusable(false);
        lv.setClickable(false);
        lv.setEnabled(false);
    }

}
