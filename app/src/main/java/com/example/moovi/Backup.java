package com.example.moovi;


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

    public void writeToCSV(String s) {
        context = context.getApplicationContext();
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("Backup.csv", Context.MODE_PRIVATE));
            ows.append(s);
            ows.close();

        } catch (IOException e) {
            Log.e("IOEXCEPTION", "tuli virhe!!!");
        }




    }
}
