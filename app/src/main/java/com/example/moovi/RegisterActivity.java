package com.example.moovi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    EditText editLast;
    EditText editEmail;
    EditText editFirst;
    TextView textError;
    EditText editPassword;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFirst = findViewById(R.id.editFirst);
        editLast = findViewById(R.id.editLast);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textError = findViewById(R.id.textError);
        datePicker = findViewById(R.id.datePicker);
        Database db = Database.getInstance();
        User testi = new User("salainensana", "jere@hotmail.com", "Jack", "Death", null);
        db.addUser(testi);


    }

    public void registerAccount(View v) {

    }




}
