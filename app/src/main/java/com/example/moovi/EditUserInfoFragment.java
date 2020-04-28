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

import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserInfoFragment extends Fragment {
    View view;
    TextView updateText;
    EditText editPhone, editAddress, editLastName, editFirstName, userEmail;
    DatePicker datePicker;
    Button updateInfo, deleteUser;

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
        deleteUser = view.findViewById(R.id.deleteUser);
        userEmail = view.findViewById(R.id.userEmail);

        // Inflate the layout for this fragment
        return view;
    }
}
