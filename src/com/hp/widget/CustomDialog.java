package com.hp.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hp.R;

public class CustomDialog extends Dialog {
	private EditText editText;
	private Button positiveButton, negativeButton;
	private TextView title;

	public CustomDialog(Context context) {
		super(context,R.style.dialog);
		setCustomDialog();
	}
	private void setCustomDialog() {
		View mView = LayoutInflater.from(getContext()).inflate(
				R.layout.dlg_custom, null);
		title = (TextView) mView.findViewById(R.id.title);
		editText = (EditText) mView.findViewById(R.id.number);
		positiveButton = (Button) mView.findViewById(R.id.positiveButton);
		negativeButton = (Button) mView.findViewById(R.id.negativeButton);
		
		super.setContentView(mView);
	}

	public View getEditText() {
		return editText;
	}

	@Override
	public void setContentView(int layoutResID) {
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
	}

	@Override
	public void setContentView(View view) {
	}

	/**
	 * 响应事件
	 * @param listener
	 */
	public void setOnPositiveListener(View.OnClickListener listener) {
		positiveButton.setOnClickListener(listener);
	}

	/**
	 * 取消按钮
	 * 
	 * @param listener
	 */
	public void setOnNegativeListener(View.OnClickListener listener) {
		negativeButton.setOnClickListener(listener);
	}
}