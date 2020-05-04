package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

//KESKENKESNKENKESKENKESKEN
    // free time spinneri kesken




public class EditReservationsFragment extends Fragment {
    View view;
    Reservation res;
    Spinner hallSpinner;
    Spinner roomSpinner;
    Spinner freeTimeSpinner;
    EditText editText;
    HallSystem hallSystem = HallSystem.getInstance();
    DatePicker datePicker;
    Hall hall;
    Room room;


    public EditReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_reservations, container, false);
        // Inflate the layout for this fragment
        res = HallSystem.getInstance().getChosenRes();
        editText = view.findViewById(R.id.editText2);


        return view;
    }

    public void onDelete(View V){
        Database.getInstance().deleteReservation();
    }

    public void onEdit(View V){
        String desc = editText.getText().toString();
        Reservation newRes = new Reservation(hall, room, desc, res.getResId(), 10, startTime, endTime, resDate);
        Database.getInstance().editReservation(newRes);

    }

    public void hallSpinner(){

        hallSpinner = view.findViewById(R.id.spinner4);
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
        ArrayList<Room> rlist = h.getRoomList();




        ArrayAdapter<Room> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, rlist);
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

    public void freeTimeSpinner(String date, String hall, String room) throws ParseException {
        ArrayList<String> timeList = new ArrayList<>();
        for (int i = 8 ; i < 21 ; i++){
            timeList.add(i+".00");
        }

        ArrayList<Reservation> list = hallSystem.getResList();

        for (Reservation r: list){
            //Mikäli varaus löytyy samalle päivälle, poistaa yllä tehdystä listasta kyseiset ajat
            if (hall.equalsIgnoreCase(r.hall.getHallName()) && room.equalsIgnoreCase(r.room.getName()) && date.equalsIgnoreCase(r.resDate)){   // Ei tietoa mätsääkö start timet daten kanssa?!?!?!?
                timeList.remove(r.startTime);
            }
        }

        freeTimeSpinner = view.findViewById(R.id.spinner6);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, timeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freeTimeSpinner.setAdapter(dataAdapter);
        freeTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                resTime = (String) parent.getItemAtPosition(position);
                System.out.println(resTime);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
