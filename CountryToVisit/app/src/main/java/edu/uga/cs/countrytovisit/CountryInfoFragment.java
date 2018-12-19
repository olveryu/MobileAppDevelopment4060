package edu.uga.cs.countrytovisit;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


public class CountryInfoFragment extends Fragment {
    ImageView city;
    TextView informationTitle;
    TextView informationText;
    String item;
    Button overviewButton;

    public static CountryInfoFragment newInstance(int index ) {
        // this uses the default constructor (not defined in this class, but Java-supplied)
        CountryInfoFragment fragment = new CountryInfoFragment();
        // save the selected list index in the new fragment's Bundle data
        // the CountryInfoFragment needs to know the country to display
        Bundle args = new Bundle();
        args.putInt( "index", index );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.activity_informations, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        city = getView().findViewById(R.id.City);
        informationTitle = getView().findViewById(R.id.InformationsTitle);
        informationText = getView().findViewById(R.id.InformationText);
        overviewButton = getView().findViewById(R.id.Overview);

        // set button listener
        overviewButton.setOnClickListener(new ButtonClickListener());

        // get select item from main
        Intent intent = getActivity().getIntent();
        item = intent.getStringExtra( "countryName" );

        // get flag id by name
        String cityName = item.replace(" ", "_").toLowerCase() + "_city";
        String cityInformations = item.replace(" ", "_").toLowerCase() + "_informations";
        int drawableResourceId = this.getResources().getIdentifier(cityName, "drawable", getActivity().getPackageName());
        int resourceId = this.getResources().getIdentifier(cityInformations,"raw", getActivity().getPackageName());

        // change flag
        city.setBackgroundResource(drawableResourceId);
        // change title
        informationTitle.setText(item);
        // change text
        informationText.setMovementMethod(new ScrollingMovementMethod());
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(resourceId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            informationText.setText(new String(b));
        } catch (Exception e) {
            // e.printStackTrace();
            informationText.setText("Error: can't show info text.");
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
