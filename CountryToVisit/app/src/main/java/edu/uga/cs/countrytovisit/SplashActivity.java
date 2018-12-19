package edu.uga.cs.countrytovisit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends BasicActivity {
    private TextView introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        introduction = findViewById(R.id.introduction);
        introduction.setText("This application can let user select one of a few countries to visit." +
                "and the details would provide a brief overview of the country, " +
                "such as the general location, population, capital city, etc. " +
                "And the most important information for a tourist," +
                " what places to visit, the country’s currency, the current exchange rate, etc.");
    }

}
