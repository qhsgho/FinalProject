package com.android.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.android.finalproject.WeekViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeekViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeekViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.android.finalproject.WeekViewFragment newInstance(String param1, String param2) {
        com.android.finalproject.WeekViewFragment fragment = new com.android.finalproject.WeekViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false);


        ViewPager2 vpPager = rootView.findViewById(R.id.vpPager_week);
        FragmentStateAdapter adapter = new WeekCalendarAdapter(this);
        vpPager.setAdapter(adapter);

        vpPager.setCurrentItem(10);
        vpPager.setOffscreenPageLimit(20);

        return rootView;
    }
}