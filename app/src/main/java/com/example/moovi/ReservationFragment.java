package com.example.moovi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
    Fragment fragment;
    //


    public ReservationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reservation, container, false);
        Button xbutton = view.findViewById(R.id.button4);
        xbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFreeTimes();
            }
        });

        searchButton = view.findViewById(R.id.button4);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        hallSpinner();


        // Inflate the layout for this fragment
        return view;

    }

    public void onViewCreated (View view, Bundle savedInstanceState) {

    }


    public void hallSpinner(){
        HallSystem hallSystem = HallSystem.getInstance();
        hallSpinner = view.findViewById(R.id.spinner);
        final ArrayList<Hall> list = hallSystem.getHallList();


        ArrayAdapter<Hall> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);

        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hall = (Hall) parent.getItemAtPosition(position);
                roomSpinner(hall);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void roomSpinner(Hall h){

        roomSpinner = view.findViewById(R.id.spinner2);
        final ArrayList<Room> rlist = h.getRoomList();


        ArrayAdapter<Room> dataAdapter = new ArrayAdapter<Room>(getContext(),android.R.layout.simple_spinner_dropdown_item, rlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(dataAdapter);


        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room = (Room) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showFreeTimes(){
        Bundle bundle = new Bundle();

        int y = datePicker.getYear();
        int m = datePicker.getMonth();
        int d = datePicker.getDayOfMonth();

        String chosendaate = String.format("%04d-%02d-%02d", y, m, d);

        String info = chosendaate +","+ hall.hallName +","+ room.name;
        bundle.putString("key", info);
        System.out.println(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragment = new freeTimesFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragmentView, fragment);
        transaction.commit();
    }



}
