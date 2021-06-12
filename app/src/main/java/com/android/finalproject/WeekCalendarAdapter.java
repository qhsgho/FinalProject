package com.android.finalproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class WeekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=20;
    public WeekCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    int year;
    int month;
    int date;
    int sundate;

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        date = cal.get(Calendar.DATE);

        // 이번주 일요일의 날짜
        sundate = date - (cal.get(Calendar.DAY_OF_WEEK) - 1);

        // cal에 이번주 일요일의 날짜 넣기
        cal.set(year, month-1, sundate);

        // cal에 포지션에 의해 변한 날짜 연산
        cal.add(Calendar.DATE, (position-10) * 7);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        sundate = cal.get(Calendar.DATE);

        return WeekCalendarFragment.newInstance(year, month, sundate);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }

}
