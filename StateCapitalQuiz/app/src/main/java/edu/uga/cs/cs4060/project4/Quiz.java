package edu.uga.cs.cs4060.project4;

import java.util.*;
import java.lang.*;


//POJ to store a quiz
public class Quiz{
  ArrayList<Question> questions = new ArrayList<Question>();
  int currentQuestionIndex = 0;
  double percentCorrect;
  int numberOfQuestions;
  int questionsCorrect;
  boolean isFinished = false;


  //Consturctor for a quiz, take in arraylist of questoins
  public Quiz(ArrayList<Question> questions){
    //take in lots of questions, get 6 random ones and add to our questions list
    this.numberOfQuestions = questions.size();
    this.percentCorrect = 0;
    this.questionsCorrect = 0;
     this.questions = questions;

    //show first question now!
    System.out.println("# " + currentQuestionIndex + " " + questions.get(currentQuestionIndex).toString());

  }

  public boolean isFinished(){
    return  isFinished;
  }

  //Load the next questions, also checks if we are done with the quiz
  public boolean next(){
    if(currentQuestionIndex + 1 >= questions.size()){
      isFinished = true;
      return false;
    }else{
      currentQuestionIndex++;
      System.out.println("# " + currentQuestionIndex + " " +questions.get(currentQuestionIndex).toString());
      return true;
    }
  }

  public Question getCurrentQuestion(){
    return questions.get(currentQuestionIndex);
  }

  //Allows user to submit their answer, and marks if they got it correct or wrong
  public boolean answer(int index){
    //go to current questions, and see if my answer is correct
    Question cur = questions.get(currentQuestionIndex);
    cur.hasAttempted = true;
    cur.userAnswer = cur.possible.get(index);

    boolean correct = false;
    if(cur.checkAnswer(index)){
      //we are right!
      correct = true;
      cur.answeredCorrect = true;

      System.out.println("Correct answer!");
    }else{
      cur.answeredCorrect = false;

      System.out.println("WRONG!");
    }
    System.out.println(percentCorrect + "%");


    return correct;
  }

  public String toString(){
    String toReturn = "Number of questions: " + numberOfQuestions + "\nPercent correct: " + percentCorrect;
    for(Question q: questions){
      toReturn += "\n" + q.toString() ;
    }
    return toReturn;
  }


}
