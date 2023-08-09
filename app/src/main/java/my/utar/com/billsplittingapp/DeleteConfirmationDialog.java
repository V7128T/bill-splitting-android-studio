package my.utar.com.billsplittingapp;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeleteConfirmationDialog extends DialogFragment {

    // Listener interface for dialog events
    public interface DeleteConfirmationListener {
        void onDeleteConfirmed();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_message, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        final AlertDialog dialog = builder.create();

        TextView dialogMessage = view.findViewById(R.id.dialogMessage);
        Button buttonDelete = view.findViewById(R.id.buttonConfirm);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        // Set the message
        dialogMessage.setText("Note: This cannot be undone!");

        // Set click listeners
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteConfirmationListener listener = (DeleteConfirmationListener) getActivity();
                if (listener != null) {
                    listener.onDeleteConfirmed();
                }
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
