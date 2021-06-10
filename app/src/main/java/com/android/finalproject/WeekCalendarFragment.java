package com.android.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.android.finalproject.WeekCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private int mParam3;

    int year;
    int month;
    int sundate;
    TextView datepreviousView = null;
    TextView gridpreviousView = null;

    public WeekCalendarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static com.android.finalproject.WeekCalendarFragment newInstance(int year, int month, int sundate) {
        com.android.finalproject.WeekCalendarFragment fragment = new com.android.finalproject.WeekCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, sundate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            year = mParam1;
            month = mParam2;
            sundate = mParam3;
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
            ((MainActivity) activity).selecteddate.setTime("");

        }
    }

    // 벡그라운드로 가면 초기화
    @Override
    public void onPause() {
        super.onPause();

        if(datepreviousView != null) {
            datepreviousView.setBackgroundColor(Color.WHITE);
            datepreviousView = null;
        }

        if(gridpreviousView != null) {
            gridpreviousView.setBackgroundColor(Color.WHITE);
            gridpreviousView = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View calView = inflater.inflate(R.layout.fragment_week_calendar, container, false);

        // 요일 그리드 뷰
        GridView gridview = (GridView) calView.findViewById(R.id.calendar_gridview_week);
        WeekGridListAdapter adapt = new WeekGridListAdapter();

        // 시간부분 그리드 뷰
        ExpandableHeightGridView gridview_timeline = (ExpandableHeightGridView) calView.findViewById(R.id.calendar_gridview_week_timeline);
        gridview_timeline.setExpanded(true);
        WeekTimelineGridListAdapter adapt_timeline = new WeekTimelineGridListAdapter();

        // 격자부분 그리드 뷰
        ExpandableHeightGridView gridview_timegrid = (ExpandableHeightGridView) calView.findViewById(R.id.calendar_gridview_week_timegrid);
        gridview_timegrid.setExpanded(true);
        WeekTimeGridListAdapter adapt_timegrid = new WeekTimeGridListAdapter();


        int weeksundate = sundate;
        int restbox = 1;
        int lastday = finddaynum(year, month);

        for(int i = 1; i < 8; i++) {

            if(weeksundate > lastday) {
                adapt.addItem(new DateItem(""+restbox));
                restbox++;
            }
            else {
                adapt.addItem(new DateItem(""+weeksundate));
            }
            weeksundate++;
        }

        for(int i = 0; i < 168; i++) {
            adapt_timegrid.addItem(new DateItem(" "));
        }


        for(int i = 0; i < 24; i++) {
            adapt_timeline.addItem(new DateItem(""+i));
        }

        // 요일 부분
        gridview.setAdapter(adapt);

        // 시간 부분
        gridview_timeline.setAdapter(adapt_timeline);
        adapt_timeline.notifyDataSetChanged();

        // 격자 부분
        gridview_timegrid.setAdapter(adapt_timegrid);
        adapt_timegrid.notifyDataSetChanged();

        // 클릭 이벤트 처리 달력
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView)view.findViewById(R.id.item_text_week);

                if(datepreviousView != null) {
                    datepreviousView.setBackgroundColor(Color.WHITE);
                }

                textView.setBackgroundColor(Color.CYAN);
                datepreviousView = textView;
            }
        });



        // 클릭 이벤트 처리 (timeline)
        gridview_timegrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View daychildView = null;
            int dayposition = 0;
            int time;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView)view.findViewById(R.id.item_text_week_time);

                // 포지션 토스트메시지
                Toast.makeText(view.getContext(),"position : "+position,Toast.LENGTH_SHORT).show();

                // 그리드 위치에 따른 요일 위치 찾기
                if ((position + 1) % 7 == 0) {
                    dayposition = 6;
                }
                else
                    dayposition = ((position + 1) % 7) - 1;

                // 요일 그리드 뷰에서 선택된 타임라인의 요일 찾기
                daychildView = gridview.getChildAt(dayposition);
                TextView dayTextview = daychildView.findViewById(R.id.item_text_week);

                if(gridpreviousView != null) {
                    gridpreviousView.setBackgroundColor(Color.WHITE);
                    datepreviousView.setBackgroundColor(Color.WHITE);
                }
                else if(datepreviousView != null) {
                    datepreviousView.setBackgroundColor(Color.WHITE);
                    datepreviousView = null;
                }

                textView.setBackgroundColor(Color.CYAN);
                dayTextview.setBackgroundColor(Color.CYAN);
                gridpreviousView = textView;
                datepreviousView = dayTextview;


                time = position / 7;
                // 달력의 날짜
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    ((MainActivity) activity).selecteddate.setYear(Integer.toString(year));
                    ((MainActivity) activity).selecteddate.setMonth(Integer.toString(month));
                    ((MainActivity) activity).selecteddate.setDate(((String) dayTextview.getText()));
                    ((MainActivity) activity).selecteddate.setTime(Integer.toString(time));
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