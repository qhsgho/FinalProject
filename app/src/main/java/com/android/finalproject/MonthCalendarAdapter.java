package com.android.finalproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=20;
    public MonthCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    int year;
    int month;

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;

        findSwipeday(position - 10);
        return MonthCalendarFragment.newInstance(year, month);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }

    // position에 따른 년도와 월 계산
    public void findSwipeday(int swipe) {
        int result;
        result = month + swipe;

        if(result > 0) {
            if((result) > 12 && (result) % 12 != 0) {
                year = year + ((result) / 12);
                month = (result) % 12;
            }
            else if ((result) > 12 && (result) % 12 == 0) {
                year = year + ((result) / 12) - 1;
                month = 12;
            }
            else {
                month = result;
            }
        }

        else {
            if(result > -12) {
                year = year - 1;
                month = 12 + result;
            }
            else {
                year = year - 1 + (result / 12);
                month = 12 + (result % 12);
            }
        }
    }


}
