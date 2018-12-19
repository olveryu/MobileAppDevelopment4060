package edu.uga.cs.cs4060.project4;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v7.widget.*;
import java.util.*;
import android.util.Log;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;



import android.widget.*;


//Entry point to application, shows splash screen with buttons to start quiz and see past results
public class splashActivity extends AppCompatActivity {
    Button startQuizButton; //Used to start a quiz
    Button resultButton; // Used to view results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //---------------------------------
//        deleteDatabase("MyDatabase.db");
        //---------------------------------
        new splashTask().execute("run");

        //Get the startQuizButton and add intent to the button
        startQuizButton = (Button)findViewById(R.id.startQuiz);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), quizActivity.class);
                startActivity(intent);
            }
        });

        //Get the result button and add click listener to load result activity
        resultButton = (Button)findViewById(R.id.resultsButtonSplash);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsActivity.class);
                startActivity(intent);
            }
        });

    }

    private class splashTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            DatabaseCommand db = new DatabaseCommand();
            db.open(splashActivity.this);
            db.importStateSCV(splashActivity.this, db);
            db.close();
            return "Executed";
        }
    }
}
