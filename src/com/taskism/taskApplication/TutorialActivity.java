/**
 * 
 */
package com.taskism.taskApplication;

import com.task.taskApplication.R;

import android.os.Bundle;
import android.view.View;

/**
 * @author Manpreet
 * 
 */
public class TutorialActivity extends ParentActivity {
	ParentActivity parentActivity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_tutorial);
		findAttrbutesId();
	}

	/**
	 * developer:Manpreet date:04-Oct-2015 return:void description:
	 */
	private void findAttrbutesId() {
		parentActivity = this;
		getSideMenu(TutorialActivity.this);
	}

	public void openLeftPanel(View view) {
		showMenu();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#close()
	 */
	@Override
	public void close() {
		super.close();

		showContent();

	}

	public void onClickBack(View view) {
		finish();
	}

}
