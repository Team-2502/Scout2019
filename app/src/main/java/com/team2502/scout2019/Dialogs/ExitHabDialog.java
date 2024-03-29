package com.team2502.scout2019.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class ExitHabDialog extends DialogFragment {
    //TODO This should probably be refactored https://stackoverflow.com/a/36139523

    public interface ExitHabDialogListener {
        void onDialogLeftHabClick(DialogFragment dialog);
        void onDialogNotLeftHabClick(DialogFragment dialog);
    }

    private ExitHabDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Exited Hab?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogLeftHabClick(ExitHabDialog.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNotLeftHabClick(ExitHabDialog.this);
                    }
                });
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ExitHabDialogListener) context;
    }
}
