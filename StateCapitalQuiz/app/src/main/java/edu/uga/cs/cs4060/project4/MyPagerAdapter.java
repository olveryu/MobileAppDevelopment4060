package edu.uga.cs.cs4060.project4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//Used to create the fragments and pages for each quiz
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 7;

    private static int index = 0;


    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }



    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        //get the question to load
        System.out.println("CREWATING WITH: " + position);

        return QuizFragment.newInstance(position);
    }

    public static int getIndex(){
        return  index;
    }


    private int currentPage;



}

