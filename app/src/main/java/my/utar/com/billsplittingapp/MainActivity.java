package my.utar.com.billsplittingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView labelTotalBill, labelNumOfPeople;
    private EditText editTextTotalBill, editTextNumPeople, editTextTotalBill2, editTextNumPeople2, editPercentage1, editPercentage2;
    private RadioGroup radioGroupOptions;
    private RadioGroup radioGroupOptions2;
    private RadioButton radioButtonEqual, radioButtonCustom, radioButtonIndividualAmount,
            radioButtonPercentage;
    private Button buttonCalculate, buttonCustom;
    private TextView textViewResult;

    // Clear input field function
    public void clearEditText(EditText editText) {
        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelTotalBill = findViewById(R.id.labelTotalBill);
        editTextTotalBill = findViewById(R.id.editTextTotalBill);
        editTextTotalBill2 = findViewById(R.id.editTextTotalBill2);
        labelNumOfPeople = findViewById(R.id.labelNumOfPeople);
        editTextNumPeople = findViewById(R.id.editTextNumPeople);
        editTextNumPeople2 = findViewById(R.id.editTextNumPeople2);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioGroupOptions2 = findViewById(R.id.radioGroupOptions2);
        radioButtonEqual = findViewById(R.id.radioButtonEqual);
        radioButtonCustom = findViewById(R.id.radioButtonCustom);
        radioButtonIndividualAmount = findViewById(R.id.radioButtonIndividualAmount);
        radioButtonPercentage = findViewById(R.id.radioButtonPercentage);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCustom = findViewById(R.id.buttonCalculateCustom);
        textViewResult = findViewById(R.id.textViewResult);

        findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
        findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);

        radioGroupOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonEqual) {
                    // Show equal breakdown UI and hide custom breakdown UI
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);
                } else if (checkedId == R.id.radioButtonCustom) {
                    // Show custom breakdown UI and hide equal breakdown UI
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.VISIBLE);
                    radioButtonPercentage.setChecked(true);
                }
            }
        });

        radioGroupOptions2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButtonPercentage) {
                    // Show percentage breakdown UI and hide custom breakdown UI
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.VISIBLE);
                    // Show or hide the dynamic EditText fields based on selected option
                } else if (checkedId == R.id.radioButtonIndividualAmount) {
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.GONE);

                } else {
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.GONE);
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
                    radioButtonEqual.setChecked(true);
                }

            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateEqualBillBreakdown();
            }
        });

        buttonCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCustomBillBreakdown();
            }
        });

        // Initialize the number of people to 2 initially
        editTextNumPeople2.setText("2");

        // TextWatcher for Custom Percentage
        editTextNumPeople2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateCustomPercentageLayout();
            }
        });

        // TextWatcher for Individual Amounts
        editTextNumPeople2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateIndividualAmountsLayout();
            }
        });

        // Call updateIndividualAmountsLayout and updateCustomPercentageLayout
        // initially to set up the layout with 2 input fields
        //updateIndividualAmountsLayout();
        //updateCustomPercentageLayout();
    }


    private void calculateEqualBillBreakdown() {

        // Get the total bill amount and number of people from EditText fields
        String totalBillStr = editTextTotalBill.getText().toString();
        String numPeopleStr = editTextNumPeople.getText().toString();

        // Check if the input fields are empty
        if (totalBillStr.isEmpty() || numPeopleStr.isEmpty()) {
            Toast.makeText(this, "Please enter both the total bill amount and the number of people.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input strings to doubles
        double totalBillAmount = Double.parseDouble(totalBillStr);
        int numPeople = Integer.parseInt(numPeopleStr);

        // Check if the number of people is greater than zero
        if (numPeople <= 0) {
            Toast.makeText(this, "Please enter a valid number of people (greater than zero).", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate the bill break-down based on the selected option (Equal or Custom)
        if (radioButtonEqual.isChecked()) {
            // Equal break-down: divide total bill amount by the number of people
            double equalAmount = totalBillAmount / numPeople;

            // Display the result in the textViewResult
            StringBuilder resultBuilder = new StringBuilder("Bill Break-Down:\n");

            String formattedAmount = String.format("%.2f", equalAmount);
            resultBuilder.append("Each person need to pay: RM").append(formattedAmount).append(".");

            textViewResult.setText(resultBuilder.toString());
            textViewResult.setVisibility(View.VISIBLE);
            clearEditText(editTextNumPeople);
            clearEditText(editTextTotalBill);
        } else {
            // Custom bill Break-down
            // Custom break-down: calculate individual amounts based on percentages
            double[] percentages = new double[numPeople];
            double totalPercentage = 0.0;

            // Get the custom percentages from EditText fields
            for (int i = 0; i < numPeople; i++) {
                EditText editTextPercentage = findViewById(getResources().getIdentifier("editTextPercentage" + (i + 1), "id", getPackageName()));
                String percentageStr = editTextPercentage.getText().toString();

                if (percentageStr.isEmpty()) {
                    Toast.makeText(this, "Please enter percentage for Person " + (i + 1), Toast.LENGTH_SHORT).show();
                    return;
                }

                percentages[i] = Double.parseDouble(percentageStr);
                totalPercentage += percentages[i];
            }

            // Check if the total percentage is 100%
            if (Math.abs(totalPercentage - 100.0) > 0.001) {
                Toast.makeText(this, "Total percentage must add up to 100%", Toast.LENGTH_SHORT).show();
                return;
            }


            double[] individualAmounts;
            // Calculate individual amounts based on percentages
            individualAmounts = new double[numPeople];
            for (int i = 0; i < numPeople; i++) {
                individualAmounts[i] = (percentages[i] / 100.0) * totalBillAmount;
            }

            findViewById(R.id.layoutCustomBreakdown).setVisibility(View.VISIBLE);

            // Display the result in the textViewResult
            StringBuilder customResultBuilder = new StringBuilder("Bill Break-Down:\n");
            for (int i = 0; i < numPeople; i++) {
                customResultBuilder.append("Person ").append(i + 1).append(": RM ").append(individualAmounts[i]).append("\n");
            }
        }
    }

    private void updateCustomPercentageLayout() {
        EditText editTextNumPeople2 = findViewById(R.id.editTextNumPeople2);
        int numPeople;

        try {
            numPeople = Integer.parseInt(editTextNumPeople2.getText().toString());
        } catch (NumberFormatException e) {
            // If the "Number of People" field is empty or not a valid number, default to 2
            numPeople = 2;
        }

        // Ensure that the number of people is at least 2
        if (numPeople < 2) {
            Toast.makeText(this, "Number of People must be MORE than or EQUAL to 2!", Toast.LENGTH_SHORT).show();
            numPeople = 2;
        }

        LinearLayout layoutCustomPercentage = findViewById(R.id.layoutCustomPercentage);
        layoutCustomPercentage.removeAllViews();

        for (int i = 1; i <= numPeople; i++) {
            EditText editText = new EditText(this);
            editText.setId(i);
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editText.setHint("Enter Percentage For Person " + i);
            layoutCustomPercentage.addView(editText);
        }
    }

    private void updateIndividualAmountsLayout() {
        EditText editTextNumPeople2 = findViewById(R.id.editTextNumPeople2);
        int numPeople;

        try {
            numPeople = Integer.parseInt(editTextNumPeople2.getText().toString());
        } catch (NumberFormatException e) {
            // If the "Number of People" field is empty or not a valid number, default to 2
            numPeople = 2;
        }

        // Ensure that the number of people is at least 2
        if (numPeople < 2) {
            Toast.makeText(this, "Number of People must be MORE than or EQUAL to 2!", Toast.LENGTH_SHORT).show();
            numPeople = 2;
        }

        LinearLayout layoutIndividualAmounts = findViewById(R.id.layoutIndividualAmount);
        layoutIndividualAmounts.removeAllViews();

        for (int i = 1; i <= numPeople; i++) {
            EditText editText = new EditText(this);
            editText.setId(i);
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editText.setHint("Enter Amount for Person " + i);
            layoutIndividualAmounts.addView(editText);
        }
    }

    private void calculateCustomBillBreakdown() {

        // Get the total bill amount and number of people from EditText fields
        String totalBillStr = editTextTotalBill2.getText().toString();
        String numPeopleStr = editTextNumPeople2.getText().toString();

        // Check if the input fields are empty
        if (totalBillStr.isEmpty() || numPeopleStr.isEmpty()) {
            Toast.makeText(this, "Please enter both the total bill amount and the number of people.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input strings to doubles
        double totalBillAmount = Double.parseDouble(totalBillStr);
        int numPeople = Integer.parseInt(numPeopleStr);

        if (radioButtonPercentage.isChecked()) {
            // ... (existing code for percentage calculation) ...
        } else if (radioButtonIndividualAmount.isChecked()) {

            // Get the individual amounts from dynamic EditText fields
            double[] individualAmounts = new double[numPeople];
            double totalIndividualAmount = 0.0;

            for (int i = 0; i < numPeople; i++) {
                EditText editTextAmount = findViewById(i + 1);
                String amountStr = editTextAmount.getText().toString();

                if (amountStr.isEmpty()) {
                    Toast.makeText(this, "Please enter amount for Person " + (i + 1), Toast.LENGTH_SHORT).show();
                    return;
                }

                individualAmounts[i] = Double.parseDouble(amountStr);
                totalIndividualAmount += individualAmounts[i];
            }

            // Check if the total individual amount matches the total bill amount
            if (Math.abs(totalIndividualAmount - totalBillAmount) > 0.001) {
                Toast.makeText(this, "Total individual amount must be equal to the total bill amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Display the result in the textViewResult
            StringBuilder customResultBuilder = new StringBuilder("Bill Break-Down:\n");
            for (int i = 0; i < numPeople; i++) {
                customResultBuilder.append("Person ").append(i + 1).append(": RM ").append(individualAmounts[i]).append("\n");
            }
            textViewResult.setText(customResultBuilder.toString());
            textViewResult.setVisibility(View.VISIBLE);

            // Check if the total individual amount matches the total bill amount exactly
            if (Math.abs(totalIndividualAmount - totalBillAmount) < 0.001) {
                Toast.makeText(this, "Amounts are correctly calculated.", Toast.LENGTH_SHORT).show();
            } else {
                // Calculate the discrepancy amount (how much money is left out or exceeded)
                double discrepancyAmount = totalIndividualAmount - totalBillAmount;
                if (discrepancyAmount > 0) {
                    Toast.makeText(this, "The total amount exceeds the total bill by RM " + discrepancyAmount, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "The total amount is RM " + Math.abs(discrepancyAmount) + " less than the total bill.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}