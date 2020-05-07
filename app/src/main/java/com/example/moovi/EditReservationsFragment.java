package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */





public class EditReservationsFragment extends Fragment {
    View view;
    Reservation res;
    Spinner hallSpinner;
    Spinner roomSpinner;
    Spinner freeTimeSpinner;
    Button refresh;
    Button edit;
    Button delete;
    EditText editText;
    HallSystem hallSystem = HallSystem.getInstance();
    DatePicker datePicker;
    Hall hall;
    Room room;
    String resTime;
    String day;
    FirebaseUser user;
    Hall newHall;
    Room newRoom;


    public EditReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_reservations, container, false);
        // Inflate the layout for this fragment
        user = HallSystem.getInstance().getUser();
        res = HallSystem.getInstance().getChosenRes();
        editText = view.findViewById(R.id.editText2);
        refresh = view.findViewById(R.id.button9);
        delete = view.findViewById(R.id.button8);
        edit = view.findViewById(R.id.button7);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    refresh();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onEdit();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {
        datePicker = view.findViewById(R.id.datePicker);
        hallSpinner();
        String[] tokens = res.resDate.split("\\-",-1);
        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[2]);
        datePicker.updateDate(year, month, day);

    }



    public void onDelete(){
        if (user.getEmail().equalsIgnoreCase("admin@gmail.com")){
            Database.getInstance().adminDeleteReservation();
        } else {
            Database.getInstance().deleteReservation();
        }

        getActivity().onBackPressed();
    }

    public void onEdit() throws ParseException {
        SimpleDateFormat format2 = new SimpleDateFormat("H.mm");

        String desc = editText.getText().toString();

        String startTime = resTime; // String pitäs saada oikeesee muotoo eli HH.mm eikä sitä litaniaa mis on 1970
        long helpTime = format2.parse(resTime).getTime();
        long helpEndTime = helpTime + 1000 * 60 * 60;
        String endTime = Long.toString(helpEndTime);


        for (Hall h: hallSystem.getHallList()){
            if (h.hallName.equalsIgnoreCase(hall.getHallName())){
                newHall = h;
                for (Room r : newHall.getRoomList()){
                    if (r.name.equalsIgnoreCase(room.getName())){
                        newRoom = r;
                    }
                }
            }
        }


        Reservation newRes = new Reservation(hall, room, desc, res.getResId(), 10, startTime, endTime, day);
        if (user.getEmail().equalsIgnoreCase("admin@gmail.com")){
            Database.getInstance().adminEditReservation(newRes);
        } else {
            Database.getInstance().editReservation(newRes);
        }
        getActivity().onBackPressed();

    }

    public void refresh() throws ParseException {
        datePicker = view.findViewById(R.id.datePicker);
        int y = datePicker.getYear();
        int m = datePicker.getMonth() + 1; // +1 because datepicker starts from month 0
        int d = datePicker.getDayOfMonth();
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd");
        String chosendaate = String.format("%04d-%02d-%02d", y, m, d);
        day = chosendaate;

        try {
            freeTimeSpinner(day,hall.getHallName(),room.getName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        edit.setVisibility(View.VISIBLE);
    }

    public void hallSpinner(){

        hallSpinner = view.findViewById(R.id.spinner4);
        final ArrayList<Hall> list = hallSystem.getHallList();



        ArrayAdapter<Hall> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);


        if(res.getHall().getHallName().equalsIgnoreCase("Slahen halli")) {
            hallSpinner.setSelection(1);
        } else if (res.getHall().getHallName().equalsIgnoreCase("urkki")) {
            hallSpinner.setSelection(2);
        }

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
        roomSpinner = view.findViewById(R.id.spinner5);
        ArrayList<Room> rlist = h.getRoomList();





        ArrayAdapter<Room> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, rlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(dataAdapter);


        if (res.getRoom().getName().equalsIgnoreCase("koripallo halli") || res.getRoom().getName().equalsIgnoreCase("squash")) {
            roomSpinner.setSelection(1);
        }

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
            //Checks if there is reservations for the same day, and if there are it deletes their times from the available times in timelist.
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

/*
TODO
Yläteksti menee "yläpalkin" kanssa päällekkäin, pitäisi korjata.
 Kannattaa harkita scrollViewistä luopumista koska turha.
 Ympäri koodia on suomenkielisiä printtejä ja kommentteja, pitäisi poistaa/ muuttaa lontooksi.
 Jos keksii tolle hardkoodatulle setSelection jutulle paremman tyylin, do dat */
