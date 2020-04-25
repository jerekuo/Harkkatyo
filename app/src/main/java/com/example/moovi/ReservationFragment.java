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
import android.widget.DatePicker;
import android.widget.Spinner;



import java.util.ArrayList;


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
    Database database = Database.getInstance();
    HallSystem hallSystem = HallSystem.getInstance();

    //


    public ReservationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reservation1, container, false);



        Button xbutton = view.findViewById(R.id.button4);
        xbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFreeTimes();
            }
        });

        searchButton = view.findViewById(R.id.button4);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);




        // Inflate the layout for this fragment
        return view;

    }



    public void onViewCreated (View view, Bundle savedInstanceState) {
        hallSpinner();

    }


    public void hallSpinner(){
        System.out.println("HALLSPINNERI AJETAAAAAN #%%¤#%¤#%¤#%¤#%¤%¤#¤%#¤");

        hallSpinner = view.findViewById(R.id.spinner);
        final ArrayList<Hall> list = hallSystem.getHallList();
        for (Hall h: list){
            System.out.println(h.hallName);
        }


        ArrayAdapter<Hall> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(dataAdapter);

        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hall = (Hall) parent.getItemAtPosition(position);
                System.out.println(hall);
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
