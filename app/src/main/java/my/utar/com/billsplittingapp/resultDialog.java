package my.utar.com.billsplittingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class resultDialog extends AppCompatDialogFragment {

    // Key for the argument (result)
    private static final String ARG_RESULT = "result";

    public static resultDialog newInstances(String result){
        resultDialog dialog = new resultDialog();
        Bundle args = new Bundle();
        args.putString(ARG_RESULT, result);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Retrieve the result string from the arguments
        String result = getArguments().getString(ARG_RESULT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bill Breakdown");
        builder.setMessage(result);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dismiss();
            }
        });

        return builder.create();
    }
}
