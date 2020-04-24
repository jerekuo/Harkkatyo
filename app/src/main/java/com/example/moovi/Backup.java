package com.example.moovi;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//CLASS FOR WRITING CSV BACKUP
public class Backup {
    Context context;

    private static Backup instance = new Backup();

    private Backup() {
    }

    public static Backup getInstance() {return instance;}

    public void writeToCSV(String s, Context c) {

        context = context.getApplicationContext();
        System.out.println(context);
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("Backup.csv", Context.MODE_PRIVATE));
            ows.append(s+"\n");
            ows.close();

        } catch (IOException e) {
            Log.e("IOEXCEPTION", "tuli virhe!!!");
        }

    }


    //Method backups user data to csv file
    //Format: "email,Firstname,Lastname,Age,userId,password
    public void writeUserBackup(User u, Context context) {

        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("userBackup.csv", Context.MODE_PRIVATE));
            ows.append(u.getEmail()+","+u.getFirstName()+","+u.getLastName()+","+
                    u.getAge()+','+u.getUserId()+","+u.getPassword()+"\n");
            ows.close();

        } catch (IOException e) {
            Log.e("IOEXCEPTION", "tuli virhe!!!");
        }
    }

}
