package com.example.moovi;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserInfoFragment extends Fragment {
    View view;
    TextView updateText;
    EditText editPhone, editAddress, editLastName, editFirstName, userEmail;
    DatePicker datePicker;
    Button updateInfo, getUserInfo;
    User editUser;

    public EditUserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_user_info, container, false);
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editPhone = view.findViewById(R.id.editPhone);
        editAddress = view.findViewById(R.id.editAddress);
        datePicker = view.findViewById(R.id.datePicker);
        updateText = view.findViewById(R.id.updateText);
        updateInfo = view.findViewById(R.id.updateInfo);
        getUserInfo = view.findViewById(R.id.getUserInfo);

        getUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    //gets the user from database so we can fill the current info to text fields.
    public void getUser() {
        userEmail = view.findViewById(R.id.userEmail);
        String email = userEmail.getText().toString();
        Database.getInstance().getUserFromDBemail(email);
        editUser = HallSystem.getInstance().getEditUser();
        if (editUser != null) {
            setInfo();
        }
    }

    //method parses info from user we got from db and sets it to text fields.
    public void setInfo() {
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editPhone = view.findViewById(R.id.editPhone);
        editAddress = view.findViewById(R.id.editAddress);
        datePicker = view.findViewById(R.id.datePicker);

        editFirstName.setText(editUser.getFirstName());
        editLastName.setText(editUser.getLastName());
        editAddress.setText(editUser.getAddress());
        editPhone.setText(editUser.getPhoneNumber());
        String date = editUser.getBirthdate();
        String[] values = date.split("\\.", -1);
        System.out.println("Arvot: "+values[0]);
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        datePicker.updateDate(year,month,day);
    }

    //Updates modified information to database.
    public void updateUserInfo(){
        String email = userEmail.getText().toString();
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
            Database.getInstance().editUser(user);
            Backup.getInstance().writeUserBackup(user, getActivity().getApplicationContext());
            Toast.makeText(getActivity(),"Information updated.",Toast.LENGTH_SHORT).show();

        }else if(address.isEmpty() || phone.isEmpty() || email.isEmpty() || name.isEmpty() || last.isEmpty()){
            Toast.makeText(getActivity(), "Please insert into all fields.", Toast.LENGTH_SHORT).show();
        }else{
            //Do nothing
        }
    }
}




