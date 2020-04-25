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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    View view;
    TextView infoHeader, infoText, updateText;
    EditText editPassword1, editPassword2, editLastName, editFirstName;
    DatePicker datePicker;
    Button updateInfo;
    FirebaseUser user = HallSystem.getInstance().getUser();


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editPassword1 = view.findViewById(R.id.editPassword1);
        editPassword2 = view.findViewById(R.id.editPassword2);
        datePicker = view.findViewById(R.id.datePicker);
        infoHeader = view.findViewById(R.id.infoHeader);
        infoText = view.findViewById(R.id.infoText);
        updateText = view.findViewById(R.id.updateText);
        updateInfo = view.findViewById(R.id.updateInfo);

        this.updateSettings(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void updateSettings(View view){
        final String email = user.getEmail();
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editFirstName.getText().toString();
                String last = editLastName.getText().toString();
                String pass1 = editPassword1.getText().toString();
                String pass2 = editPassword2.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
                Date d = new Date(day, month, year);
                String strDate = dateFormatter.format(d);
                if(pass1.isEmpty() != true && pass2.isEmpty() != true && email.isEmpty() != true && pass1.equals(pass2)
                        && name.isEmpty() != true && last.isEmpty() != true){
                    User user = new User(pass1,email,name,last,strDate);
                    Database.getInstance().addUser(user);
                    Backup.getInstance().writeUserBackup(user, getActivity().getApplicationContext());
                    Toast.makeText(getActivity(),"Information updated.",Toast.LENGTH_SHORT).show();
                }else if(pass1.isEmpty() || pass2.isEmpty() || pass1 != pass2 || email.isEmpty() || name.isEmpty() || last.isEmpty()){
                    Toast.makeText(getActivity(), "Passwords do not match or text field is empty.", Toast.LENGTH_SHORT).show();
                }else{
                    //Do nothing
                }
            }
        });

    }
}
