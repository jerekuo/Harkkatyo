package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar_Fragment extends Fragment {
    View view;

    public Calendar_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar_, container, false);
        CalendarView calendarView = view.findViewById(R.id.calendarView);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_, container, false);


    }
}
