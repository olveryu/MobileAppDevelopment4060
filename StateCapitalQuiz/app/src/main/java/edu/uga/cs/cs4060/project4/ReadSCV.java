package edu.uga.cs.cs4060.project4;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


//Reads data from CSV and imports into database if there is no datbase
//Done async
public class ReadSCV {
    final String TAG = "ReadSCV";
    private ArrayList<String[]> data = new ArrayList<String[]>();

    /**
     * constructor to read SCV file
     * @param context: the current context
     * @param id: the R.id.something (id of the file)
     */
    public ReadSCV(Context context, int id){
        try {
            InputStream in_s = context.getResources().openRawResource(id);
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextLine;
            // start reading the scv files, remember the first row is the attribute
            while ((nextLine = reader.readNext()) != null) {
                data.add(nextLine);
            }
        }catch (Exception e) {
            Log.e( TAG, e.toString() );
        }
    }

    /**
     * get arrayList of the scv file data
     * @return: an arrayList of string[] of the data
     */
    public ArrayList<String[]> getData() {
        return data;
    }
}
