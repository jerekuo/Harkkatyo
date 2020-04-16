package com.example.moovi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText editLast;
    EditText editEmail;
    EditText editFirst;
    Spinner spinnerDay;
    Spinner spinnerMonth;
    Spinner spinnerYear;
    TextView textError;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFirst = findViewById(R.id.editFirst);
        editLast = findViewById(R.id.editLast);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textError = findViewById(R.id.textError);
        this.daySpinner();
        this.monthSpinner();
        this.yearSpinner();


    }

    public void registerAccount(View v) {

    }

    public void daySpinner(){
            spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
            final ArrayList<Integer> days = new ArrayList<>();
            for(int i = 1; i<32; i++) {
                days.add(i);
            }
            ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, days);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDay.setAdapter(dataAdapter);
            spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


    }

    public void monthSpinner(){
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        final ArrayList<Integer> days = new ArrayList<>();
        for(int i = 1; i<13; i++) {
            days.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, days);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(dataAdapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void yearSpinner(){
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        final ArrayList<Integer> days = new ArrayList<>();
        for(int i = 1920; i<2021; i++) {
            days.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, days);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(dataAdapter);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
