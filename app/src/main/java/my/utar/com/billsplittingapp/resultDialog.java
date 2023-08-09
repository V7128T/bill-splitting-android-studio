package my.utar.com.billsplittingapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class resultDialog extends AppCompatDialogFragment {

    // Key for the argument (result)
    private static final String ARG_RESULT = "result";

    // Variable to store the result message
    private String resultMessage;

    public static resultDialog newInstances(String result) {
        resultDialog dialog = new resultDialog();
        Bundle args = new Bundle();
        args.putString(ARG_RESULT, result);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Retrieve the result string from the arguments
        resultMessage = getArguments().getString(ARG_RESULT);

        // Create a custom dialog with minimalist style
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);

        // Set the message in the custom dialog box
        TextView dialogMessage = view.findViewById(R.id.dialogMessage);
        dialogMessage.setText(resultMessage);

        // Set the click listener for the "OK" button
        Button buttonOK = view.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog after pressing "OK"
                dismiss();
            }
        });

        // Share button
        Button buttonShare = view.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult(resultMessage);
            }
        });

        // Build the custom dialog
        Dialog customDialog = new Dialog(getActivity());
        customDialog.setContentView(view);

        return customDialog;
    }

    // Function to share the result message through other apps
    private void shareResult(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

}
