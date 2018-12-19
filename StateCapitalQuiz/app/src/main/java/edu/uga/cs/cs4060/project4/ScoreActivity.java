package edu.uga.cs.cs4060.project4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import java.util.*;

//Shows the result and score from the quiz the user took after the quiz
public class ScoreActivity extends AppCompatActivity {

    TextView scoreText;
    TextView questionsText;

    Button anotherButton;
    Button resultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Grab the score/quiz summary  from the intent
        double score =  getIntent().getDoubleExtra("score",0);
        String quizSummary = getIntent().getStringExtra("answers");


        //Initalize the textViews from the activity to set their text
        scoreText = (TextView)findViewById(R.id.scoreTextView);
        questionsText = (TextView   )findViewById(R.id.questionsTextView);

        //Round score to 2 decimal places and set the text
        score *= 10000;
        score = (int)(score) / 100;
        scoreText.setText(score + " %");


        //Get the quizButton and add click listener to take another quiz
        anotherButton = (Button)findViewById(R.id.anotherButton);
        anotherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), quizActivity.class);
                startActivity(intent);
            }
        });

        //set the text of the quiz summary / questions in the quiz
        questionsText.setText(quizSummary);

        // Get the resultButton and add click listener to show results in ResultsActivity
        resultsButton = (Button)findViewById(R.id.resultsButton);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsActivity.class);
                startActivity(intent);
            }

        });
    }
}
