package com.hp.widget;

import com.hp.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimerAlertDialog extends AlertDialog {
	private Context mContext;
	private TextView mDate;
	public TimerAlertDialog(Context context ,TextView textView) {
		super(context);
		this.mContext = context;
		this.mDate=textView;
		setDate();
	}
	public void setDate(){
		View view = View.inflate(mContext, R.layout.date_time_picker, null);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.new_act_date_picker);
		final TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.new_act_time_picker);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setView(view);
		builder.setTitle("预计到达时间");
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int year = datePicker.getYear();
						int month = datePicker.getMonth() + 1;
						int day = datePicker.getDayOfMonth();
						int hour = timePicker.getCurrentHour();
						int min = timePicker.getCurrentMinute();
						mDate.setText(year + "-" + month + "-" + day + " "
								+ hour + ":" + min);

					}
				});
		builder.show();
	}
}
