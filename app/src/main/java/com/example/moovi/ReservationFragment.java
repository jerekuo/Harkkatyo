package com.example.moovi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {


    Spinner hallSpinner;
    Spinner roomSpinner;
    Button searchButton;
    View view;
    Hall hall;
    Room room;
    DatePicker datePicker;

    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reservation, container, false);
        searchButton = view.findViewById(R.id.button4);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {
        this.hallSpinner();
    }


    public void hallSpinner(){
        HallSystem hallSystem = HallSystem.getInstance();
        hallSpinner = view.findViewById(R.id.spinner);
        final ArrayList<Hall> list = hallSystem.getHallList();

        ArrayAdapter<Hall> dataAdapter = new ArrayAdapter<Hall>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);
        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hall = (Hall) parent.getItemAtPosition(position);
                roomSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void roomSpinner(){

        roomSpinner = view.findViewById(R.id.spinner2);
        final ArrayList<Room> rlist = hall.getRoomList();


        ArrayAdapter<Room> dataAdapter = new ArrayAdapter<Room>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, rlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);


        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room = (Room) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showFreeTimes(View V){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        fragment = new freeTimesFragment();
        transaction.replace(R.id.fragmentView, fragment);
    }

    public ArrayList<String> getResObjectList(){
        Intent intent = new Intent();

        int y = datePicker.getYear();
        int m = datePicker.getMonth();
        int d = datePicker.getDayOfMonth();
        ArrayList<String> ResObjectList = new ArrayList<>();
        ResObjectList.add(String.format("%04d-%02d-%02d", y, m, d));
        ResObjectList.add(hall.hallName);
        ResObjectList.add(room.name);
        intent.putExtra("key", ResObjectList);

        return ResObjectList;
    }

}
