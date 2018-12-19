package edu.uga.cs.cs4060.project4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.*;
import java.util.*;


//Displays the results of previous quizzes shown on a listview
public class ResultsActivity extends AppCompatActivity {

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list = (ListView)findViewById(R.id.listView);
        DatabaseCommand db = new DatabaseCommand();
        db.open(this);



        ArrayList<String[]> results = db.readTableQuery("SELECT * FROM result ORDER BY _id DESC");
        String[] values = new String[results.size()];

        int index = 0;
        for(String[] row : results){
            String toInsert ="";

            toInsert += "Score: " + row[2];
            toInsert += "% | Date: " + row[1];


            values[index] = toInsert;
            index++;

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        list.setAdapter(adapter);


    }
}
