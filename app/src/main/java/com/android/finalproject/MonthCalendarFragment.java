package com.android.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.android.finalproject.MonthCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // 년도와 달
    private int mParam1;
    private int mParam2;

    int year;
    int month;
    LinearLayout previousView;

    public MonthCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MonthCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.android.finalproject.MonthCalendarFragment newInstance(int year, int month) {
        com.android.finalproject.MonthCalendarFragment fragment = new com.android.finalproject.MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            year = mParam1;
            month = mParam2;

        }
    }

    // 적절한 생명주기에서 타이틀 변경
    @Override
    public void onResume() {
        super.onResume();

        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle(year+"년 "+month+"월");
            ((MainActivity) activity).selecteddate.setYear(Integer.toString(year));
            ((MainActivity) activity).selecteddate.setMonth(Integer.toString(month));
            ((MainActivity) activity).selecteddate.setDate("");
            ((MainActivity) activity).selecteddate.setTime("8");
            ((MainActivity) activity).selecteddate.setType("month");

        }
    }

    // 벡그라운드로 가면 초기화
    @Override
    public void onPause() {
        super.onPause();

        if(previousView != null) {
            previousView.setBackgroundColor(Color.WHITE);
            previousView = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View calView = inflater.inflate(R.layout.fragment_month_calendar, container, false);

        // 달력에서 날짜 받기
        Calendar cal = Calendar.getInstance();

        // 달의 첫번째 요일 알기 위함
        cal.set(year, month-1, 1);

        // 데이터 있는지 확인
        FragmentActivity activity = getActivity();
        boolean isdate = ((MainActivity) activity).hasMonth(Integer.toString(year), Integer.toString(month));

        GridView gridview = (GridView) calView.findViewById(R.id.calendar_gridview);
        GridListAdapter adapt = new GridListAdapter(year, month, cal.get(Calendar.DAY_OF_WEEK) - 1, finddaynum(year, month), isdate);

        // 빈 요일만큼 달의 앞부분 공백 추가
        for(int i = 1; i < cal.get(Calendar.DAY_OF_WEEK); i++) {
            adapt.addItem(new DateItem(""));
        }
        // 날짜 넣기
        for(int i = 0; i < finddaynum(year, month); i++) {
            adapt.addItem(new DateItem(Integer.toString(i+1)));
        }

        // 7*6 맞추기 위해 공백 추가
        int v = 42 - adapt.getCount();
        for(int i = 0; i < v; i++) {
            adapt.addItem(new DateItem(""));
        }

        gridview.setAdapter(adapt);

        // 클릭 이벤트 처리
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.item_text_background);
                TextView textView = (TextView) view.findViewById(R.id.item_text);

                if((String) textView.getText() != "") {
                    if(previousView != null) {
                        previousView.setBackgroundColor(Color.WHITE);
                    }
                    linearLayout.setBackgroundColor(Color.CYAN);
                    previousView = linearLayout;

                    // 달력의 날짜
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        ((MainActivity) activity).selecteddate.setAll(Integer.toString(year), Integer.toString(month), (String) textView.getText(), "8", "month");
                        ((MainActivity) activity).v = parent.getChildAt(position);
                    }
                }
            }
        });

        return calView;
    }

    // 달의 총 요일을 찾는 함수
    public int finddaynum(int year, int month) {
        int day_num = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day_num = 31;
                break;
            case 2:
                if((year%4==0 && year%100 != 0) || year%400 == 0)
                    day_num = 29;
                else
                    day_num = 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day_num = 30;
                break;
        }
        return day_num;
    }

}