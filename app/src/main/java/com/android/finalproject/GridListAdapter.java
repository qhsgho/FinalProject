package com.android.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<DateItem> items = new ArrayList<DateItem>();
    Context context;

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
            calView = inflater.inflate(R.layout.cal_item, parent, false);
        }

        // 그리드뷰 크기 결정
        ViewGroup.LayoutParams lp = calView.getLayoutParams();
        lp.height = parent.getHeight()/6;
        lp.width = parent.getWidth()/7;

        calView.setLayoutParams(lp);

        TextView dateText = calView.findViewById(R.id.item_text);
        dateText.setText(dateitem.getDate());

        return calView;
    }
}
