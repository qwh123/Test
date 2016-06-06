package com.hp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.easemob.easeui.ui.EaseConversationListFragment.EaseConversationListItemClickListener;
import com.hp.R;

/**
 * 会话界面
 * 
 * @author qwh
 * 
 */
public class MessageConversionActivity extends EaseBaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_mesaage_conversion);
		// 步骤一：添加一个FragmentTransaction的实例
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 步骤二：用add()方法加上Fragment的对象rightFragment
		EaseConversationListFragment mConversationListFragment = new EaseConversationListFragment();
		mConversationListFragment
				.setConversationListItemClickListener(new EaseConversationListItemClickListener() {
					@Override
					public void onListItemClicked(EMConversation conversation) {
						startActivity(new Intent(
								MessageConversionActivity.this,
								ChatActivity.class).putExtra(
								EaseConstant.EXTRA_USER_ID,
								conversation.getUserName()));
					}
				});
		transaction.add(R.id.fragment_conversion, mConversationListFragment);
		transaction.show(mConversationListFragment);
		// 步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
		transaction.commit();

	}
}
