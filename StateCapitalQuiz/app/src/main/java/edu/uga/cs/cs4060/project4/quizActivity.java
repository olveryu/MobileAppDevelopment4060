package edu.uga.cs.cs4060.project4;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.*;
import android.content.Intent;
import java.util.*;
import java.util.Collections;
import java.text.*;

//Activity for each quiz. This uses fragments and a viewpager to swipe between questions
public class quizActivity extends AppCompatActivity  {

    public final String TAG = "quizActivity"; //Log debug tag
    Button submitButton; // Button for submit on activity
    RadioGroup radioGroup; // Radio group to hold answers
    TextView screenPrompt; // Textview for question prompt
    ProgressBar progress; //Progress bar to show progress to user

    int numSelected = 0;

    Quiz quiz; //THE POJ Quiz object to hold data about current quiz
    final int NUM_QUESTIONS = 6; // Number of questions in the quiz
    ArrayList<Question> questions = new ArrayList<Question>(); // Arraylist of questions, these will be used to generate the quiz
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        new loadTask().execute("run");

        vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        //Add back button to tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                System.out.println("page selected " + position);
                quiz.currentQuestionIndex = position;

                if(position == 6 ){ // If they are done, go to score
                    boolean canMoveOn = true;
                    for(Question q : quiz.questions){
                        if(q.hasAttempted == false){
                            canMoveOn = false;
                        }
                    }
                    if(canMoveOn){
                        goToScore();
                    }else{
                        Toast.makeText( (quizActivity.this), "Please answer all questiosn!", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    //Go to score once they are finished with quiz!
    public void goToScore() {
        double numCorrect = 0;
        double totalQuestions = 6;
        //calculate the score now!
        for(Question ques : quiz.questions){
            if(ques.answeredCorrect){
                numCorrect++;
            }
        }
        double score = numCorrect / totalQuestions;
        quiz.percentCorrect = score;
        new saveTask().execute("run"); // Save the quiz to the database
        //Start an intent to show the ScoreActivity page
            Intent intent = new Intent(this, ScoreActivity.class);

            String quizSummary = ""; // String to pass of the summary of the quiz
            //Loop over question and get state, if user got correct, and the right answer
            for (Question q : questions) {
                quizSummary += q.state + ": " + q.correctAnswer + " ~ " + q.userAnswer;
                if (q.answeredCorrect) {
                    quizSummary += " | Correct\n";
                } else {
                    quizSummary += " | Wrong\n";
                }
            }
            intent.putExtra("score", quiz.percentCorrect);
            intent.putExtra("answers", quizSummary);
            startActivity(intent);
        }

    private class loadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            DatabaseCommand db = new DatabaseCommand();
            db.open(quizActivity.this);
            //read the state table
            ArrayList<String[]> stateSet = db.readTable("state");

            //Shuffle the stateSet
            Collections.shuffle(stateSet);


            //Get the first 6 random questions and add to the ArrayList<Questions>
            for (int i = 0; i < NUM_QUESTIONS; i++) {
                String row[] = stateSet.get(i); // get the String[] row entry
                String id = row[0]; // Get the primay key of the state used
                String state = row[1]; //Get the state for the question
                String prompt = "What is the captial of " + state; // Make the prompt
                String possible[] = {row[3], row[4]}; // Other possible captials in the state
                String correct = row[2]; //The captial of the state
                Question q = new Question(prompt, state, correct, possible, id); // Create the question
                questions.add(q); // Add to our question array list, used to generate the quiz
            }
            db.close();
            quiz = new Quiz(questions); // Load the quiz
            return "Executed";
        }
    }

    private class saveTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            DatabaseCommand db = new DatabaseCommand();
            db.open(quizActivity.this);
            //get the last result key, and add 1 to see what the new added result row ID will be,
            //This is used to insert the value into the questions table
            int lastResultId = Integer.parseInt(db.getLastPrimaryKey("result"));
            lastResultId += 1;
            ContentValues values = new ContentValues();

            //Get date for the result row insert
            Date currentTime = Calendar.getInstance().getTime();
            String d = DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime);

            //Format to 2 decimal places
            double score = quiz.percentCorrect * 10000;
            score = (int) (score) / 100;

            //Insert the date we took, score we got, and the results!
            values.put(MyDatabaseHelper.RESULT_COLUMN_DATE, d);
            values.put(MyDatabaseHelper.RESULT_COLUMN_SCORE, score);
            db.db.insert(MyDatabaseHelper.TABLE_RESULT, null, values);


            //Insert all questions from this quiz into the qusetion table
            for (Question q : questions) {
                //insert the questoins here with the result id!
                ContentValues insertValues = new ContentValues();
                insertValues.put(MyDatabaseHelper.QUESTIONS_COLUMN_RESULT_ID, lastResultId);
                insertValues.put(MyDatabaseHelper.QUESTIONS_COLUMN_STATE_ID, Integer.parseInt(q.id));
                insertValues.put(MyDatabaseHelper.QUESTIONS_COLUMN_ANSWER, q.userAnswer);
                insertValues.put(MyDatabaseHelper.QUESTIONS_COLUMN_CORRECTNESS, (q.answeredCorrect) ? "true" : "false");
                db.db.insert(MyDatabaseHelper.TABLE_QUESTIONS, null, insertValues);

            }
            db.close();
            return "Executed";
        }
    }
}
