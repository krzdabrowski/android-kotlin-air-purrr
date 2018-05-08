package com.example.trubul.airpurrr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
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
    private ChangeListener mListener;
    private String newStringAutoThreshold;
    private int newIntAutoThreshold;
    private boolean isCorrectInput;


    public interface ChangeListener {
        void onChange();
    }

    public int getThreshold() {
        return newIntAutoThreshold;
    }

    public void setListener(ChangeListener listener) {
        mListener = listener;
    }

    public AlertDialogForAuto(Context context) {
        mContext = context;
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        TextView title = new TextView(mContext);
        final EditText editText = new EditText(mContext);

        // Configuration of AlertDialog
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextSize(32);
        editText.post(new Runnable() {  // show keyboard automatically
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
        builder.setCancelable(true);  // with BACK button
        builder.setPositiveButton(
                R.string.OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.setNegativeButton(
                R.string.anuluj,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();
        float dpi = mContext.getResources().getDisplayMetrics().density;
        dialog.setView(editText, (int) (135 * dpi), (int) (10 * dpi), (int) (135 * dpi), (int) (10 * dpi));
        dialog.show();

        //Overriding the handler immediately after show is probably a better approach than OnShowListener
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStringAutoThreshold = editText.getText().toString();
                getAutoThreshold();

                if (isCorrectInput)
                    dialog.dismiss();
            }
        });
    }

    public void getAutoThreshold() {
        if (!TextUtils.isEmpty(newStringAutoThreshold)) {
            isCorrectInput = true;
            newIntAutoThreshold = Integer.parseInt(newStringAutoThreshold);
            mListener.onChange();
        }
        else {
            isCorrectInput = false;
            Toast.makeText(mContext, "Wprowadź poprawną wartość!", Toast.LENGTH_SHORT).show();
        }
    }

}
