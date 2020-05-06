package com.example.moovi;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.namespace.QName;

//CLASS FOR WRITING CSV BACKUP
public class Backup {

    private static Backup instance = new Backup();

    private Backup() {
        //NULL CONSTRUCTOR
    }

    public static Backup getInstance() {return instance;}


    //Method backups user data to csv file
    //Format: "email,Firstname,Lastname,Age,userId,password
    //Path to file /data/data/com.example.moovi/files/userBackup.csv
    public void writeUserBackup(User u, Context context) {

        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("userBackup.csv", Context.MODE_PRIVATE));
            ows.append(u.getEmail() + "," + u.getFirstName() + "," + u.getLastName() + "," +
                    u.getBirthdate() + ',' + u.getAddress() + "," + u.getPhoneNumber() + "\n");
            ows.close();

        } catch (IOException e) {
            Log.e("IOEXCEPTION", "tuli virhe!!!");
        }
    }

    public void writeHallBackup(Hall h, Context context) {

        try {
            OutputStreamWriter ows1 = new OutputStreamWriter(context.openFileOutput("hallBackup.csv", Context.MODE_PRIVATE));
            ows1.append(h.getHallId() + "," + h.getHallLocation() + "," + h.getHallName() + "," + h.getRoomList() + "\n");
            ows1.close();

        } catch (IOException e) {
            Log.e("IOEXCEPTION", "virhe tuli!");
        }
    }
}
