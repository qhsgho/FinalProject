package com.android.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeekTimeGridListAdapter extends BaseAdapter {
    ArrayList<DateItem> items = new ArrayList<DateItem>();
    Context context;

    int year;
    int month;
    int date;
    int lastday;
    boolean isdayofweek[];
    boolean isdate;

    WeekDateItem weekDateItem[];



    public WeekTimeGridListAdapter(int year, int month, int sundate, int lastday, boolean[] isdayofweek, boolean isdate, WeekDateItem[] weekDateItems) {
        this.year = year;
        this.month = month;
        this.date = sundate;
        this.lastday = lastday;
        this.isdayofweek = isdayofweek;
        this.isdate = isdate;

        this.weekDateItem = weekDateItems;
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

        if(calView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            calView = inflater.inflate(R.layout.cal_item_week_time, parent, false);
        }

        TextView dateText = calView.findViewById(R.id.item_text_week_time);
        dateText.setText(dateitem.getDate());

        if(isdate) {
            int daypos;
            daypos = position % 7;

            if(isdayofweek[daypos]) {
                int stime = position / 7;

                if(((MainActivity) MainActivity.context).hasTime(Integer.toString(weekDateItem[daypos].getYear()), Integer.toString(weekDateItem[daypos].getMonth()),
                        Integer.toString(weekDateItem[daypos].getDate()), Integer.toString(stime))) {

                    ((MainActivity) MainActivity.context).v = calView;
                    ((MainActivity) MainActivity.context).findTimeFromDb(Integer.toString(weekDateItem[daypos].getYear()), Integer.toString(weekDateItem[daypos].getMonth()),
                            Integer.toString(weekDateItem[daypos].getDate()), Integer.toString(stime), Integer.toString(stime+1));

                }
            }
        }

        return calView;
    }
}
