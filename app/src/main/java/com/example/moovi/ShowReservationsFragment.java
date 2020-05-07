package com.example.moovi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowReservationsFragment extends Fragment {
    View view;
    CalendarView calendarView;
    TextView calendarText;
    ArrayList<Reservation> reslist = new ArrayList<>();
    Button button;

    public ShowReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar_, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        button = view.findViewById(R.id.button6);

        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        // METHOD FOR FETCHING ALL RESERVATIONS ON THIS DAY
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                reslist = Database.getInstance().getReservationsByDate(dayOfMonth, month + 1, year);


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printReservations(reslist);
            }
        });
    }

    public void printReservations(ArrayList<Reservation> list) {
        calendarText = view.findViewById(R.id.textView3);
        String s = "";

        Collections.sort(list, new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                Float fo1 = Float.parseFloat(o1.getStartTime());
                Float fo2 = Float.parseFloat(o2.getStartTime());

                return fo1.compareTo(fo2);
            }


        });
        for (Reservation r : list) {
            s += r.getHall() + " " + r.getRoom() + " " + r.getStartTime() + "\n";
            s += r.getDescription() + "\n\n";
            System.out.println(s);
        }

        if (s != "") {
            calendarText.setText(s);
        } else {
            calendarText.setText("No reservations on this day!");
        }
    }
}