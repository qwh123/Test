package com.hp.activity;

import com.hp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 举报界面
 * 
 * @author qwh
 * 
 */
public class ReportActivity extends Activity {
	private RadioGroup reportGroup;
	private RadioButton rbtn1;
	private RadioButton rbtn2;
	private RadioButton rbtn3;
	private RadioButton rbtn4;
	private RadioButton rbtn5;
	private EditText edtReport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_jubao);
		intitView();
		reportGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void intitView() {
		reportGroup = (RadioGroup) findViewById(R.id.rdg_report);
		rbtn1 = (RadioButton) findViewById(R.id.rbtn_1);
		rbtn2 = (RadioButton) findViewById(R.id.rbtn_2);
		rbtn3 = (RadioButton) findViewById(R.id.rbtn_3);
		rbtn4 = (RadioButton) findViewById(R.id.rbtn_4);
		rbtn5 = (RadioButton) findViewById(R.id.rbtn_5);
		edtReport = (EditText) findViewById(R.id.edt_jubao);

	}
}
