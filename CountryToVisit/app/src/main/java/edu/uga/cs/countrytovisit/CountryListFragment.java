package edu.uga.cs.countrytovisit;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CountryListFragment extends android.app.ListFragment {

    private String item;

    // Array of Android versions strings for the list fragment
    private String[] countryList = {
            "China",
            "New Zealand",
            "Japan",
            "South Africa",
            "Italy"
    };

    // indicate if this is a landscape mode with both fragments present or not
    int orientation;
    // list selection index
    int index = 0;

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        // create a new ArrayAdapter for the list
        setListAdapter( new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_activated_1, countryList ) );

        // set the twoFragmentsActivity variable to true iff we are in 2 fragment (landscape) view
        View detailsFrame = getActivity().findViewById( R.id.countryInfo);
        orientation = this.getResources().getConfiguration().orientation;
        // restore the saved list index selection (Android version no), if available
        if( savedInstanceState != null ) {
            // Restore last state for checked position.
            index = savedInstanceState.getInt("index", 0 );
        }

        // set the list mode as single choice and
        // if we are in 2 fragment (landscape) orientation, show the Android version information
        if( orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getListView().setChoiceMode( ListView.CHOICE_MODE_SINGLE );
            androidVersionInfo(index,getListView());
        }
        else {
            getListView().setChoiceMode( ListView.CHOICE_MODE_SINGLE );
            getListView().setItemChecked(index, true );
        }
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);
        // save the list index selection
        outState.putInt( "index", index);
    }

    @Override
    public void onListItemClick( ListView l, View v, int position, long id ) {
        // on a click on a list item, show the selected Android version info
        androidVersionInfo( position,l );
    }

    void androidVersionInfo( int index, ListView l ) {
        this.index = index;
        // set item name
        item = l.getItemAtPosition(index).toString();
        if( orientation == Configuration.ORIENTATION_LANDSCAPE ) {  // only in the 2 fragment (landscape) orientation

            getListView().setItemChecked( index, true );
            Intent intent = getActivity().getIntent();
            intent.putExtra("countryName", item);
            // get the placeholder element (FrameLayout) in a 2 fragment (landscape) orientation layout
            CountryInfoFragment details = CountryInfoFragment.newInstance( index );
            // replace the placeholder with the new fragment stance within a transaction
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace( R.id.countryInfo, details );

            // TRANSIT_FRAGMENT_FADE means that the fragment should fade in or fade out
            fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

            // commit the transaction, i.e. make the changes
            fragmentTransaction.commit();
        }
        else {
            // In a 1 fragment orientation (portrait), start a new activity using an Intent, as in the previous demo app
            Intent intent = new Intent();
            intent.setClass( getActivity(), CountryInfoActivity.class );
            intent.putExtra("countryName", item);
            startActivity( intent );
        }
    }

}
