package my.utar.com.billsplittingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class resultDialog extends AppCompatDialogFragment {


   /* @Override
    public void onStart() {
        super.onStart();
        // Remove the default "OK" button from the dialog box
        Dialog dialog = getDialog();
        if (dialog != null) {
            Button defaultBtn = dialog.findViewById(android.R.id.button1); // button1 refers to the default ID of positive button
            if (defaultBtn != null) {
                defaultBtn.setVisibility(View.GONE);
            }
        }
    }*/

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

        // Build the custom dialog
        Dialog customDialog = new Dialog(getActivity());
        customDialog.setContentView(view);

        return customDialog;
    }

}
