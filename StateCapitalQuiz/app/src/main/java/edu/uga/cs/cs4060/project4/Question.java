package edu.uga.cs.cs4060.project4;

import java.util.*;


//POJ to store question for a fragment
public class Question{
  public String prompt;
  public String state;
  public String correctAnswer;
  public String userAnswer;
  public String[] possibleAnswers;
  public boolean hasAttempted = false;
  public boolean answeredCorrect = false;
  public ArrayList<String> possible = new ArrayList<String>();
  public int correctIndex;
  public String id;

  public Question(String prompt, String state, String correctAnswer, String[] possibleAnswers, String id){
    this.possibleAnswers = possibleAnswers;
    this.correctAnswer = correctAnswer;
    this.state = state;
    this.prompt = prompt;
    System.out.println("Question created!");
    generateQuizList();
    this.id = id;
  }

  //Shuffles the answers
  private void generateQuizList(){
    for(int i = 0; i < possibleAnswers.length; i++){
      possible.add(possibleAnswers[i]);
    }
    possible.add(correctAnswer);
    Collections.shuffle(possible);
    for(int i = 0; i < possible.size(); i++){
      if(possible.get(i).equals(correctAnswer)){
        correctIndex = i;
      }
    }
  }

  //Checks if answer is correct
  public boolean checkAnswer(int index){
    //error
    return index == correctIndex;
  }

  public String toString(){
    String toReturn = prompt + " " + correctAnswer + "* ";
    for(String s : possibleAnswers){
      toReturn += s + " ";
    }
    return toReturn;
  }


}
