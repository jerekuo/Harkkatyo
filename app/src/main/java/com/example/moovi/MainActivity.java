package com.example.moovi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText password;
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password = (EditText) findViewById(R.id.password);
        userName = (EditText) findViewById(R.id.userName);
    }

    //takes user to register window
    public void toRegister(View v) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);

    }
    //takes user to login window
    public void toLogin(View v) {
        //TODO tähän tapa tarkistaa käyttäjän nimi sekä salasana tietokannasta, jotta voi kirjautua sisään
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);

    }

}
