package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {


    Spinner hallSpinner;
    Spinner roomSpinner;
    Button calendarButton;
    Button searchButton;
    View view;
    Hall hall;

    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.hallSpinner();
        this.roomSpinner();




        view = inflater.inflate(R.layout.fragment_reservation, container, false);

        calendarButton = view.findViewById(R.id.button5);

        searchButton = view.findViewById(R.id.button4);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    public void hallSpinner(){
        HallSystem hallSystem = HallSystem.getInstance();
        hallSpinner = view.findViewById(R.id.spinner);
        final ArrayList<Hall> list = hallSystem.getHallList();
        //mkamfk

        ArrayAdapter<Hall> dataAdapter = new ArrayAdapter<Hall>.createFromResource(getActivity().getBaseContext(), R.); //(getContext(),android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);
        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hall = (Hall) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void roomSpinner(){

        roomSpinner = view.findViewById(R.id.spinner2);
        final ArrayList<Room> rlist = hall.getRoomList();

        ArrayAdapter<Room> dataAdapter = new ArrayAdapter<Room>(getContext(),android.R.layout.simple_spinner_dropdown_item, rlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);
        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
