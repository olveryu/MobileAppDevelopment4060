package edu.uga.cs.countrytovisit;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class CountryInfoActivity extends BasicActivity {


    @Override
    protected void onCreate( Bundle savedInstanceState ) {


        super.onCreate( savedInstanceState );

        // if this call is not for a change in the configuration, do nothing and return
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            finish();
            return;
        }

        // if not in 2 fragment mode ie in portrait
        if( savedInstanceState == null ) {

            CountryInfoFragment countryInfoFragment = new CountryInfoFragment();

            // pass on any saved data, i.e., Android version no (list index)
            countryInfoFragment.setArguments( getIntent().getExtras() );

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // add the fragment within a transaction
            getFragmentManager().beginTransaction().add( android.R.id.content, countryInfoFragment).commit();
        }
    }

}
