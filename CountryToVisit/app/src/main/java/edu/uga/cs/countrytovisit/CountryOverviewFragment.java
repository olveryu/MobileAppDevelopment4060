package edu.uga.cs.countrytovisit;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


public class CountryOverviewFragment extends Fragment {
    ImageView flag;
    TextView overviewTitle;
    TextView overviewText;
    String item;
    Button informationButton;

    public static CountryOverviewFragment newInstance(int index ) {
        // this uses the default constructor (not defined in this class, but Java-supplied)
        CountryOverviewFragment fragment = new CountryOverviewFragment();
        // save the selected list index in the new fragment's Bundle data
        // the CountryInfoFragment needs to know the country to display
        Bundle args = new Bundle();
        args.putInt( "index", index );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.activity_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        flag = getView().findViewById(R.id.Flag);
        Log.d("haha", String.valueOf(flag));
        overviewTitle = getView().findViewById(R.id.OverviewTitle);
        overviewText = getView().findViewById(R.id.OverviewText);
        informationButton = getView().findViewById(R.id.Information);

        // set button listener
        informationButton.setOnClickListener(new ButtonClickListener());

        // get select item from main
        Intent intent = getActivity().getIntent();
        item = intent.getStringExtra( "countryName" );

        // get flag id by name
        String flagName = item.replace(" ", "_").toLowerCase();
        int drawableResourceId = this.getResources().getIdentifier(flagName, "drawable", getActivity().getPackageName());
        int resourceId = this.getResources().getIdentifier(flagName,"raw", getActivity().getPackageName());

        // change flag
        flag.setBackgroundResource(drawableResourceId);
        // change title
        overviewTitle.setText(item);
        // change text
        overviewText.setMovementMethod(new ScrollingMovementMethod());
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(resourceId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            overviewText.setText(new String(b));
        } catch (Exception e) {
            // e.printStackTrace();
            overviewText.setText("Error: can't show info text.");
        }
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0 );
    }

    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Fragment details;
            switch (view.getId()) {
                case R.id.Overview:
                    details = CountryOverviewFragment.newInstance(getShownIndex());
                    switchView(details);
                    break;
                case R.id.Information:
                    details = CountryInfoFragment.newInstance(getShownIndex());
                    switchView(details);
                    break;
                default:
                    break;
            }
        }

        public void switchView(Fragment details){
            int orientation = getResources().getConfiguration().orientation;
            // replace the placeholder with the new fragment stance within a transaction
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                fragmentTransaction.replace( R.id.countryInfo, details );
            }else{
                fragmentTransaction.replace( android.R.id.content, details );
            }
            // TRANSIT_FRAGMENT_FADE means that the fragment should fade in or fade out
            fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
            // commit the transaction, i.e. make the changes
            fragmentTransaction.commit();
        }
    }
}
