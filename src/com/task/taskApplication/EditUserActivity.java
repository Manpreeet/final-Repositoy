/**
 * 
 */
package com.task.taskApplication;

import android.content.Context;
import android.os.Bundle;

/**
 * @author Manpreet
 * 
 */
public class EditUserActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.acitivty_edit_user);
		findAttributesId();
	}

	/**
	 * developer:Manpreet
	   date:03-Oct-2015
	   return:void
	   description:method for find attributes id
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
	}
}
