package com.example.trubul.airpurrr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by krzysiek
 * On 3/12/18.
 */

public class AlertDialogForAuto {
    private Context mContext;
    private MyCallback mCallback;
    private String newStringAutoThreshold;
    private int newIntAutoThreshold;
    private EditText editText;

    public interface MyCallback {
        PMDataResults getPMDataDetectorResults();
        Double[] getPMValuesDetector();
    }

    public AlertDialogForAuto(Context context, MyCallback callback) {
        mContext = context;
        mCallback = callback;
        createDialog();
    }

    private void createDialog() {
        float dpi = mContext.getResources().getDisplayMetrics().density;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        TextView title = new TextView(mContext);
        editText = new EditText(mContext);

        // Konfiguracja

        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextSize(32);
        editText.post(new Runnable() { // show keyboard automatically
            public void run() {
                editText.requestFocusFromTouch();
                InputMethodManager lManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.showSoftInput(editText, 0);
            }
        });

        title.setText(R.string.podaj_wartosc_w_procentach);
        title.setPadding(16, 16, 16, 16);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24);

        builder.setCustomTitle(title);
        builder.setCancelable(true); // with BACK button

        builder.setPositiveButton(
                R.string.OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newStringAutoThreshold = editText.getText().toString();
                        getAutoThreshold();
                    }
                });

        builder.setNegativeButton(
                R.string.anuluj,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setView(editText, (int) (135 * dpi), (int) (10 * dpi), (int) (135 * dpi), (int) (10 * dpi));
        dialog.show();

    }

    public void getAutoThreshold() {
        if (!newStringAutoThreshold.equals("")) {
            newIntAutoThreshold = Integer.parseInt(newStringAutoThreshold);
            setAutoThreshold(newIntAutoThreshold);

        }
        else {
            Toast.makeText(mContext, "Wprowadź poprawną wartość!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAutoThreshold(int threshold) {
        // jesli jakakolwiek z obu wartosci przekroczy threshold
        if ( (4 * mCallback.getPMValuesDetector()[0] > threshold) || (2 * mCallback.getPMValuesDetector()[1] > threshold) ) {
            mCallback.getPMDataDetectorResults().flagTriStateAuto = 2;
        }
        else {
            mCallback.getPMDataDetectorResults().flagTriStateAuto = 1;
        }
    }

}