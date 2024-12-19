package co.pokeum.inputs.dialog;

import android.content.DialogInterface;

import androidx.annotation.NonNull;

public interface InputsDialogInterface {

    interface OnClickListener {

        /**
         * Called when the positive button is clicked.
         *
         * @param dialog The dialog that received the click.
         * @param id     Used to identify which dialog was interacted with if multiple dialogs are used.
         * @param result Contains the inputs provided by the user.
         */
        void onPositiveButtonClick(DialogInterface dialog, int id, @NonNull String result);

        /**
         * Called when the negative button is clicked.
         *
         * @param dialog The dialog that received the click.
         * @param id     Used to identify which dialog was interacted with if multiple dialogs are used.
         */
        void onNegativeButtonClick(DialogInterface dialog, int id);
    }

    interface ResultListener extends OnClickListener {

        @Override
        default void onNegativeButtonClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    }
}