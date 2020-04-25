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
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    View view;
    TextView infoText, updateText;
    EditText editPhone, editAddress, editLastName, editFirstName;
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
        editPhone = view.findViewById(R.id.editPhone);
        editAddress = view.findViewById(R.id.editAddress);
        datePicker = view.findViewById(R.id.datePicker);
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
                String address = editAddress.getText().toString();
                String phone = editPhone.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                String strDate = (day+"."+month+"."+year);
                if(address.isEmpty() != true && phone.isEmpty() != true && email.isEmpty() != true
                        && name.isEmpty() != true && last.isEmpty() != true){
                    User user = new User(email,name,last,strDate,address,phone);
                    Database.getInstance().addUser(user);
                    Backup.getInstance().writeUserBackup(user, getActivity().getApplicationContext());
                    Toast.makeText(getActivity(),"Information updated.",Toast.LENGTH_SHORT).show();
                    infoText.setText("Name: "+name+" "+last+"\n"+"Birthdate: "+strDate+"\n"+"Email: "+email.toString()+"\n"+
                            "Number: "+phone+"\n"+"Address: "+address);
                }else if(address.isEmpty() || phone.isEmpty() || email.isEmpty() || name.isEmpty() || last.isEmpty()){
                    Toast.makeText(getActivity(), "Please insert into all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    //Do nothing
                }
            }
        });

    }
}
