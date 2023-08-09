package my.utar.com.billsplittingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTotalBill, editTextNumPeople, editTextTotalBill2,
            editTextNumPeoplePercentage, editTextNumPeopleIndividual,
            editTextNumPeopleCombine, editTextTotalBillCombine;
    private RadioButton radioButtonEqual, radioButtonCustom, radioButtonIndividualAmount,
            radioButtonPercentage, radioButtonCombine, radioButtonCombine2;
    private TextView textViewResult;
    private ArrayList<EditText> editTextPercentageList = new ArrayList<>();
    private ArrayList<EditText> editTextIndividualList = new ArrayList<>();
    private ArrayList<EditText> editTextCombinePercentageList = new ArrayList<>();
    private ArrayList<EditText> editTextCombineAmountList = new ArrayList<>();

    // Clear input field function
    public void clearEditText(EditText editText) {
        editText.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Splitless.");
//        DeleteData("equal_breakdown");
//        DeleteData("individual_breakdown");
//        DeleteData("percentage_breakdown");
//        DeleteData("combine_breakdown");

        editTextNumPeople = findViewById(R.id.editTextNumPeople);
        editTextNumPeoplePercentage = findViewById(R.id.editTextNumPeoplePercentage);
        editTextNumPeopleIndividual = findViewById(R.id.editTextNumPeopleIndividual);
        editTextNumPeopleCombine = findViewById(R.id.editTextNumPeopleCombine);
        editTextTotalBill = findViewById(R.id.editTextTotalBill);
        editTextTotalBill2 = findViewById(R.id.editTextTotalBill2);
        editTextTotalBillCombine = findViewById(R.id.editTextTotalBillCombine);
        RadioGroup radioGroupOptions = findViewById(R.id.radioGroupOptions);
        RadioGroup radioGroupOptions2 = findViewById(R.id.radioGroupOptions2);
        RadioGroup radioGroupCombine = findViewById(R.id.radioGroupCombine);
        radioButtonEqual = findViewById(R.id.radioButtonEqual);
        radioButtonCustom = findViewById(R.id.radioButtonCustom);
        radioButtonCombine = findViewById(R.id.radioButtonCombine);
        radioButtonCombine2 = findViewById(R.id.radioButtonCombine2);
        radioButtonIndividualAmount = findViewById(R.id.radioButtonIndividualAmount);
        radioButtonPercentage = findViewById(R.id.radioButtonPercentage);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        Button buttonCustom = findViewById(R.id.buttonCalculateCustom);
        Button buttonCalculateCombine = findViewById(R.id.buttonCalculateCombine);
        textViewResult = findViewById(R.id.textViewResult);

        findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
        findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);

        radioGroupOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonEqual) {
                    // Show equal breakdown UI and hide custom breakdown UI
                    setTitle("Equal-Breakdown");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.GONE);
                } else if (checkedId == R.id.radioButtonCustom) {
                    // Show custom breakdown UI and hide equal breakdown UI
                    setTitle("Custom Breakdown: Percentage");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.VISIBLE);
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.VISIBLE);
                    radioButtonPercentage.setChecked(true);
                } else {
                    setTitle("Combined Breakdown: Percentage & Amounts");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.GONE);
                    editTextNumPeople.setVisibility(View.GONE);
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
                    editTextNumPeoplePercentage.setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCombinePercentage).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCombineAmount).setVisibility(View.VISIBLE);
                    editTextTotalBillCombine.setVisibility(View.VISIBLE);
                    editTextNumPeopleCombine.setVisibility(View.VISIBLE);
                    radioButtonCombine2.setChecked(true);
                }
            }
        });

        radioGroupCombine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonEqual3) {
                    // Show equal breakdown UI and hide custom breakdown UI
                    setTitle("Equal-Breakdown");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.GONE);
                    editTextNumPeople.setVisibility(View.VISIBLE);
                    radioButtonEqual.setChecked(true);
                } else if (checkedId == R.id.radioButtonCustom2) {
                    // Show custom breakdown UI and hide equal breakdown UI
                    setTitle("Percentage Breakdown");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.VISIBLE);
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.VISIBLE);
                    radioButtonPercentage.setChecked(true);
                } else {
                    setTitle("Combined Breakdown");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.VISIBLE);
                    radioButtonCombine.setChecked(true);
                }
            }
        });

        radioGroupOptions2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButtonPercentage) {
                    // Show percentage breakdown UI and hide Equal breakdown UI
                    setTitle("Percentage Breakdown");
                    editTextNumPeopleIndividual.setText("2");
                    editTextNumPeopleCombine.setText("2");
                    clearEditText(editTextTotalBill2);
                    clearEditText(editTextTotalBillCombine);
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.GONE);
                    findViewById(R.id.svIndividualAmount).setVisibility(View.GONE);
                    findViewById(R.id.svPercentage).setVisibility(View.VISIBLE);
                    editTextNumPeopleIndividual.setVisibility(View.GONE);
                    editTextNumPeoplePercentage.setVisibility(View.VISIBLE);
                    editTextTotalBillCombine.setVisibility(View.GONE);
                    editTextNumPeopleCombine.setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.VISIBLE);
                    // Show or hide the dynamic EditText fields based on selected option
                } else if (checkedId == R.id.radioButtonIndividualAmount) {
                    setTitle("Individual Amounts Breakdown");
                    clearEditText(editTextTotalBill2);
                    clearEditText(editTextTotalBillCombine);
                    editTextNumPeoplePercentage.setText("2");
                    editTextNumPeopleCombine.setText("2");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.VISIBLE);
                    findViewById(R.id.svPercentage).setVisibility(View.GONE);
                    findViewById(R.id.svIndividualAmount).setVisibility(View.VISIBLE);
                    editTextNumPeoplePercentage.setVisibility(View.GONE);
                    editTextNumPeopleIndividual.setVisibility(View.VISIBLE);
                    editTextTotalBillCombine.setVisibility(View.GONE);
                    editTextNumPeopleCombine.setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomPercentage).setVisibility(View.GONE);

                } else if (checkedId == R.id.radioButtonCombine) {
                    setTitle("Combined Breakdown");
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCustomBreakdown).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombine).setVisibility(View.VISIBLE);
                    clearEditText(editTextTotalBill2);
                    editTextNumPeoplePercentage.setVisibility(View.GONE);
                    editTextNumPeopleIndividual.setVisibility(View.GONE);

                } else {
                    setTitle("Equal-Breakdown");
                    clearEditText(editTextTotalBill2);
                    editTextNumPeopleIndividual.setText("2");
                    editTextNumPeoplePercentage.setText("2");
                    findViewById(R.id.layoutIndividualAmount).setVisibility(View.GONE);
                    findViewById(R.id.layoutEqualBreakdown).setVisibility(View.VISIBLE);
                    findViewById(R.id.layoutCombine).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombinePercentage).setVisibility(View.GONE);
                    findViewById(R.id.layoutCombineAmount).setVisibility(View.GONE);
                    findViewById(R.id.svPercentage).setVisibility(View.GONE);
                    findViewById(R.id.svIndividualAmount).setVisibility(View.GONE);
                    editTextTotalBillCombine.setVisibility(View.GONE);
                    editTextNumPeopleCombine.setVisibility(View.GONE);
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

        buttonCalculateCombine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCombineBillBreakdown();
            }
        });

        // Initialize the number of people to 2 initially
        editTextNumPeoplePercentage.setText("2");
        editTextNumPeopleIndividual.setText("2");
        editTextNumPeopleCombine.setText("2");

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

        // TextWatcher for Individual Amounts
        editTextNumPeopleCombine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateCombineLayout();
            }
        });


    }


    private void updateCustomPercentageLayout() {

        int numPeople, maxNumPeople = 10;

        try {
            numPeople = Integer.parseInt(editTextNumPeoplePercentage.getText().toString());
        } catch (NumberFormatException e) {
            // If the "Number of People" field is empty or not a valid number, default to 2
            numPeople = 2;
        }

        // Ensure that the number of people is at least 2
        if (numPeople < 2) {
            Toast.makeText(this, "Please enter a valid number of people (greater than 1).", Toast.LENGTH_SHORT).show();
            numPeople = 2;
        } else if (numPeople > maxNumPeople) {
            Toast.makeText(this, "Number of People cannot exceed " + maxNumPeople, Toast.LENGTH_LONG).show();
            editTextNumPeoplePercentage.setText("10");
            numPeople = 10;
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
            // Set input type to accept only NUMBERS with decimals
            editTextPercentageIds.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // Set font family and text color
            // Customized Cursor but API level 29 required..
            //editTextPercentageIds.setCursorDrawable(R.drawable.custom_cursor);
            editTextPercentageIds.setTypeface(ResourcesCompat.getFont(this, R.font.proto_mono_regular));
            editTextPercentageIds.setTextColor(ContextCompat.getColor(this, R.color.terminalGreen));
            editTextPercentageIds.setHintTextColor(ContextCompat.getColor(this, R.color.lightGrey_800));

            layoutCustomPercentage.addView(editTextPercentageIds);

            // Add the dynamically generated EditText view to the list
            editTextPercentageList.add(editTextPercentageIds);
        }
    }

    private void updateIndividualAmountsLayout() {

        // Set the maximum number of people to 10
        int numPeople, maxNumPeople = 10;

        try {
            numPeople = Integer.parseInt(editTextNumPeopleIndividual.getText().toString());
        } catch (NumberFormatException e) {
            // If the "Number of People" field is empty or not a valid number, default to 2
            numPeople = 2;
        }


        // Ensure that the number of people is at least 2 and not more than 10
        if (numPeople < 2) {
            Toast.makeText(this, "Please enter a valid number of people (greater than 1).", Toast.LENGTH_SHORT).show();
            numPeople = 2;
        } else if (numPeople > maxNumPeople) {
            Toast.makeText(this, "Number of People cannot exceed " + maxNumPeople, Toast.LENGTH_LONG).show();
            editTextNumPeopleIndividual.setText("10");
            numPeople = 10;
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
            // Set input type to accept only NUMBERS with decimals
            editTextIndividualIds.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // Set font family and text color
            editTextIndividualIds.setTypeface(ResourcesCompat.getFont(this, R.font.proto_mono_regular));
            editTextIndividualIds.setTextColor(ContextCompat.getColor(this, R.color.terminalGreen));
            editTextIndividualIds.setHintTextColor(ContextCompat.getColor(this, R.color.lightGrey_800));

            layoutIndividualAmounts.addView(editTextIndividualIds);

            // Add the dynamically generated EditText view to the list
            editTextIndividualList.add(editTextIndividualIds);
        }
    }

    private void updateCombineLayout() {


        Log.d("CombineLayout", "updateCombineLayout() called");
        int numPeople, maxNumPeople = 10;

        try {
            numPeople = Integer.parseInt(editTextNumPeopleCombine.getText().toString());
            Log.d("CombineLayout", "Number of people: " + numPeople);
        } catch (NumberFormatException e) {
            numPeople = 2;
        }

        if (numPeople < 2) {
            Toast.makeText(this, "Number of People must be MORE than or EQUAL to 2!", Toast.LENGTH_SHORT).show();
            numPeople = 2;
        } else if (numPeople > maxNumPeople) {
            Toast.makeText(this, "Number of People cannot exceed " + maxNumPeople, Toast.LENGTH_LONG).show();
            editTextNumPeopleCombine.setText("10");
            numPeople = 10;
        }


        // Log the visibility status of the layout
        Log.d("CombineLayout", "layoutCombinePercentage visibility: "
                + findViewById(R.id.layoutCombinePercentage).getVisibility());
        Log.d("CombineLayout", "layoutCombineAmount visibility: "
                + findViewById(R.id.layoutCombineAmount).getVisibility());

        LinearLayout layoutCombinePercentage = findViewById(R.id.layoutCombinePercentage);
        LinearLayout layoutCombineAmount = findViewById(R.id.layoutCombineAmount);
        layoutCombinePercentage.removeAllViews();
        layoutCombineAmount.removeAllViews();

        editTextCombinePercentageList.clear();
        editTextCombineAmountList.clear();

        for (int i = 1; i <= numPeople; i++) {
            // Combine Percentage
            EditText editTextCombinePercentageIds = new EditText(this);
            editTextCombinePercentageIds.setId(View.generateViewId());
            editTextCombinePercentageIds.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editTextCombinePercentageIds.setHint("Enter Percentage For Person " + i);
            // Set input type to accept only NUMBERS with decimals
            editTextCombinePercentageIds.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // Set font family and text color
            editTextCombinePercentageIds.setTypeface(ResourcesCompat.getFont(this, R.font.proto_mono_regular));
            editTextCombinePercentageIds.setTextColor(ContextCompat.getColor(this, R.color.terminalGreen));
            editTextCombinePercentageIds.setHintTextColor(ContextCompat.getColor(this, R.color.lightGrey_800));

            layoutCombinePercentage.addView(editTextCombinePercentageIds);


            // Combine Amount
            EditText editTextCombineAmountIds = new EditText(this);
            editTextCombineAmountIds.setId(View.generateViewId());
            editTextCombineAmountIds.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editTextCombineAmountIds.setHint("Enter Amount for Person " + i);
            // Set input type to accept only NUMBERS with decimals
            editTextCombineAmountIds.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // Set font family and text color
            editTextCombineAmountIds.setTypeface(ResourcesCompat.getFont(this, R.font.proto_mono_regular));
            editTextCombineAmountIds.setTextColor(ContextCompat.getColor(this, R.color.terminalGreen));
            editTextCombineAmountIds.setHintTextColor(ContextCompat.getColor(this, R.color.lightGrey_800));

            layoutCombineAmount.addView(editTextCombineAmountIds);

            // Add the dynamically generated EditText view to the list
            editTextCombinePercentageList.add(editTextCombinePercentageIds);
            editTextCombineAmountList.add(editTextCombineAmountIds);
        }
    }

    private void calculateEqualBillBreakdown() {

        // Get the total bill amount and number of people from EditText fields
        String totalBillStr = editTextTotalBill.getText().toString();
        String numPeopleStr = editTextNumPeople.getText().toString();

        // Set Maximum 10 people
        int maxNumPeople = 10;

        // Check if there's no radio button chosen
        if (!radioButtonEqual.isChecked() && !radioButtonCustom.isChecked() && !radioButtonCombine.isChecked()) {
            Toast.makeText(this, "Please choose one of the options below (Equal / Custom / Combine).", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the input fields are empty
        if (totalBillStr.isEmpty() || numPeopleStr.isEmpty()) {
            Toast.makeText(this, "Please enter both the total bill amount and the number of people.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input strings to doubles
        double totalBillAmount = Double.parseDouble(totalBillStr);
        int numPeople = Integer.parseInt(numPeopleStr);

        // Check if the number of people is greater than two and not greater than 10
        if (numPeople < 2) {
            Toast.makeText(this, "Please enter a valid number of people (greater than 1).", Toast.LENGTH_SHORT).show();
            return;
        } else if (numPeople > maxNumPeople) {
            Toast.makeText(this, "Number of People cannot exceed " + maxNumPeople, Toast.LENGTH_LONG).show();
            editTextNumPeople.setText("10");
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

            int decimalPlaces = 2;
            // Create a DecimalFormat object with the desired format pattern
            String pattern = "#." + new String(new char[decimalPlaces]).replace('\0', '#');
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            // Format the double value to the 2 decimal places
            double formattedEqualAmount = Double.parseDouble(decimalFormat.format(equalAmount));
            double formattedTotalAmount = Double.parseDouble(decimalFormat.format(totalBillAmount));

            // Create an ArrayList of Person objects with equal amounts
            ArrayList<Person> personDetails = new ArrayList<>();
            for (int i = 0; i < numPeople; i++) {
                personDetails.add(new Person("Equal Breakdown", "Person " + (i + 1), formattedTotalAmount, formattedEqualAmount, 0));
            }

            // Display the result in dialog
            StringBuilder customResultBuilder = new StringBuilder("Total Amount = RM"+ formattedTotalAmount + "\n");

            // Line dashes
            int lineLength = 11;
            for (int i = 0; i < lineLength; i++) {
                customResultBuilder.append("><");
            }

            customResultBuilder.append("\nTotal Amount to pay for each person:\n");

            // Line dashes
            for (int i = 0; i < lineLength; i++) {
                customResultBuilder.append("><");
            }

            customResultBuilder.append("\n");

            for (int i = 0; i < numPeople; i++) {

                customResultBuilder.append("Person ").append(i + 1).append(": RM").append(formattedEqualAmount).append("\n");
            }

            // Inflate the custom_dialog.xml layout
            View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

            // Find the TextView inside the custom_dialog layout and set the result text
            TextView textViewCustomResult = dialogView.findViewById(R.id.dialogMessage);
            textViewCustomResult.setText(customResultBuilder.toString());

            // Build and show the custom dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            resultDialog dialog = resultDialog.newInstances(customResultBuilder.toString());
            dialog.show(getSupportFragmentManager(), "custom_dialog");

            textViewResult.setText(resultBuilder.toString());
            textViewResult.setVisibility(View.VISIBLE);

            // Save the calculated result and details in SharedPreferences
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            saveSharedPreferencesEqual(currentDate, personDetails);

            clearEditText(editTextNumPeople);
            clearEditText(editTextTotalBill);
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
                    "Please enter all the details before proceeding with the calculation."
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input strings to doubles
        double totalBillAmount = Double.parseDouble(totalBillStr);
        int numPeoplePercentage = Integer.parseInt(numPeopleStrPercent);
        int numPeopleIndividual = Integer.parseInt(numPeopleStrIndividual);

        int decimalPlaces = 2;
        // Create a DecimalFormat object with the desired format pattern
        String pattern = "#." + new String(new char[decimalPlaces]).replace('\0', '#');
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        if (editTextPercentageList.isEmpty()) {
            Toast.makeText(this, "Please enter percentages for each person.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (radioButtonPercentage.isChecked()) {
            // Custom break-down: calculate individual amounts based on percentages
            double[] percentages = new double[numPeoplePercentage];
            double totalPercentage = 0.00;

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

            // Format the double value to the 2 decimal places
            double formattedTotalAmount = Double.parseDouble(decimalFormat.format(totalBillAmount));
            // Format values to 2 decimal places before storing in SharedPreferences
            for (int i = 0; i < numPeoplePercentage; i++) {
                percentages[i] = Double.parseDouble(decimalFormat.format(percentages[i]));
            }

            // Create an ArrayList of Person objects with custom percentages
            ArrayList<Person> personDetails = new ArrayList<>();
            for (int i = 0; i < numPeoplePercentage; i++) {
                double individualAmount = (percentages[i] / 100.0) * totalBillAmount;
                double formattedIndividualAmount = Double.parseDouble(decimalFormat.format(individualAmount));
                personDetails.add(new Person("Custom Percentage", "Person" + (i + 1), formattedTotalAmount, formattedIndividualAmount, percentages[i]));
            }

            // Check if the total percentage is 100%
            if (Math.abs(totalPercentage - 100.0) > 0.001) {
                Toast.makeText(this, "Total percentage must add up to 100%", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate individual amounts based on percentages
            double[] individualAmountsPerc = new double[numPeoplePercentage];
            for (int i = 0; i < numPeoplePercentage; i++) {
                individualAmountsPerc[i] = (percentages[i] / 100.0) * totalBillAmount;
            }

            // Save the calculated result and details in Shared Preferences
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            saveSharedPreferencesPercentage(currentDate, personDetails);

            // Display the result in dialog
            StringBuilder customResultBuilder = new StringBuilder("Total Amount = RM" + formattedTotalAmount + "\n");

            // Line dashes
            int lineLength = 11;
            for (int i = 0; i < lineLength; i++) {
                customResultBuilder.append("><");
            }
            customResultBuilder.append("\n").append("Total Amount to pay for each person:\n");


            for (int i = 0; i < lineLength; i++) {
                customResultBuilder.append("><");
            }

            customResultBuilder.append("\n");


            for (int i = 0; i < numPeoplePercentage; i++) {

                String formattedAmount = String.format("%.2f", individualAmountsPerc[i]);
                customResultBuilder.append("Person ").append(i + 1).append("(").append(percentages[i]).append("%): RM").append(formattedAmount).append("\n");
            }

            // Inflate the custom_dialog.xml layout
            View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

            // Find the TextView inside the custom_dialog layout and set the result text
            TextView textViewCustomResult = dialogView.findViewById(R.id.dialogMessage);
            textViewCustomResult.setText(customResultBuilder.toString());

            // Build and show the custom dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            resultDialog dialog = resultDialog.newInstances(customResultBuilder.toString());
            dialog.show(getSupportFragmentManager(), "custom_dialog");


            // Custom Break-down: Individual
        } else if (radioButtonIndividualAmount.isChecked()) {

            String correctAmountMsg, exceedAmountMsg, lackAmountMsg;
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            // Get the individual amounts from dynamic EditText fields
            double[] individualAmountsIndiv = new double[numPeopleIndividual];
            double totalIndividualAmount = 0.0;


            // Create an ArrayList of Person objects with custom percentages
            ArrayList<Person> personDetails = new ArrayList<>();
            ArrayList<Result> individualResult = new ArrayList<>();


            // Format the double value to the 2 decimal places
            double formattedTotalAmount = Double.parseDouble(decimalFormat.format(totalBillAmount));
            // Format values to 2 decimal places before storing in SharedPreferences
            for (int i = 0; i < numPeopleIndividual; i++) {
                individualAmountsIndiv[i] = Double.parseDouble(decimalFormat.format(individualAmountsIndiv[i]));
            }

            for (int i = 0; i < numPeopleIndividual; i++) {
                EditText editTextIndividual = editTextIndividualList.get(i);
                String amountStr = editTextIndividual.getText().toString();

                if (amountStr.isEmpty()) {
                    Toast.makeText(this, "Please enter amount for Person " + (i + 1), Toast.LENGTH_SHORT).show();
                    return;
                }

                individualAmountsIndiv[i] = Double.parseDouble(amountStr);
                totalIndividualAmount += individualAmountsIndiv[i];

                // Add the Person details to the ArrayList
                personDetails.add(new Person("Custom Individual", "Person " + (i + 1), formattedTotalAmount, individualAmountsIndiv[i], 0));
            }

            correctAmountMsg = "Amounts are correctly calculated.";

            // Check if the total individual amount matches the total bill amount exactly
            if (Math.abs(totalIndividualAmount - totalBillAmount) < 0.001) {
                Toast.makeText(this, correctAmountMsg, Toast.LENGTH_SHORT).show();
                // Save the calculated result and details in SharedPreferences
                individualResult.add(new Result(correctAmountMsg));
                saveSharedPreferencesIndividual(currentDate, personDetails, individualResult);
            } else {
                // Calculate the discrepancy amount (how much money is left out or exceeded)
                double discrepancyAmount = totalIndividualAmount - totalBillAmount;

                String formattedAmount = String.format("%.2f", discrepancyAmount);


                exceedAmountMsg = "The total amount exceeds the total bill by RM " + formattedAmount + ".";
                lackAmountMsg = "The total amount is RM " + formattedAmount + " less than the total bill.";

                if (discrepancyAmount > 0) {
                    Toast.makeText(this, exceedAmountMsg, Toast.LENGTH_LONG).show();
                    // Save the calculated result and details in SharedPreferences
                    individualResult.add(new Result(exceedAmountMsg));
                } else {
                    Toast.makeText(this, lackAmountMsg, Toast.LENGTH_LONG).show();
                    individualResult.add(new Result(lackAmountMsg));
                }
                saveSharedPreferencesIndividual(currentDate, personDetails, individualResult);
            }

        }
    }

    // Combine Breakdown Method
    private void calculateCombineBillBreakdown() {
        String correctAmountMsg = "Amounts are correctly calculated.";
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Get the total bill amount and number of people from EditText fields
        String numPeopleStr = editTextNumPeopleCombine.getText().toString();
        String totalBillStr = editTextTotalBillCombine.getText().toString();

        int maxNumPeople = 10;
        // Validate input fields
        if (totalBillStr.isEmpty() || numPeopleStr.isEmpty()) {
            Toast.makeText(this, "Please enter both the total bill amount and the number of people.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input strings to doubles
        double totalBillAmount = Double.parseDouble(totalBillStr);
        int numPeople = Integer.parseInt(numPeopleStr);

        // Check if the number of people is greater than zero
        if (numPeople < 2) {
            Toast.makeText(this, "Please enter a valid number of people (greater than 2).", Toast.LENGTH_SHORT).show();
            return;
        } else if (numPeople > maxNumPeople) {
            Toast.makeText(this, "Number of People cannot exceed " + maxNumPeople, Toast.LENGTH_LONG).show();
            return;
        }

        // Get the individual amounts and percentages from dynamic EditText fields
        double[] individualAmounts = new double[numPeople];
        double[] percentages = new double[numPeople];
        double totalPercentage = 0.0;
        double totalIndividualAmount = 0.0;

        // Create an ArrayList of Person objects with custom percentages
        ArrayList<Person> personDetails = new ArrayList<>();
        ArrayList<Result> combineResult = new ArrayList<>();

        int decimalPlaces = 2;
        // Create a DecimalFormat object with the desired format pattern
        String pattern = "#." + new String(new char[decimalPlaces]).replace('\0', '#');
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        // Format the double value to the 2 decimal places
        double formattedTotalAmount = Double.parseDouble(decimalFormat.format(totalBillAmount));

        for (int i = 0; i < numPeople; i++) {
            EditText editTextPercentage = editTextCombinePercentageList.get(i);
            EditText editTextAmount = editTextCombineAmountList.get(i);

            String percentageStr = editTextPercentage.getText().toString();
            String amountStr = editTextAmount.getText().toString();

            if (percentageStr.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please enter both percentage and amount for Person " + (i + 1), Toast.LENGTH_SHORT).show();
                return;
            }

            percentages[i] = Double.parseDouble(percentageStr);
            totalPercentage += percentages[i];

            individualAmounts[i] = Double.parseDouble(amountStr);
            totalIndividualAmount += individualAmounts[i];

            // Format the values to 2 decimal places before adding to the ArrayList
            double formattedIndividualAmount = Double.parseDouble(decimalFormat.format(individualAmounts[i]));
            double formattedPercentage = Double.parseDouble(decimalFormat.format(percentages[i]));

            // Add the Person details to the ArrayList
            personDetails.add(new Person("Combine Breakdown", "Person " + (i + 1), formattedTotalAmount, formattedIndividualAmount, formattedPercentage));
        }


        double discrepancyAmount = totalIndividualAmount - totalBillAmount;
        String formattedDiscrepancyAmount = String.format("%.2f", discrepancyAmount);

        String discrepancyMsg = (discrepancyAmount > 0) ?
                "The total amount exceeds the total bill by RM " + formattedDiscrepancyAmount + "." :
                "The total amount is RM " + formattedDiscrepancyAmount + " less than the total bill.";

        // Check if the total percentage is 100%
        if (Math.abs(totalPercentage - 100.0) > 0.001) {
            Toast.makeText(this, "Total percentage must add up to 100%", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Math.abs(discrepancyAmount) < 0.001) {
            combineResult.add(new Result(correctAmountMsg));
            saveSharedPreferencesCombine(currentDate, personDetails, combineResult);
            Toast.makeText(this, correctAmountMsg, Toast.LENGTH_SHORT).show();
        } else {
            combineResult.add(new Result(discrepancyMsg));
            saveSharedPreferencesCombine(currentDate, personDetails, combineResult);
            Toast.makeText(this, discrepancyMsg, Toast.LENGTH_LONG).show();
        }
    }

    // Load existing data from sharedPreferences
    private ArrayList<Person> loadExistingEqualDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("equal_breakdown", MODE_PRIVATE);
        Gson gson = new Gson();
        String equalDetailsJson = sharedPreferences.getString("historyItems", null);

        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> existingPersonDetails = gson.fromJson(equalDetailsJson, type);

        if (existingPersonDetails == null) {
            existingPersonDetails = new ArrayList<>();
        }

        return existingPersonDetails;
    }

    private ArrayList<Person> loadExistingPercentageDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("percentage_breakdown", MODE_PRIVATE);
        Gson gson = new Gson();
        String percentageDetailsJson = sharedPreferences.getString("historyItems", null);

        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> existingPersonDetails = gson.fromJson(percentageDetailsJson, type);

        if (existingPersonDetails == null) {
            existingPersonDetails = new ArrayList<>();
        }

        return existingPersonDetails;
    }

    private ArrayList<Person> loadExistingIndividualDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("individual_breakdown", MODE_PRIVATE);
        Gson gson = new Gson();
        String individualDetailsJson = sharedPreferences.getString("historyItems", null);

        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> existingPersonDetails = gson.fromJson(individualDetailsJson, type);

        if (existingPersonDetails == null) {
            existingPersonDetails = new ArrayList<>();
        }

        return existingPersonDetails;
    }

    private ArrayList<Result> loadExistingIndividualResult() {
        SharedPreferences sharedPreferences = getSharedPreferences("individual_breakdown", MODE_PRIVATE);
        Gson gson = new Gson();
        String resultJson = sharedPreferences.getString("Result", null);

        Type type = new TypeToken<ArrayList<Result>>() {
        }.getType();
        ArrayList<Result> existingIndividualResult = gson.fromJson(resultJson, type);

        if (existingIndividualResult == null) {
            existingIndividualResult = new ArrayList<>();
        }

        return existingIndividualResult;
    }

    private ArrayList<Person> loadExistingCombineDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("combine_breakdown", MODE_PRIVATE);
        Gson gson = new Gson();
        String combineDetailsJson = sharedPreferences.getString("historyItems", null);

        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> existingPersonDetails = gson.fromJson(combineDetailsJson, type);

        if (existingPersonDetails == null) {
            existingPersonDetails = new ArrayList<>();
        }

        return existingPersonDetails;
    }

    private ArrayList<Result> loadExistingCombineResult() {
        SharedPreferences sharedPreferences = getSharedPreferences("combine_breakdown", MODE_PRIVATE);
        Gson gson = new Gson();
        String combineResultJson = sharedPreferences.getString("Result", null);

        Type type = new TypeToken<ArrayList<Result>>() {
        }.getType();
        ArrayList<Result> existingCombineResult = gson.fromJson(combineResultJson, type);

        if (existingCombineResult == null) {
            existingCombineResult = new ArrayList<>();
        }

        return existingCombineResult;
    }

    private void saveSharedPreferencesEqual(String date, ArrayList<Person> personDetails) {

        SharedPreferences sharedPreferences = getSharedPreferences("equal_breakdown", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<Person> existingEqualDetails = loadExistingEqualDetails();

        // Add new details to the existing list
        existingEqualDetails.addAll(personDetails);

        // Save the details in SharedPreferences
        editor.putString("date", date);

        // Convert the ArrayList of Person objects to JSON string using Gson library
        Gson gson = new Gson();
        String equalDetailsJson = gson.toJson(existingEqualDetails);
        editor.putString("historyItems", equalDetailsJson); // use to store equal calculation person details


        editor.apply();
    }

    private void saveSharedPreferencesPercentage(String date, ArrayList<Person> personDetails) {

        SharedPreferences sharedPreferences = getSharedPreferences("percentage_breakdown", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<Person> existingPercentageDetails = loadExistingPercentageDetails();

        // Add new details to the existing list
        existingPercentageDetails.addAll(personDetails);

        // Save the details in SharedPreferences
        editor.putString("date", date);

        // Convert the ArrayList of Person objects to JSON string using Gson library
        Gson gson = new Gson();
        String percentageDetailsJson = gson.toJson(existingPercentageDetails);
        editor.putString("historyItems", percentageDetailsJson); // use to store percentage calculation person details

        editor.apply();
    }

    private void saveSharedPreferencesIndividual(String date, ArrayList<Person> personDetails, ArrayList<Result> individualResult) {

        SharedPreferences sharedPreferences = getSharedPreferences("individual_breakdown", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Load existing data
        ArrayList<Person> existingPersonDetails = loadExistingIndividualDetails();
        ArrayList<Result> existingIndividualResult = loadExistingIndividualResult();

        // Add new details to the existing list
        existingPersonDetails.addAll(personDetails);
        existingIndividualResult.addAll(individualResult);

        // Save the details in SharedPreferences
        editor.putString("date", date);

        // Convert the ArrayList of Person objects to JSON string using Gson library
        Gson gson = new Gson();
        String individualDetailsJson = gson.toJson(existingPersonDetails);
        editor.putString("historyItems", individualDetailsJson); // use to store individual calculation person details

        String resultJson = gson.toJson(existingIndividualResult);
        editor.putString("Result", resultJson);

        editor.apply();
    }

    private void saveSharedPreferencesCombine(String date, ArrayList<Person> personDetails, ArrayList<Result> combineResult) {

        SharedPreferences sharedPreferences = getSharedPreferences("combine_breakdown", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Load existing data
        ArrayList<Person> existingPersonDetails = loadExistingCombineDetails();
        ArrayList<Result> existingCombineResult = loadExistingCombineResult();

        // Add new details to the existing list
        existingPersonDetails.addAll(personDetails);
        existingCombineResult.addAll(combineResult);

        // Save the details in SharedPreferences
        editor.putString("date", date);

        // Convert the ArrayList of Person objects to JSON string using Gson library
        Gson gson = new Gson();
        String combineDetailsJson = gson.toJson(personDetails);
        editor.putString("historyItems", combineDetailsJson); // use to store combine calculation person details

        String resultJson = gson.toJson(existingCombineResult);
        editor.putString("Result", resultJson);

        editor.apply();
    }

    // Clear SharedPreferences data (for debugging)
    private void DeleteData(String key) {
        SharedPreferences preferences = getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    // Share via Whatsapp
    private void shareViaWhatsApp(String message) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp"); // Specify WhatsApp package name to ensure sharing via WhatsApp

        // Message here
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);

        // Checking whether Whatsapp
        // is installed or not
        if (whatsappIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "Please install whatsapp first.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(whatsappIntent);
    }

    // Share via email
    private void shareViaEmail(String subject, String message, String[] recipients) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle the case where no email clients are installed on the device
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_view_history) {
            Intent intent = new Intent(this, ViewHistory.class);
            startActivity(intent);
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

