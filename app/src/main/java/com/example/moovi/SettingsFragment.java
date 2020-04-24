package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    View view;
    EditText editLastName;
    EditText editFirstName;
    TextView textError;
    EditText editPassword1, editPassword2;
    DatePicker datePicker;
    TextView infoHeader;
    TextView infoText;
    TextView updateText;
    Button updateInfo;



    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        textError = view.findViewById(R.id.textError);
        editPassword1 = view.findViewById(R.id.editPassword1);
        editPassword2 = view.findViewById(R.id.editPassword2);
        datePicker = view.findViewById(R.id.datePicker);
        infoHeader = view.findViewById(R.id.infoHeader);
        infoText = view.findViewById(R.id.infoText);
        updateText = view.findViewById(R.id.updateText);

        updateInfo = view.findViewById(R.id.updateInfo);

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editFirstName.getText().toString();
                String last = editLastName.getText().toString();
                String pass1 = editPassword1.getText().toString();
                String pass2 = editPassword2.getText().toString();
                User user = new User(pass1,null,name,last,null);
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }



}
