package com.hp.activity;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UserInfo;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserPerSignActivity extends Activity {

	private EditText edtPersign;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_persign);
		
		String persign=ApplicationController.getInstance().getUser().getString("persign","");
		
		TopBar tBar=(TopBar) findViewById(R.id.topbar_persign);
		tBar.setTitleText("个人简介");
		tBar.setRighttImageResource(R.drawable.icon_right_tj);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Intent intent=getIntent();
				intent.putExtra("persign", edtPersign.getText().toString());
				setResult(4, intent);
				finish();
			}
			@Override
			public void leftClick() {
				finish();
			}
		});
		
		edtPersign=(EditText) findViewById(R.id.id_editContent);
		edtPersign.setHint(persign);
		
	}
	public void doBack(View view){
		this.finish();
	}
}
