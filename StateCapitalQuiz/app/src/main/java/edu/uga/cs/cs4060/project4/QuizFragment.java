package edu.uga.cs.cs4060.project4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


//Fragment loaded for each question framgnet
public class QuizFragment extends Fragment {

    RadioGroup radioGroup; // Radio group to hold answers
    TextView screenPrompt; // Textview for question prompt
    ProgressBar progress; //Progress bar to show progress to user
    Question question;
            String prompt;

    public static QuizFragment newInstance(int index ) {
        // this uses the default constructor (not defined in this class, but Java-supplied)
        QuizFragment fragment = new QuizFragment();
        // save the selected list index in the new fragment's Bundle data
        Bundle args = new Bundle();
        args.putInt( "index", index );
        fragment.setArguments( args );
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        int layoutid = R.layout.quiz_fragment;
        View view;

        if(getShownIndex() == 6){
            layoutid = R.layout.loading;
             view = inflater.inflate(layoutid, container, false);
            return  view;
        }

        view = inflater.inflate(layoutid, container, false);



        //Find and initalize views on screen
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        screenPrompt = (TextView) view.findViewById(R.id.screenPrompt);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);

        double percentComplete = ((double)getShownIndex() / (double)6) * 100;
        System.out.println(percentComplete + " ");
        progress.setProgress((int)percentComplete); // Sets progress bar animation to 0%

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton)getView().findViewById(selectedId);

                System.out.println(radioButton.getText());

//                When user clicks button, find the selected Radio Button index
                View rb = radioGroup.findViewById(selectedId);
                int index = radioGroup.indexOfChild(rb);
                System.out.println("SELECtED IDNEX: "  + index);
                ((quizActivity)getActivity()).quiz.answer(index);


            }
        });

//        Questions title = ((quizActivity)getActivity()).quiz[getQuestoin.atIndexindex];

        if (! ((quizActivity)getActivity()).quiz.isFinished()) { // If the user is still taking the quiz, load the question
            if(getShownIndex() >= 6){
                return view;
            }
            Question currentQuestion = ((quizActivity)getActivity()).quiz.questions.get(getShownIndex()); // Load the current question

            System.out.println(currentQuestion.toString());

            screenPrompt.setText(currentQuestion.prompt); // set the new p
            // rompt for the question

            char[] letters = {'A', 'B', 'C'}; //Used for prefix: Example, A) Atlanta, B)Athens
            for (int i = 0; i < currentQuestion.possible.size(); i++) {
                //loop over all the possible answers and add them to the radio button/ radio group
                ((RadioButton) (radioGroup.getChildAt(i))).setText(letters[i] + ") " + currentQuestion.possible.get(i));
            }// end of for loop
        } else { // User is done with the 6 question in this quiz now
            ((quizActivity)getActivity()).goToScore(); // Save the quiz to the database
            //Here we now switch to the ScoreActivity, we send over the score from the quiz, and a string of the correct answers/state/user answer
        }

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0 );
    }
}
