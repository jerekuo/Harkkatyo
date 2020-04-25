package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class freeTimesFragment extends Fragment {

    HallSystem hallSystem = HallSystem.getInstance();

    View view;
    TextView textView;
    String resTime;
    EditText editText;
    Hall newHall;
    Room newRoom;

    Date d;
    String hall;
    String room;

    public freeTimesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_free_times, container, false);

        Button button = view.findViewById(R.id.button5);

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
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-mm-dd");
        String[] tokens = list.split("[,]");


        textView = view.findViewById(R.id.textView);
        textView.setText("*****Näytetään vapaat ajat*****\nHALLI: "+hall+"\nHUONE: "+room);
    }



    public void makeReservation() throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("HH.mm");
        SimpleDateFormat format2 = new SimpleDateFormat("H.mm");
        editText = view.findViewById(R.id.editText);
        String desc = editText.toString();
        Date startTime = format2.parse(resTime);
        Date endTime = format2.parse(resTime);   //TODO varauksen lopetusaika pitäs laittaa kuntoon.
        HallSystem hallSystem = HallSystem.getInstance();

        for (Hall h: hallSystem.getHallList()){
            if (h.hallName.equalsIgnoreCase(hall)){
                newHall = h;
                for (Room r : hallSystem.getRoomList()){
                    if (r.name.equalsIgnoreCase(room)){
                        newRoom = r;
                    }
                }
            }
        }

        Reservation res = new Reservation(newHall, newRoom,  desc, 123, 100, startTime, endTime );

        if (checkIfFree(res) == true) {
            hallSystem.addToResList(res);
            System.out.println(res.getDescription()+"\n");
            System.out.println(res.getHall()+"\n");
            System.out.println(res.getRoom()+"\n");
            System.out.println(res.getStartTime()+"\n");
            System.out.println(res.getEndTime()+"\n");
            Toast.makeText(getContext(),"Reservation done", Toast.LENGTH_SHORT).show();

        } else{
            System.out.println("Varauksen teko ei onnistunut, löytyi päällekkäisyys.");
        }

    }


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

        if (availability.equalsIgnoreCase("False")){  // Metodi palauttaa truen jos listavertailun jälkeen ei ole löytynyt päällekkäisyyttä
            return false;                                          // Palauttaa falsen jos lötyy päällekkäisyyttä
        } else{
            return true;
        }

    }

}
