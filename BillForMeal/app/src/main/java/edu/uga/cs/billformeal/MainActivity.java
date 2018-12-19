package edu.uga.cs.billformeal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // edit text
    private EditText totalText;
    private EditText participantText;

    // result
    private TextView tipsText;
    private TextView finalAmountText;

    // button
    private Button tenButton;
    private Button fifteenButton;
    private Button eighteenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // edit text
        totalText = findViewById(R.id.billTotal);
        participantText = findViewById(R.id.participantNumber);

        // result
        tipsText = findViewById(R.id.tips);
        finalAmountText = findViewById(R.id.finalAmount);

        // button
        tenButton = findViewById(R.id.ten);
        fifteenButton = findViewById(R.id.fifteen);
        eighteenButton = findViewById(R.id.eighteen);

        tenButton.setOnClickListener(new ButtonClickListener());
        fifteenButton.setOnClickListener(new ButtonClickListener());
        eighteenButton.setOnClickListener(new ButtonClickListener());
    }
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ten:
                    billCalculation(0.1);
                    break;
                case R.id.fifteen:
                    billCalculation(0.15);
                    break;
                case R.id.eighteen:
                    billCalculation(0.18);
                    break;
                default:
                    break;
            }
        }

        /*
         * calculation method to calculate the tips and total, then display
         * @param tipsPercentage is the tips percentage.
         * @return nothing.
         */
        private void billCalculation(double tipsPercentage){
            // set total
            double total;
            if (totalText.getText().toString().length() != 0) {
                total = Double.parseDouble(totalText.getText().toString());
            }else{
                return;
            }
            // set participant number
            int participant;
            if(participantText.getText().toString().length() != 0){
                participant = Integer.parseInt(participantText.getText().toString());
            }else{
                participant = 1;
            }
            // calculate tips, final amount and each person
            double tips = total * tipsPercentage;
            double finalAmount = total + tips;
            double eachPerson = finalAmount / participant;
            tipsText.setText("Tips: " + String.format("%.2f", tips));
            if(participant == 0){
                finalAmountText.setText("Error(Your participant number is 0.)");
            }else {
                finalAmountText.setText("final amount to paid: " + String.format("%.2f", eachPerson));
            }
        }
    }
}
