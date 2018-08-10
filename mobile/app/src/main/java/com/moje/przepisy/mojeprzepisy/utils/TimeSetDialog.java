package com.moje.przepisy.mojeprzepisy.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.R;

public class TimeSetDialog {
  String hour;
  String minute;
  int hourInt;
  int minuteInt;

  public void showDialog(Activity activity, final TextView textViewToSet){
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.time_set_dialog);

    final NumberPicker hourPicker = dialog.findViewById(R.id.hourPicker);
    final NumberPicker minutePicker = dialog.findViewById(R.id.minutePicker);

    hourPicker.setMinValue(0);
    hourPicker.setMaxValue(72);
    hourPicker.setWrapSelectorWheel(true);
    minutePicker.setMinValue(0);
    minutePicker.setMaxValue(59);
    minutePicker.setWrapSelectorWheel(true);

    Button dialogButton = (Button) dialog.findViewById(R.id.set_time_button);
    dialogButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hourInt = hourPicker.getValue();
        hour = Integer.toString(hourInt);
        minuteInt = minutePicker.getValue();
        minute = Integer.toString(minuteInt);
        dialog.dismiss();
        textViewToSet.setText(hour + " : " + minute);
      }
    });
    dialog.show();
  }
}