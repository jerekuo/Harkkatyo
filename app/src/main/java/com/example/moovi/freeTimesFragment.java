package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class freeTimesFragment extends Fragment {

    HallSystem hallSystem = HallSystem.getInstance();


    View view;
    TextView textView;
    String resTime;
    EditText editText;
    Spinner freeTimeSpinner;
    Hall newHall;
    Room newRoom;
    Fragment fragment;

    String d;
    String hall;
    String room;
    Database db = Database.getInstance();

    public freeTimesFragment() {
        // Required empty public constructor
    }
    //PUSHI


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_free_times, container, false);

        Button button = view.findViewById(R.id.button5);
        freeTimeSpinner = view.findViewById(R.id.spinner3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeReservation();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String list = bundle.getString("key");
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        String[] tokens = list.split("[,]");
        d = tokens[0];
        hall = tokens[1];
        room = tokens[2];


        try {
            freeTimeSpinner(d,hall,room);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        textView = view.findViewById(R.id.textView);
        textView.setText("*****Showing free times*****\nHALL: "+hall+"\nROOM: "+room);
    }


    //Populates spinner with available times, Method first populates arraylist with all available times and then fetches reservations from db and picks already reserved
    //times out of the list.
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

        freeTimeSpinner = view.findViewById(R.id.spinner3);
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



    //adds the new reservation to database
    public void makeReservation() throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("HH.mm");
        SimpleDateFormat format2 = new SimpleDateFormat("H.mm");
        editText = view.findViewById(R.id.editText);
        String desc = editText.getText().toString();
        String startTime = resTime; // String pitäs saada oikeesee muotoo eli HH.mm eikä sitä litaniaa mis on 1970
        long helpTime = format2.parse(resTime).getTime();
        long helpEndTime = helpTime + 1000 * 60 * 60;
        String endTime = Long.toString(helpEndTime);


        for (Hall h: hallSystem.getHallList()){
            if (h.hallName.equalsIgnoreCase(hall)){
                newHall = h;
                for (Room r : newHall.getRoomList()){
                    if (r.name.equalsIgnoreCase(room)){
                        newRoom = r;
                    }
                }
            }
        }

        Reservation res = new Reservation(newHall, newRoom,  desc, 123, 100, startTime, endTime , d);

        if (checkIfFree(res) == true) {
            hallSystem.addToResList(res);
            Toast.makeText(getContext(),"Reservation done", Toast.LENGTH_SHORT).show();
            fragment = new ReservationFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentView, fragment);
            transaction.commit();

        } else{
            System.out.println("Varauksen teko ei onnistunut, löytyi päällekkäisyys.");
        }

    }

    //Method checks if the chosen time is free.
    public Boolean checkIfFree(Reservation reservation){
        HallSystem hallSystem = HallSystem.getInstance();
        Reservation res = reservation;
        String availability = "True";

        for (Reservation r : hallSystem.getResList()) {
            if (r.room == res.room && r.startTime.compareTo(res.startTime)  <= 0 && r.endTime.compareTo(res.endTime) >= 0){
                //Listasta löytyy varaus samalle ajalle
                // date vertailu löytyy netistä helposti
                availability = "False";
                break;
            }
        }

        //Return true if the chosen time is free.
        return !availability.equalsIgnoreCase("False");

    }


}
