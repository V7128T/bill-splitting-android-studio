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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView labelNumOfPeople;
    private EditText editTextTotalBill, editTextNumPeople, editTextTotalBill2, editTextNumPeoplePercentage, editTextNumPeopleIndividual;
    private RadioGroup radioGroupOptions;
    private RadioGroup radioGroupOptions2;
    private RadioButton radioButtonEqual, radioButtonCustom, radioButtonIndividualAmount,
            radioButtonPercentage;
    private Button buttonCalculate, buttonCustom;
    private TextView textViewResult;
    private ArrayList<EditText> editTextPercentageList = new ArrayList<>();
    private ArrayList<EditText> editTextIndividualList = new ArrayList<>();

    // Clear input field function
    public void clearEditText(EditText editText) {
        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView labelTotalBill = findViewById(R.id.labelTotalBill);
        editTextNumPeoplePercentage = findViewById(R.id.editTextNumPeoplePercentage);
        editTextNumPeopleIndividual = findViewById(R.id.editTextNumPeopleIndividual);
        editTextTotalBill = findViewById(R.id.editTextTotalBill);
        editTextTotalBill2 = findViewById(R.id.editTextTotalBill2);
        labelNumOfPeople = findViewById(R.id.labelNumOfPeople);
        editTextNumPeople = findViewById(R.id.editTextNumPeople);
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
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
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
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
                    editTextNumPeoplePercentage.setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.VISIBLE);
                    // Show or hide the dynamic EditText fields based on selected option
                } else if (checkedId == R.id.radioButtonIndividualAmount) {
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.VISIBLE);
                    editTextNumPeoplePercentage.setVisibility(View.GONE);
                    editTextNumPeopleIndividual.setVisibility(View.VISIBLE);
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
        editTextNumPeoplePercentage.setText("2");
        editTextNumPeopleIndividual.setText("2");

        // TextWatcher for Custom Percentage

        editTextNumPeoplePercentage.addTextChangedListener(new TextWatcher() {
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
        editTextNumPeopleIndividual.addTextChangedListener(new TextWatcher() {
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
        }
    }

    private void updateCustomPercentageLayout() {

        int numPeople;

        try {
            numPeople = Integer.parseInt(editTextNumPeoplePercentage.getText().toString());
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

        // Clear the existing list
        editTextPercentageList.clear();

        for (int i = 1; i <= numPeople; i++) {
            EditText editTextPercentageIds = new EditText(this);
            editTextPercentageIds.setId(View.generateViewId()); // Generate unique ID
            editTextPercentageIds.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editTextPercentageIds.setHint("Enter Percentage For Person " + i);
            layoutCustomPercentage.addView(editTextPercentageIds);

            // Add the dynamically generated EditText view to the list
            editTextPercentageList.add(editTextPercentageIds);
        }
    }

    private void updateIndividualAmountsLayout() {

        int numPeople;

        try {
            numPeople = Integer.parseInt(editTextNumPeopleIndividual.getText().toString());
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

        // Clear the existing list
        editTextIndividualList.clear();

        for (int i = 1; i <= numPeople; i++) {
            EditText editTextIndividualIds = new EditText(this);
            editTextIndividualIds.setId(View.generateViewId());
            editTextIndividualIds.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editTextIndividualIds.setHint("Enter Amount for Person " + i);
            layoutIndividualAmounts.addView(editTextIndividualIds);

            // Add the dynamically generated EditText view to the list
            editTextIndividualList.add(editTextIndividualIds);
        }
    }

    private void calculateCustomBillBreakdown() {

        // Get the total bill amount and number of people from EditText fields
        String numPeopleStrPercent = editTextNumPeoplePercentage.getText().toString();
        String numPeopleStrIndividual = editTextNumPeopleIndividual.getText().toString();


        String totalBillStr = editTextTotalBill2.getText().toString();
        // Check if the input fields are empty
        if (totalBillStr.isEmpty() || numPeopleStrIndividual.isEmpty()
                || numPeopleStrPercent.isEmpty()) {
            Toast.makeText(this,
                    "Please enter both the all the details before proceed calculation."
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input strings to doubles
        double totalBillAmount = Double.parseDouble(totalBillStr);
        int numPeoplePercentage = Integer.parseInt(numPeopleStrPercent);
        int numPeopleIndividual = Integer.parseInt(numPeopleStrIndividual);

        if (radioButtonPercentage.isChecked()) {
            // Custom break-down: calculate individual amounts based on percentages
            double[] percentages = new double[numPeoplePercentage];
            double totalPercentage = 0.0;

            // Get the custom percentages from EditText fields
            for (int i = 0; i < numPeoplePercentage; i++) {
                EditText editTextPercentageIds = editTextPercentageList.get(i);

                if (editTextPercentageIds.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter percentage for Person " + (i + 1), Toast.LENGTH_SHORT).show();
                    return;
                }

                percentages[i] = Double.parseDouble(editTextPercentageIds.getText().toString());
                totalPercentage += percentages[i];
            }

            // Check if the total percentage is 100%
            if (Math.abs(totalPercentage - 100.0) > 0.001) {
                Toast.makeText(this, "Total percentage must add up to 100%", Toast.LENGTH_SHORT).show();
                return;
            }


            // Calculate individual amounts based on percentages
            double[] individualAmounts = new double[numPeoplePercentage];
            for (int i = 0; i < numPeoplePercentage; i++) {
                individualAmounts[i] = (percentages[i] / 100.0) * totalBillAmount;
            }

            //findViewById(R.id.layoutCustomBreakdown).setVisibility(View.VISIBLE);

            // Display the result in dialog
            StringBuilder customResultBuilder = new StringBuilder("Total Amount to pay for each person:\n" + "----------------------\n");
            for (int i = 0; i < numPeoplePercentage; i++) {
                customResultBuilder.append("Person ").append(i + 1).append(": RM ").append(individualAmounts[i]).append("\n");
            }

            resultDialog showResultDialog = resultDialog.newInstances(customResultBuilder.toString());
            showResultDialog.show(getSupportFragmentManager(),customResultBuilder.toString());


            // Custom Break-down: Individual
        } else if (radioButtonIndividualAmount.isChecked()) {

            // Get the individual amounts from dynamic EditText fields
            double[] individualAmounts = new double[numPeopleIndividual];
            double totalIndividualAmount = 0.0;

            for (int i = 0; i < numPeopleIndividual; i++) {
                EditText editTextAmount = editTextIndividualList.get(i);
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
            for (int i = 0; i < numPeopleIndividual; i++) {
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